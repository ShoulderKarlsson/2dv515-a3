package a3.searchengine.lib;

import java.util.ArrayList;

public class Page {
    private String url;
    private double pageRank = 1.0;
    private ArrayList<Integer> words;
    private ArrayList<String> links;

    Page(String url, ArrayList<Integer> words, ArrayList<String> links) {
        this.url = url;
        this.words = words;
        this.links = links;
    }

    public String getUrl() {
        return this.url;
    }

    public ArrayList<Integer> getWords() {
        return words;
    }
}
