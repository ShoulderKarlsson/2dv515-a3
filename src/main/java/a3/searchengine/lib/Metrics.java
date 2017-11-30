package a3.searchengine.lib;

public class Metrics {
    public static int wordFrequence(String word, PageBucket pb) {
        final int[] score = {0};
        int queryWordId = pb.getIdForWord(word);
        pb.pages.forEach(page -> {
            page.getWords().forEach(wordId -> {
                if (wordId == queryWordId) {
                    score[0]++;
                }
            });
        });

        return score[0];
    }
}
