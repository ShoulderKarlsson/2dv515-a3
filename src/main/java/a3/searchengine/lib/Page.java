package a3.searchengine.lib;

import java.io.Serializable;
import java.util.ArrayList;

public class Page implements Serializable {
    public String url;
    public double pageRank = 1.0;
    public ArrayList<Integer> words;
    public ArrayList<String> links;

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

    public boolean hasLinkTo(String url) {
        return this.links.contains(url);
    }

    public int getAmountOfLinks() {
        return links.size();
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public double getPageRank() {
        return this.pageRank;
    }

}
