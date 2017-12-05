package a3.searchengine.lib;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PageBucket {
    HashMap<String, Integer> wordId = new HashMap<>();
    ArrayList<Page> pages = new ArrayList<>();


    public PageBucket() {

        generateDB();
    }

    public int getIdForWord(String word) {
        if (wordId.containsKey(word)) {
            return wordId.get(word);
        }

        int newId = wordId.size();
        wordId.put(word, newId);
        return newId;
    }

    private void generatePage(String url, File wordsFile, File linksFile) {
        try {
            pages.add(new Page(
                    url,
                    this.readFile(wordsFile, " "),
                    this.readFile(linksFile)
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> readFile(File file, String separator) {
        ArrayList<Integer> fileData = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Files.lines(file.toPath()).forEach(line -> {
                String[] wordsList = line.split(separator);
                Arrays.stream(wordsList).forEach(word -> fileData.add(this.getIdForWord(word)));
            });
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

    private ArrayList<String> readFile(File file) {
        ArrayList<String> fileData = new ArrayList<>();
        try {
            Files.lines(file.toPath()).forEach(fileData::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

    private void generateDB() {
        try {
            File mainDir = ResourceUtils.getFile("classpath:Wikipedia/Words/");
            for (File mainDirFile : mainDir.listFiles()) {
                for (File wordBag : mainDirFile.listFiles()) {
                    this.generatePage(
                            "/wiki/" + wordBag.getName(),
                            wordBag,
                            this.getLinksFile(mainDirFile.getName(), wordBag.getName())
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        storeDataToIndex();
    }

    private boolean tryFetchDataFromIndex() {
        return false;
    }

    private void storeDataToIndex() {
        JSONArray pageData = createJSONData();
        JSONArray wordTOIdData = buildWordToIdJSONData();
        storeFile(pageData, "PageData.json");
        storeFile(wordTOIdData, "WordToIdData.json");
    }

    private void storeFile(JSONArray json, String name) {
        File dir = new File("src/main/resources/DB");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/DB/" + name);
            fileWriter.write(json.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public JSONArray buildWordToIdJSONData() {
        JSONArray json = new JSONArray();
        for (Map.Entry<String, Integer> entry : wordId.entrySet()) {
            JSONObject data = new JSONObject();
            data.put(entry.getKey(), entry.getValue());
            json.add(data);
        }

        return json;
    }

    private JSONArray createJSONData() {
        JSONArray json = new JSONArray();
        for (Page p : pages) {
            JSONObject pageJSON = new JSONObject();
            JSONArray wordsJsonArray = new JSONArray();
            wordsJsonArray.addAll(p.words);
            JSONArray linksJsonArray = new JSONArray();
            linksJsonArray.addAll(p.links);
            pageJSON.put("url", p.getUrl());
            pageJSON.put("pageRank", p.pageRank);
            pageJSON.put("words", wordsJsonArray);
            pageJSON.put("links", linksJsonArray);

            json.add(pageJSON);
        }

        return json;
    }

    private File getLinksFile(String mainDirectoryName, String pageName) {
        try {
            return ResourceUtils.getFile("classpath:Wikipedia/Links/" + mainDirectoryName + "/" + pageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
