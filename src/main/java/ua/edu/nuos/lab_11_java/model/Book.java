package ua.edu.nuos.lab_11_java.model;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Book implements Serializable, Comparable<Book> {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int pages;
    private int year;
    private String genre;

@Override
    public int compareTo(Book other) {
        return Integer.compare(this.year, other.year);
    }
}


