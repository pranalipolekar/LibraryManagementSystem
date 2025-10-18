package org.library.bookmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BookRepository {
    private static final Logger logger  = Logger.getLogger(BookRepository.class.getName());
    private Map<String, Book> bookMap = new HashMap<>();
    public void addBook(Book book) {
        bookMap.put(book.getIsbn(), book);
    }
    public void removeBook(String isbn) {
        bookMap.remove(isbn);
    }

    public void updateBookTitle(String isbn, String newTitle){
        Book book = bookMap.get(isbn);
        if (book !=null)
        {
            book.setTitle(newTitle);
        }
    }
    public void updateBookAuthor(String isbn, String newAuthor){
        Book book = bookMap.get(isbn);
        if (book !=null)
        {
            book.setAuthor(newAuthor);
        }
    }
    public void updateBookYear(String isbn, int newYear){
        Book book = bookMap.get(isbn);
        if (book !=null)
        {
            book.setPublicationYear(newYear);
        }
    }
    public Book getBookByIsbn(String isbn) {
        return bookMap.get(isbn);
    }
        public List<Book> searchByTitle(String title) {
            List<Book> result = new ArrayList<>();
            for (Book book : bookMap.values()) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    result.add(book);
                }
            }
            return result;
        }

        // Search books by author
        public List<Book> searchByAuthor(String author) {
            List<Book> result = new ArrayList<>();
            for (Book book : bookMap.values()) {
                if (book.getAuthor().equalsIgnoreCase(author)) {
                    result.add(book);
                }
            }
            return result;
        }

        // Display all books
        public void displayAllBooks() {
        logger.info("All Books in the Repository:");
            for (Book book : bookMap.values()) {
                logger.info(book.toString());

            }
        }
    }
