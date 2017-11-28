package a3.searchengine.lib;

import java.util.ArrayList;

public class Page {
    private String url;
    private ArrayList<Integer> words = new ArrayList<>();


    public String getUrl() {
        return this.url;
    }

    public ArrayList<Integer> getWords() {
        return words;
    }
}
