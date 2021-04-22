package id.ac.umn.icemoney.dao

import androidx.room.Dao
import androidx.room.Query
import id.ac.umn.icemoney.entity.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM `user` WHERE id = :id")
    fun getUserByID(id: Long): User

    @Query("SELECT * FROM `user` WHERE username = :username")
    fun getUserByUsername(username: String): User
}