package org.library.borrow;

import java.time.LocalDate;

public class BorrowRecord  {
    private String recordId;
    private String patronId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private BorrowStatus status;

    public BorrowRecord(String recordId,String patronId,String bookId){
        this.recordId=recordId;
        this.patronId=patronId;
        this.bookId =bookId;
        this.status = BorrowStatus.BORROWED;
        this.borrowDate=LocalDate.now();
    }
    public String getRecordId() {
        return recordId;
    }
    public String getPatronId() {
        return patronId;
    }
    public String getBookId() {
        return bookId;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public BorrowStatus getStatus() {
        return status;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "recordId='" + recordId + '\'' +
                ", patronId='" + patronId + '\'' +
                ", BookId='" + bookId + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", status=" + status +
                '}';
    }
}
