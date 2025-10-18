package org.library.borrow;

import java.time.LocalDate;
import java.util.*;
import java.lang.*;
import java.util.logging.*;
public class BorrowRepository {
    private static final Logger logger = Logger.getLogger(BorrowRepository.class.getName());
    private Map<String, BorrowRecord> borrowMap = new HashMap<>();

    // Add a new borrow record
    public void addBorrowRecord(BorrowRecord record) {
        borrowMap.put(record.getRecordId(), record);
    }

    // Get a borrow record by record ID
    public BorrowRecord getBorrowRecord(String recordId) {
        return borrowMap.get(recordId);
    }

    // Update borrow status or return date
    public void updateBorrowRecord(String recordId, BorrowStatus status, LocalDate returnDate) {
        BorrowRecord record = borrowMap.get(recordId);
        if (record != null) {
            record.setStatus(status);
            record.setReturnDate(returnDate);
        } else {
            logger.info("BorrowRecord not found: " + recordId);
        }
    }

    // Get all borrow records of a patron
    public List<BorrowRecord> getRecordsByPatronId(String patronId) {
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord record : borrowMap.values()) {
            if (record.getPatronId().equals(patronId)) {
                result.add(record);
            }
        }
        return result;
    }

    // Get all borrow records of a book
    public List<BorrowRecord> getRecordsByBookId(String bookId) {
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord record : borrowMap.values()) {
            if (record.getBookId().equals(bookId)) {
                result.add(record);
            }
        }
        return result;
    }

    // Display all borrow records
    public void displayAllRecords() {
        for (BorrowRecord record : borrowMap.values()) {
           logger.info(record.toString());
        }
    }
}
