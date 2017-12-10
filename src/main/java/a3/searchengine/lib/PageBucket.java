package a3.searchengine.lib;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class PageBucket {
    private FileStorage fs = new FileStorage();
    HashMap<String, Integer> wordId = new HashMap<>();
    ArrayList<Page> pages = new ArrayList<>();
    private final String PAGE_DATA_FILE = "PAGE_DATA";
    private final String WORD_TO_ID_FILE = "WORD_TO_ID_FILE";


    public PageBucket() {
        generateDB();
    }


    /**
     * Writes the data stored in memory to disk
     */
    void writeDb() {

        System.out.println(" > Starting writing to disk..");
        fs.write(pages, PAGE_DATA_FILE);
        System.out.println(" > Pages done..");

        fs.write(wordId, WORD_TO_ID_FILE);
        System.out.println(" > wordIds done..");
    }

    boolean isDataOnDisk() {
        return fs.isDataStored(PAGE_DATA_FILE, WORD_TO_ID_FILE);
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

    /**
     * Generates the "database" used for the calculations
     *
     * If the data is already used previously it will load it from disk
     * else if will generate the data from scratch and then write it to disk
     */
    private void generateDB() {
        if (this.isDataOnDisk()) {
            System.out.println(" > Data found on disk, fetching...");
            wordId = fs.readWordtoIds(WORD_TO_ID_FILE);
            pages = fs.readPages(PAGE_DATA_FILE);
            System.out.println(" > Data loaded from disk...");
        } else {
            System.out.println(" > Generating new data...");
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
        }
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
