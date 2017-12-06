package a3.searchengine.lib;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class PageBucket {
    HashMap<String, Integer> wordId = new HashMap<>();
    ArrayList<Page> pages = new ArrayList<>();


    public PageBucket() {
        generateDB();
    }

    int getIdForWord(String word) {
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


        FileStorage fs = new FileStorage();
        fs.write(pages, "pages");
        fs.write(wordId, "wordIds");

        fs.readPages("pages");
        fs.readWordtoIds("wordIds");

        System.out.println(fs);
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
