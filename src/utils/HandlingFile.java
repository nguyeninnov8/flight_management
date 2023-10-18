package utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class HandlingFile<T> implements IHandlingFile<T> {

    public HandlingFile() {
    }

    @Override
    public boolean saveToFile(String filePath, List<T> list) {
        File file = new File(filePath);
        if (list.isEmpty()) {
            System.err.println("Empty list!");
            return false;
        }
        try {
            FileOutputStream writeData = new FileOutputStream(file);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
            for (T item : list) {
                writeStream.writeObject(item);
            }
            writeStream.flush();
            writeStream.close();
            writeData.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean loadFromFile(String filePath, List<T> list) {
        File file = new File(filePath);
        if (file.length() == 0) {
            System.err.println("Empty file: " + filePath);
            return false;
        }
        boolean more;
        try {
            FileInputStream readData = new FileInputStream(file);
            ObjectInputStream readStream = new ObjectInputStream(readData);
            more = true;
            while (more) {
                try {
                    T item = (T) readStream.readObject();
                    if (item != null) {
                        list.add(item);
                    } else {
                        more = false;
                    }
                } catch (EOFException e){
                    more = false;
                }
            }
            readStream.close();
            readData.close();
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }

}
