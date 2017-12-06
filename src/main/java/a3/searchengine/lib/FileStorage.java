package a3.searchengine.lib;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class FileStorage {

    FileStorage() {
        createFolderForFiles();
    }


    /**
     * Creating DB folder if there is none present
     */
    private void createFolderForFiles() {
        File f = new File("./src/main/resources/DB");
        if (!f.exists() && !f.isDirectory()) {
            f.mkdir();
        }
    }

    public void write(Collection col, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(this.getFilePath(fileName));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(col);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(HashMap<String, Integer> map, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(this.getFilePath(fileName));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList<Page> readPages(String fileName) {
        ArrayList<Page> pages = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.getFilePath(fileName)));
            ArrayList<Page> objects = (ArrayList<Page>) ois.readObject();
            pages.addAll(objects);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pages;
    }

    HashMap<String, Integer> readWordtoIds(String fileName) {
        HashMap<String, Integer> wordId = new HashMap<>();

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.getFilePath(fileName)));
            HashMap<String, Integer> objects = (HashMap<String, Integer>) ois.readObject();
            wordId.putAll(objects);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return wordId;
    }


    boolean isDataStored(String fileNameOne, String fileNameTwo) {
        File fileOne = new File(this.getFilePath(fileNameOne));
        File fileTwo = new File(this.getFilePath(fileNameTwo));

        return fileOne.exists() && fileTwo.exists();
    }

    private String getFilePath(String fileName) {
        return "./src/main/resources/DB/" + fileName + ".ser";
    }
}
