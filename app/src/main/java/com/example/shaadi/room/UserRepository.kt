package com.example.shaadi.room

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shaadi.models.Result
import com.example.shaadi.models.User
import com.example.shaadi.network.MyApi
import com.example.shaadi.network.ShaadiCalls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(val context: Context) {

    var newMutable : MutableLiveData<List<User>> = MutableLiveData()
    var userDao = UserDatabase.buildDatabase(context).userDao()

    fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun fetchUser(): Response<Result> {

        val r = MyApi.invoke().create(ShaadiCalls::class.java).getData().execute()
        return r
    }
}