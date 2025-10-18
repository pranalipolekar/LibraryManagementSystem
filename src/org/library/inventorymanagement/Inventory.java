package org.library.inventorymanagement;

import org.library.bookmanagement.Book;
import org.library.bookmanagement.BookRepository;

import java.util.logging.Logger;

public class Inventory {
    private static final Logger logger  = Logger.getLogger(Inventory.class.getName());
    private BookRepository bookRepository;
    public Inventory(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public boolean isBookAvailable(String isbn) {
       Book book = bookRepository.getBookByIsbn(isbn);
         return book != null && !book.isBorrowed();
    }


    public void checkoutCopy(String isbn) {
        Book book = bookRepository.getBookByIsbn(isbn);
        if (book != null && !book.isBorrowed()) {
            book.setBorrowed(true);
        } else {
            logger.info("Book not available for checkout: " + isbn);
        }
    }

    public void returnCopy(String isbn) {
        Book book = bookRepository.getBookByIsbn(isbn);
        if (book != null && book.isBorrowed()) {
            book.setBorrowed(false);
        } else {
            logger.info("Book not found or not borrowed: " + isbn);
        }
    }


}
