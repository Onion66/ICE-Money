package id.ac.umn.icemoney.ui.home

import android.app.Application
import androidx.lifecycle.*
import id.ac.umn.icemoney.database.DataModule
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.repository.TransactionRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var transactionList: LiveData<List<Transaction>>
    private lateinit var transactionRepo: TransactionRepo

    init {
        val transactionDAO = DataModule.getDatabase(application).transactionDao()
        transactionRepo = TransactionRepo(transactionDAO)
        transactionList = transactionRepo.getAllTransactions()
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepo.addTransaction(transaction)
        }
    }
}