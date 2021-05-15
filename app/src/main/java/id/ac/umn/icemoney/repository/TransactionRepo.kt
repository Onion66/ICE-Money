package id.ac.umn.icemoney.repository

import androidx.lifecycle.LiveData
import id.ac.umn.icemoney.dao.TransactionDAO
import id.ac.umn.icemoney.entity.Transaction

class TransactionRepo(private val transactionDAO: TransactionDAO) {
    fun getAllTransactions(): LiveData<List<Transaction>> {
        return transactionDAO.getAllTransactions()
    }

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