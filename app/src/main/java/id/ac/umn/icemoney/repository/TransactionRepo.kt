package id.ac.umn.icemoney.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.ac.umn.icemoney.dao.TransactionDAO
import id.ac.umn.icemoney.entity.Transaction

class TransactionRepo(private val transactionDAO: TransactionDAO) {
    val transactions : LiveData<List<Transaction>> = transactionDAO.getAllTransactions()

    fun getTransaction() : List<Transaction> = transactionDAO.getTransaction()

    suspend fun getTransactions() : List<Transaction> = transactionDAO.getTransactions()

    suspend fun addTransaction(transaction: Transaction) {
        transactionDAO.insertTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDAO.deleteTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDAO.updateTransaction(transaction)
    }

    suspend fun clearTransactions() {
        transactionDAO.deleteAllTransaction()
    }
}