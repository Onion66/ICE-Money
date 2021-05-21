package id.ac.umn.icemoney.view.home

import android.app.Application
import androidx.lifecycle.*
import id.ac.umn.icemoney.database.DataModule
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.repository.TransactionRepo
import id.ac.umn.icemoney.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application): AndroidViewModel(application) {
    var transactionList: LiveData<List<Transaction>>
    private val transactionRepo: TransactionRepo
    val transactions = MutableLiveData<List<Transaction>>()

    init {
        val transactionDAO = DataModule.getDatabase(application).transactionDao()
        transactionRepo = TransactionRepo(transactionDAO)
        transactionList = transactionRepo.transactions
    }

    fun getTransaction() {
        transactions.value = transactionRepo.getTransaction()
    }

    fun getTransactions() {
        viewModelScope.launch {
            transactions.value = transactionRepo.getTransactions()
        }
    }

    fun setTransaction(data : MutableList<Transaction>) {
        transactions.value = data.toList()
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepo.addTransaction(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepo.updateTransaction(transaction)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepo.clearTransactions()
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepo.deleteTransaction(transaction)
        }
    }
}