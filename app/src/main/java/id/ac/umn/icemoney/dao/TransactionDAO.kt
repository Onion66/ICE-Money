package id.ac.umn.icemoney.dao

import androidx.room.Dao
import androidx.room.Query
import id.ac.umn.icemoney.entity.Transaction

@Dao
interface TransactionDAO {
    @Query("SELECT * FROM `transaction`")
    fun getTransactionList(): List<Transaction>
}