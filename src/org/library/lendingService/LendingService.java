package org.library.lendingService;

import org.library.bookmanagement.Book;
import org.library.bookmanagement.BookRepository;
import org.library.borrow.BorrowRecord;
import org.library.borrow.BorrowRepository;
import org.library.borrow.BorrowStatus;
import org.library.inventorymanagement.Inventory;
import org.library.patronmanagement.Patron;
import org.library.patronmanagement.PatronRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class LendingService {

        private static final Logger logger = Logger.getLogger(LendingService.class.getName());
        private BookRepository bookRepository;
        private PatronRepository patronRepository;
        private BorrowRepository borrowRepository;
        private Inventory inventory;

        // Constructor injects all dependencies
        public LendingService(BookRepository bookRepo,
                              PatronRepository patronRepo,
                              BorrowRepository borrowRepo,
                              Inventory inventory) {
            this.bookRepository = bookRepo;
            this.patronRepository = patronRepo;
            this.borrowRepository = borrowRepo;
            this.inventory = inventory;
        }

        // Checkout a book
        public boolean checkoutBook(String patronId, String isbn) {
            Patron patron = patronRepository.getPatronById(patronId);
            if (patron == null) {
                logger.info("Patron not found: " + patronId);
                return false;
            }
            Book book = bookRepository.getBookByIsbn(isbn);
            if (book == null) {
                logger.info("Book not found: " + isbn);
                return false;
            }
            if (!inventory.isBookAvailable(isbn)) {
                logger.info("Book is currently not available: " + isbn);
                return false;
            }

            // Mark book as borrowed
            inventory.checkoutCopy(isbn);

            // Create and save borrow record
            String recordId = UUID.randomUUID().toString();
            BorrowRecord record = new BorrowRecord(recordId,patronId,isbn);
            borrowRepository.addBorrowRecord(record);

            logger.info("Checkout successful! Book: " + book.getTitle() + " for Patron: " + patron.getPatronName());
            return true;
        }

        // Return a borrowed book
        public boolean returnBook(String patronId, String isbn) {
            // Get all borrow records for this patron
            List<BorrowRecord> records = borrowRepository.getRecordsByPatronId(patronId);

            // Find the active borrow record for this book
            BorrowRecord activeRecord = null;
            for (BorrowRecord record : records) {
                if (record.getBookId().equals(isbn) && record.getStatus() == BorrowStatus.BORROWED) {
                    activeRecord = record;
                    break;
                }
            }

            // If no active record is found, log and return false
            if (activeRecord == null) {
                logger.info("No active borrow record found for Patron: " + patronId + ", Book: " + isbn);
                return false;
            }

            // Update borrow record status and return date
            activeRecord.setStatus(BorrowStatus.RETURNED);
            activeRecord.setReturnDate(LocalDate.now());
            borrowRepository.updateBorrowRecord(activeRecord.getRecordId(), BorrowStatus.RETURNED, LocalDate.now());

            // Update inventory
            inventory.returnCopy(isbn);

            logger.info("Return successful! Book: " + isbn + " by Patron: " + patronId);
            return true;
        }
        // Get borrow history for a patron
        public List<BorrowRecord> getBorrowHistory(String patronId) {
            return borrowRepository.getRecordsByPatronId(patronId);
        }
    }


