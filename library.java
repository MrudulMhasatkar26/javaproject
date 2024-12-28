package Librarymanagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class library {
  private List<Book> books;
    private final String filePath = "data/books.txt";

    public library() {
        books = new ArrayList<>();
        loadBooksFromFile();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        saveBooksToFile();
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            for (int i = 0; i < books.size(); i++) {
                System.out.println(i + 1 + ". " + books.get(i));
            }
        }
    }

    public void borrowBook(int index) {
        if (index >= 0 && index < books.size()) {
            Book book = books.get(index);
            if (!book.isBorrowed()) {
                book.borrowBook();
                saveBooksToFile();
                System.out.println("You borrowed: " + book.getTitle());
            } else {
                System.out.println("The book is already borrowed.");
            }
        } else {
            System.out.println("Invalid book index!");
        }
    }

    public void returnBook(int index) {
        if (index >= 0 && index < books.size()) {
            Book book = books.get(index);
            if (book.isBorrowed()) {
                book.returnBook();
                saveBooksToFile();
                System.out.println("You returned: " + book.getTitle());
            } else {
                System.out.println("The book wasn't borrowed.");
            }
        } else {
            System.out.println("Invalid book index!");
        }
    }

    private void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Book book = new Book(parts[0], parts[1]);
                if (parts[2].equals("borrowed")) {
                    book.borrowBook();
                }
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("No saved books found.");
        }
    }

    private void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : books) {
                writer.write(book.getTitle() + ";" + book.getAuthor() + ";" +
                        (book.isBorrowed() ? "borrowed" : "available"));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books to file.");
        }
    }
}
