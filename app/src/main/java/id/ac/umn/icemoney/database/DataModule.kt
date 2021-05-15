package id.ac.umn.icemoney.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.umn.icemoney.dao.TransactionDAO
import id.ac.umn.icemoney.dao.UserDAO
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.entity.User

@Database(entities = [User::class, Transaction::class], version = 1, exportSchema = false)
abstract class DataModule: RoomDatabase() {
    abstract fun userDao() : UserDAO
    abstract fun transactionDao() : TransactionDAO

    companion object {
        @Volatile
        private var INSTANCE : DataModule? = null

        fun getDatabase(context : Context) : DataModule {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataModule::class.java,
                    "expense_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}