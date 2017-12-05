package a3.searchengine.lib;

import java.util.ArrayList;
import java.util.Collections;

public class PageBucketSearch {
    public PageBucket pb;
    private final int pageRankIterations = 3;
//    private ArrayList<Page> pages;

    public PageBucketSearch(PageBucket pb) {
        this.pb = pb;
    }

    public ArrayList<SearchResult> search(String query) {
        ArrayList<SearchResult> searchResult = new ArrayList<>();

        double[] frequenceyScore = new double[this.pb.pages.size()];
        double[] locationScore = new double[this.pb.pages.size()];

        String[] queryWords = query.split(" ");
        calculatePageRank();

        for (int i = 0; i < this.pb.pages.size(); i++) {
            Page page = getPage(i);
            frequenceyScore[i] = countWordFrequencyScore(page, queryWords);
            locationScore[i] = countWordLocationScore(page, queryWords);

        }

        normalize(frequenceyScore, false);
        normalize(locationScore, true);

        for (int i = 0; i < this.pb.pages.size(); i++) {
            Page p = getPage(i);
//            double score = 1.0 * frequenceyScore[i] + 0.5 * locationScore[i];
            double score = 1.0 * frequenceyScore[i] + 1.0 * p.pageRank + 0.5 * locationScore[i];
            searchResult.add(new SearchResult(p, score));
        }

        Collections.sort(searchResult);
        System.out.println("Search result: " + query);
        int limit = 5;
        for (int i = 0; i < limit; i++) {
            System.out.println(searchResult.get(i).toString());
        }

        return searchResult;
    }

    private Page getPage(int i) {
        return this.pb.pages.get(i);
    }

    private void normalize(double[] scores, boolean wantSmall) {

        // Yes
        double randomLowValue = 0.00001;

        if (wantSmall) {
            double min = Double.MAX_VALUE;
            for (double score : scores) {
                if (score < min) min = score;
            }

            for (int i = 0; i < scores.length; i++) {
                scores[i] = min / Math.max(scores[i], randomLowValue);
            }

        } else {
            double max = Double.MIN_VALUE;
            for (double score : scores) {
                if (score > max) max = score;
            }

            if (max == 0) max = randomLowValue;
            for (int i = 0; i < scores.length; i++) {
                scores[i] = scores[i] / max;
            }
        }
    }


    private double countWordFrequencyScore(Page page, String[] queryWords) {
        double score = 0;
        for (String word : queryWords) {
            int wordId = this.pb.getIdForWord(word);
            for (int pageWordId : page.getWords()) {
                if (wordId == pageWordId) score++;
            }
        }

        return score;
    }

    private double countWordLocationScore(Page page, String[] queryWords) {
        double score = 0;
        boolean wasFound = false;

        for (String word : queryWords) {
            int queryWordId = this.pb.getIdForWord(word);
            for (int i = 0; i < page.getWords().size(); i++) {
                if (page.getWords().get(i) == queryWordId) {
                    score += i;
                    wasFound = true;
                    break;
                }
            }

            if (!wasFound) {
                score += 100000;
            } else {
                wasFound = false;
            }
        }
        return score;
    }

    public void calculatePageRank() {
//        System.out.println("Doing some heavy calculations!!");
        for (int i = 0; i < pageRankIterations; i++) {
            for (Page p : this.pb.pages) {
                iteratePageRank(p);
            }

            System.out.println("Iteration done! " + i + " / " + pageRankIterations);
        }
    }

    private void iteratePageRank(Page p) {
        double pr = 0;

        for (Page po : this.pb.pages) {
            if (po.hasLinkTo(p.getUrl())) {
                pr += po.pageRank / (double) po.getAmountOfLinks();
            }
        }

        p.pageRank = 0.85 * pr + 0.15;
    }


    public class SearchResult implements Comparable<SearchResult> {
        public Page p;
        public double score;
        SearchResult(Page p, double score) {
            this.p = p;
            this.score = score;
        }

        public String toString() {
            return this.p.getUrl() + ". score: " + this.score;
        }

        @Override
        public int compareTo(SearchResult sr) {
            double srScore = sr.score;
            return Double.compare(srScore, this.score);
        }
    }


}
