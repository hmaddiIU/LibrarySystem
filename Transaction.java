/*
Project     : Library System
Author      : Hamid Maddi
Created on  : 04/06/2024
Description : This class will be used to manipulate transactions in the library system. 
*/


import java.util.Date;

public class Transaction {
    private int transactionId;
    private int patronId;
    private int bookId;
    private Date transactionDate;

    // default constructor
    public Transaction() {}

    // constructor
    public Transaction(int transactionID, int patronId, int bookId, Date transactionDate) {
        this.transactionID = transactionID;
        this.patronId = patronId;
        this.bookId = bookId;
        this.transactionDate = transactionDate;
    }

    // Getter methods
    public int getTransactionID() {
        return transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getPatronId() {
        return patronId;
    }

    public int getBookId() {
        return bookId;
    }

    // setter methods
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
