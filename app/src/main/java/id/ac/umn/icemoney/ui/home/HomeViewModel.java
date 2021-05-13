package id.ac.umn.icemoney.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.umn.icemoney.entity.Transaction;
import id.ac.umn.icemoney.repository.TransactionRepository;

public class HomeViewModel extends AndroidViewModel {
    TransactionRepository transactionRepository;
    LiveData<List<Transaction>> transactionList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        transactionList = transactionRepository.getTransactionList();
    }
    public LiveData<List<Transaction>> getTransactionList(){
        return this.transactionList;
    }
    public void insert(Transaction transaction) {
        transactionRepository.insert(transaction);
    }
    public void deleteAll() {
        transactionRepository.deleteAll();
    }
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }
    public void update(Transaction transaction) {
        transactionRepository.update(transaction);
    }
}
