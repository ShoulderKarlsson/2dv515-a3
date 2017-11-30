package a3.searchengine.lib;


import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    private void generatePage(String url, File wordsFile) {
        try {
            ArrayList<Integer> words = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(wordsFile));

            Files.lines(wordsFile.toPath()).forEach(line -> {
                String[] wordsList =  line.split(" ");
                Arrays.stream(wordsList).forEach(word -> words.add(this.getIdForWord(word)));
            });

            br.close();
            pages.add(new Page(url, words));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateDB() {
        try {
            File mainDir = ResourceUtils.getFile("classpath:Wikipedia/Words/");
            for (File mainDirFile : mainDir.listFiles()) {
                for (File wordBag : mainDirFile.listFiles()) {
                    this.generatePage("/wiki/" + wordBag.getName(), wordBag);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
