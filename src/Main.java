import org.library.bookmanagement.Book;
import org.library.bookmanagement.BookRepository;
import org.library.borrow.BorrowRecord;
import org.library.borrow.BorrowRepository;
import org.library.inventorymanagement.Inventory;
import org.library.lendingService.LendingService;
import org.library.patronmanagement.Patron;
import org.library.patronmanagement.PatronRepository;

import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {


        // --- Initialize repositories (only once) ---
        BookRepository bookRepo = new BookRepository();
        PatronRepository patronRepo = new PatronRepository();
        BorrowRepository borrowRepo = new BorrowRepository();
        Inventory inventory = new Inventory(bookRepo);

        // --- Add sample books ---
        bookRepo.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "ISBN001", 1937));
        bookRepo.addBook(new Book("1984", "George Orwell", "ISBN002", 1949));
        bookRepo.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "ISBN003", 1960));
        bookRepo.addBook(new Book("Atomic Habits", "James Clear", "ISBN004", 2018));
        //bookRepo.displayAllBooks();
        // --- Add sample patrons ---
        patronRepo.addPatron(new Patron("P001", "Alice", "alice@example.com"));
        patronRepo.addPatron(new Patron("P002", "Bob", "bob@example.com"));
        //patronRepo.displayAllPatrons();
        // --- Initialize LendingService ---
        LendingService lendingService = new LendingService(bookRepo, patronRepo, borrowRepo, inventory);

        // --- Checkout books safely ---
        safeCheckout(lendingService, "P001", "ISBN001"); // Alice borrows The Hobbit
        safeCheckout(lendingService, "P002", "ISBN002"); // Bob borrows 1984
        safeCheckout(lendingService,"P001","ISBN004");   // Alice borrows Atomic Habits

        // --- Display borrow history after checkout ---
        //logger.info("\n--- Borrow History After Checkout ---");
        printBorrowHistory(lendingService, "P001");
        printBorrowHistory(lendingService, "P002");

        // --- Return books safely ---
        safeReturn(lendingService, "P002", "ISBN002"); // Alice returns The Hobbit

        // --- Display borrow history after return ---
        logger.info("\n--- Borrow History After Return ---");
        printBorrowHistory(lendingService, "P001");
        printBorrowHistory(lendingService, "P002");

        patronRepo.removePatron("P002"); // Remove Bob
        // --- Display all books and patrons ---
       bookRepo.displayAllBooks();
       patronRepo.displayAllPatrons();
    }

    // Helper method for safe checkout
    private static void safeCheckout(LendingService service, String patronId, String isbn) {
        if (!service.checkoutBook(patronId, isbn)) {
            logger.info("Checkout failed for Patron: " + patronId + ", Book: " + isbn);
        }
    }

    // Helper method for safe return
    private static void safeReturn(LendingService service, String patronId, String isbn) {
        if (!service.returnBook(patronId, isbn)) {
            logger.info("Return failed for Patron: " + patronId + ", Book: " + isbn);
        }
    }

    // Helper function to print borrow history
    private static void printBorrowHistory(LendingService lendingService, String patronId) {
        List<BorrowRecord> history = lendingService.getBorrowHistory(patronId);
        logger.info("Borrow history for Patron ID: " + patronId);
        if (history.isEmpty()) {
            logger.info("No borrow records found.");
        } else {
            for (BorrowRecord record : history) {
                logger.info("Book ISBN: " + record.getBookId() +
                        ", Status: " + record.getStatus() +
                        ", Borrow Date: " + record.getBorrowDate() +
                        ", Return Date: " + record.getReturnDate());
            }
        }
    }
}
