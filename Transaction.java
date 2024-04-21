/*
Project     : Library System
Author      : Hamid Maddi
Created on  : 04/06/2024
Description : This class will be used to manipulate transactions in the library system. 
*/

import java.util.Date;
import java.io.Serializable;

public class Transaction implements Serializable {
    // Attributes
    private static final long serialVersionUID = 3L;
    private int transactionId;
    private int patronId;
    private int bookId;
    private Date transactionDate;

    // default constructor
    public Transaction() {}

    // constructor
    public Transaction(int transactionId, int patronId, int bookId, Date transactionDate) {
        this.transactionId = transactionId;
        this.patronId = patronId;
        this.bookId = bookId;
        this.transactionDate = transactionDate;
    }

    // getter methods
    public int getTransactionID() {
        return transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getPatronID() {
        return patronId;
    }

    public int getBookID() {
        return bookId;
    }

    // setter methods
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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
