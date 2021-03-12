package com.example.shaadi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shaadi.models.User

@Dao
interface UserDao {

    @Query("select * from User")
    fun getAllUsers(): List<User>

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)
}