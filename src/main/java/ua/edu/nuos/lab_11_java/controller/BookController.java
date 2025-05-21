package ua.edu.nuos.lab_11_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.edu.nuos.lab_11_java.model.Book;
import ua.edu.nuos.lab_11_java.repository.BookRepositoryJSONImpl;
import ua.edu.nuos.lab_11_java.service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BookController {
    private final BookService bookService;
    private final BookRepositoryJSONImpl bookRepositoryJSON;

    private List<Book> books = new ArrayList<>();

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.bookRepositoryJSON = new BookRepositoryJSONImpl();
    }

    @GetMapping("")
    public String mainPage() {
        return "home";
    }

    @GetMapping("/books")
    public String showBooks(Model model) {
        if (!model.containsAttribute("books")) {
            model.addAttribute("books", books.isEmpty() ? Collections.emptyList() : books);
        }
        return "list";
    }

    @GetMapping("/load-from-json")
    public String loadFromJson(RedirectAttributes redirectAttributes) {
        try {
            books = bookRepositoryJSON.loadFromFile("books.json");
            redirectAttributes.addFlashAttribute("books", books);
            redirectAttributes.addFlashAttribute("message", "Дані успішно завантажено з books.json");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Помилка при завантаженні: " + e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/save-to-json")
    public String saveToJson(RedirectAttributes redirectAttributes) {
        try {
            if (!books.isEmpty()) {
                bookRepositoryJSON.saveToFile(books, "books.json");
                redirectAttributes.addFlashAttribute("message", "Дані успішно збережено у books.json");
            } else {
                redirectAttributes.addFlashAttribute("warning", "Немає даних для збереження");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Помилка збереження: " + e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/books/author")
    public String filterByAuthor(@RequestParam String author, RedirectAttributes redirectAttributes) {
        var result = bookService.findBooksByAuthor(books, author);
        redirectAttributes.addFlashAttribute("books", result);
        return "redirect:/books";
    }

    @GetMapping("/books/publisher")
    public String filterByPublisher(@RequestParam String publisher, RedirectAttributes redirectAttributes) {
        var result = bookService.findBooksByPublisher(books, publisher);
        redirectAttributes.addFlashAttribute("books", result);
        return "redirect:/books";
    }

    @GetMapping("/books/pages")
    public String filterByPages(@RequestParam int minPages, RedirectAttributes redirectAttributes) {
        var result = bookService.findBooksByMinPages(books, minPages);
        redirectAttributes.addFlashAttribute("books", result);
        return "redirect:/books";
    }

    @GetMapping("/books/genre")
    public String filterByGenreSorted(@RequestParam String genre, RedirectAttributes redirectAttributes) {
        var result = bookService.findBooksByGenreSorted(books, genre);
        redirectAttributes.addFlashAttribute("books", result);
        return "redirect:/books";
    }

    @GetMapping("/books/check")
    public String checkById(@RequestParam int id, RedirectAttributes redirectAttributes) {
        Book found = bookService.findBookById(books, id);
        if (found != null) {
            redirectAttributes.addFlashAttribute("message",
                    "Назва: " + found.getTitle() +
                            "\nЖанр: " + found.getGenre() +
                            "\nРік видання: " + found.getYear());
        } else {
            redirectAttributes.addFlashAttribute("warning", "Книгу з таким ID не знайдено.");
        }
        return "redirect:/books";
    }

    @GetMapping("/books/stats/genre")
    public String statsByGenre(RedirectAttributes redirectAttributes) {
        Map<String, List<Book>> map = bookService.mapGenreToBooksSortedByAuthor(books);
        redirectAttributes.addFlashAttribute("genreMap", map);
        return "redirect:/books/stats/genre-view";
    }

    @GetMapping("/books/stats/genre-view")
    public String showGenreStats(Model model) {
        return "stats_genre";
    }

    @GetMapping("/books/stats/publisher")
    public String statsByPublisher(RedirectAttributes redirectAttributes) {
        Map<String, Integer> map = bookService.countBooksPerPublisher(books);
        redirectAttributes.addFlashAttribute("publisherMap", map);
        return "redirect:/books/stats/publisher-view";
    }

    @GetMapping("/books/stats/publisher-view")
    public String showPublisherStats(Model model) {
        return "stats_publisher";
    }

    @GetMapping("/books/add_book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add_book";
    }

    @PostMapping("/books/add_book")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {

        boolean exists = books.stream().anyMatch(b -> b.getId() == book.getId());

        if (exists) {
            redirectAttributes.addFlashAttribute("error", "Книга з ID " + book.getId() + " вже існує. Оберіть інший ID.");
            return "redirect:/books/add_book";
        }

        int index = 0;
        while (index < books.size() && books.get(index).getId() < book.getId()) {
            index++;
        }
        books.add(index, book);

        redirectAttributes.addFlashAttribute("message", "Книгу успішно додано: " + book.getTitle());
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean removed = books.removeIf(b -> b.getId() == id);
        if (removed) {
            redirectAttributes.addFlashAttribute("message", "Книга з ID " + id + " успішно видалена.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Книгу з ID " + id + " не знайдено.");
        }
        return "redirect:/books";
    }



    @GetMapping("/books/search_by_author")
    public String showSearchByAuthorForm() {
        return "search_by_author";
    }

    @GetMapping("/books/search_by_publisher")
    public String showSearchByPublisherForm() {
        return "search_by_publisher";
    }

    @GetMapping("/books/search_by_genre")
    public String showSearchByGenreForm() {
        return "search_by_genre";
    }

    @GetMapping("/books/search_by_id")
    public String showSearchByIdForm() {
        return "search_by_id";
    }

    @GetMapping("/books/search_by_pages")
    public String showSearchByPagesForm() {
        return "search_by_pages";
    }
}
