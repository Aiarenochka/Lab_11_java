package ua.edu.nuos.lab_11_java.factory;

import ua.edu.nuos.lab_11_java.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookFactory {
    public static List<Book> createBooks(){
        return new ArrayList<>(List.of(
                new Book(1, "The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", 277, 1951, "Fiction"),
                new Book(2, "1984", "George Orwell", "Secker & Warburg", 328, 1949, "Dystopia"),
                new Book(3, "To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", 281, 1960, "Drama"),
                new Book(4, "Brave New World", "Aldous Huxley", "Chatto & Windus", 311, 1932, "Dystopia"),
                new Book(5, "Fahrenheit 451", "Ray Bradbury", "Ballantine Books", 249, 1953, "Sci-Fi"),
                new Book(6, "Animal Farm", "George Orwell", "Secker & Warburg", 112, 1945, "Dystopia"),
                new Book(7, "Go Set a Watchman", "Harper Lee", "HarperCollins", 278, 2015, "Drama"),
                new Book(8, "The Martian Chronicles", "Ray Bradbury", "Doubleday", 222, 1950, "Sci-Fi"),
                new Book(9, "Nine Stories", "J.D. Salinger", "Little, Brown and Company", 198, 1951, "Fiction"),
                new Book(10, "Do Androids Dream of Electric Sheep?", "Philip K. Dick", "Doubleday", 210, 1968, "Sci-Fi"),
                new Book(11, "The Handmaid's Tale", "Margaret Atwood", "McClelland and Stewart", 311, 1985, "Dystopia"),
                new Book(12, "The Left Hand of Darkness", "Ursula K. Le Guin", "Ace Books", 304, 1969, "Sci-Fi"),
                new Book(13, "Slaughterhouse-Five", "Kurt Vonnegut", "Delacorte", 275, 1969, "Fiction"),
                new Book(14, "Lord of the Flies", "William Golding", "Faber and Faber", 224, 1954, "Drama"),
                new Book(15, "Neuromancer", "William Gibson", "Ace Books", 271, 1984, "Sci-Fi")        ));
    }
}
