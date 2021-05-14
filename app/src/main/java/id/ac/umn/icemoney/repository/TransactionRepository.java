package id.ac.umn.icemoney.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.umn.icemoney.dao.TransactionDAO;
import id.ac.umn.icemoney.database.TransactionRoomDatabase;
import id.ac.umn.icemoney.entity.Transaction;

public class TransactionRepository {
    private TransactionDAO daoTransaction;
    private LiveData<List<Transaction>> transactionList;

    public TransactionRepository(Application app){
        TransactionRoomDatabase db =
                TransactionRoomDatabase.getDatabase(app);
        daoTransaction = db.daoTransaction();
        transactionList = daoTransaction.getAllTransaction();
    }
    public LiveData<List<Transaction>> getTransactionList(){
        return this.transactionList;
    }
    public void insert(Transaction mhs){
        new insertAsyncTask(daoTransaction).execute(mhs);
    }
    public void deleteAll(){
        new deleteAllAsyncTask(daoTransaction).execute();
    }
    public void delete(Transaction mhs) {
        new deleteAsyncTask(daoTransaction).execute(mhs);
    }
    public void update(Transaction mhs) {
        new updateAsyncTask(daoTransaction).execute(mhs);
    }
    private static class insertAsyncTask extends
            AsyncTask<Transaction, Void, Void> {
        private TransactionDAO asyncDaoTransaction;
        insertAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }
        @Override
        protected Void doInBackground(final Transaction... transactions) {
            asyncDaoTransaction.insertTransaction(transactions[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends
            AsyncTask<Void, Void, Void> {
        private TransactionDAO asyncDaoTransaction;
        deleteAllAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }
        @Override
        protected Void doInBackground(final Void... voids) {
            asyncDaoTransaction.deleteAllTransaction();
            return null;
        }
    }
    private static class deleteAsyncTask extends
            AsyncTask<Transaction, Void, Void>{
        private TransactionDAO asyncDaoTransaction;
        deleteAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }
        @Override
        protected Void doInBackground(final Transaction... transactions) {
            asyncDaoTransaction.deleteTransaction(transactions[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends
            AsyncTask<Transaction, Void, Void>{
        private TransactionDAO asyncDaoTransaction;
        updateAsyncTask(TransactionDAO dao){
            this.asyncDaoTransaction = dao;
        }
        @Override
        protected Void doInBackground(final Transaction... transactions) {
            asyncDaoTransaction.updateTransaction(transactions[0]);
            return null;
        }
    }
}
