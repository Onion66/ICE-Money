package id.ac.umn.icemoney.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.umn.icemoney.entity.Transaction

@Dao
interface TransactionDAO {
    @Query("SELECT * FROM `transaction`")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTransaction(transaction: Transaction)

    @Delete
    fun deleteTransaction(transaction: Transaction)

    @Update
    fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM `transaction`")
    fun deleteAllTransaction()
}