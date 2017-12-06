package a3.searchengine.lib;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class FileStorage {

    public void write(Collection col, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(this.getFilePath(fileName));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(col);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(HashMap<String, Integer> col, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(this.getFilePath(fileName));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(col);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getFilePath(String fileName) {
        return "./src/main/resources/" + fileName + ".ser";
    }

    public ArrayList<Page> readPages(String fileName) {
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

    public HashMap<String, Integer> readWordtoIds(String fileName) {
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


    public boolean isDataStored() {


        // TODO: Implement..?


        return false;
    }
}
