package id.ac.umn.icemoney.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import id.ac.umn.icemoney.entity.Transaction

@Dao
interface TransactionDAO {
    @Query("SELECT * FROM `transaction`")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM `transaction`")
    fun getTransaction(): List<Transaction>
    
    @Query("SELECT * FROM `transaction`")
    suspend fun getTransactions(): List<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM `transaction`")
    suspend fun deleteAllTransaction()
}