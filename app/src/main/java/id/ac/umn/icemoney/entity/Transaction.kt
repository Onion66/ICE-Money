package id.ac.umn.icemoney.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey                             val id: String,
    @ColumnInfo(name = "amount")            val amount: Long,
    @ColumnInfo(name = "category")          val category: String,
    @ColumnInfo(name = "date")              val date: String,
    @ColumnInfo(name = "is_income")         val isIncome: Boolean = true,
    @ColumnInfo(name = "name")              val name: String,
    @ColumnInfo(name = "payment_method")    val paymentMethod: String,
) : Serializable
