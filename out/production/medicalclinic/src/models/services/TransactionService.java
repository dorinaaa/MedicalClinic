package models.services;

import models.beans.Patient;
import models.beans.Transaction;
import models.dao.TransactionDao;

import java.util.List;

public class TransactionService {

    public void registerTransaction(Transaction transaction) throws ClassNotFoundException {
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.insertTransaction(transaction);
    }
    public List<Transaction> getAllTransactions() throws ClassNotFoundException {
        TransactionDao transactionDao = new TransactionDao();
        return transactionDao.selectTransactions(0, null);
    }

    public List<Transaction> getUserTransactions(int role) throws ClassNotFoundException {
        TransactionDao transactionDao = new TransactionDao();
        return transactionDao.selectTransactions(role, null);
    }

    public List<Transaction> getPatientTransactions(Patient patient) throws ClassNotFoundException {
        TransactionDao transactionDao = new TransactionDao();
        return transactionDao.selectTransactions(0, patient);
    }
}
