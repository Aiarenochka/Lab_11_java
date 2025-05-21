package ua.edu.nuos.lab_11_java.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ua.edu.nuos.lab_11_java.model.Book;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class BookRepositoryJSONImpl implements BookRepository {
    @Override
    public void saveToFile(List<Book> books, File file) {
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(books, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToFile(List<Book> items, String fileName) {
        File file = new File(fileName);
        saveToFile(items, file);
    }

    @Override
    public List<Book> loadFromFile(File file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type listType = new TypeToken<List<Book>>() {}.getType();
            return gson.fromJson(new FileReader(file), listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> loadFromFile(String fileName) {
        File file = new File(fileName);
        return loadFromFile(file);
    }
}
