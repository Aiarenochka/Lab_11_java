package ua.edu.nuos.lab_11_java.service;

import org.springframework.stereotype.Service;
import ua.edu.nuos.lab_11_java.model.Book;

import java.util.*;
import java.util.stream.Collectors;
@Service

public class BookService {

    public List<Book> findBooksByAuthor(List<Book> books, String author) {
        return books.stream()
                .filter(book -> book != null && book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByPublisher(List<Book> books, String publisher) {
        return books.stream()
                .filter(book -> book != null && book.getPublisher().equalsIgnoreCase(publisher))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByMinPages(List<Book> books, int minPages) {
        return books.stream()
                .filter(book -> book != null && book.getPages() >= minPages)
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByGenreSorted(List<Book> books, String genre) {
        return books.stream()
                .filter(book -> book != null && book.getGenre().equalsIgnoreCase(genre))
                .sorted(Comparator.comparingInt(Book::getYear)
                        .thenComparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public List<Book> searchingNameGenreAuthor(List<Book> books, String inputData){
        if (inputData == null || inputData.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String searchTerm = inputData.trim().toLowerCase();
        boolean isNumeric = searchTerm.matches("-?\\d+(\\.\\d+)?");

        return books.stream()
                .filter(book -> book != null && (
                        book.getTitle().toLowerCase().contains(searchTerm) ||
                                book.getAuthor().toLowerCase().contains(searchTerm) ||
                                book.getGenre().toLowerCase().contains(searchTerm) ||
                                book.getPublisher().toLowerCase().contains(searchTerm) ||
                                (isNumeric && (
                                        book.getId() == Integer.parseInt(searchTerm) ||
                                                book.getYear() == Integer.parseInt(searchTerm) ||
                                                book.getPages() == Integer.parseInt(searchTerm)
                                ))
                )).collect(Collectors.toList());
    }

    public Book findBookById(List<Book> books, int id) {
        return books.stream()
                .filter(book -> book != null && book.getId() == id)
                .findFirst()
                .orElse(null);
    }


    public Map<String, List<Book>> mapGenreToBooksSortedByAuthor(List<Book> books) {
        return books.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Book::getGenre,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Book::getAuthor))
                                        .collect(Collectors.toList())
                        )
                ));
    }

    public Map<String, Integer> countBooksPerPublisher(List<Book> books) {
        return books.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Book::getPublisher,
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )
                ));
    }
}
