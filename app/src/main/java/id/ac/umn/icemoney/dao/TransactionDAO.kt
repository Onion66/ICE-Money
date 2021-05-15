package id.ac.umn.icemoney.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.umn.icemoney.entity.Transaction

@Dao
interface TransactionDAO {
    @Query("SELECT * FROM `transaction`")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM `transaction`")
    suspend fun deleteAllTransaction()
}