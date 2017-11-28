package a3.searchengine.lib;

import java.util.ArrayList;
import java.util.HashMap;

public class PageBucket {
    HashMap<String, Integer> wordTold = new HashMap<>();
    ArrayList<Page> pages = new ArrayList<>();
}
