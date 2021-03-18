package com.example.shaadi.network

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.assignment1.util.Coroutines
import com.example.shaadi.models.Result
import com.example.shaadi.models.User
import com.example.shaadi.room.UserDatabase
import com.example.shaadi.room.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val context: Context): ViewModel() {

    var mutableList : MutableLiveData<List<User>> = MutableLiveData()
    var liveData: LiveData<List<User>> = mutableList
    var newMutable : MutableLiveData<List<User>> = MutableLiveData()
    var newLiveData: LiveData<List<User>> = newMutable

    val userRepo = UserRepository(context)


    fun getUsers() {
        Coroutines.IO {
            val users = userRepo.getAllUsers()
            if (!(users.isNotEmpty())) {
                fetchUsers()
            } else {
                mutableList.postValue(users)
            }
        }
    }

    fun changeStatus(user: User) {
        userRepo.updateUser(user)
    }

    fun fetchUsers(): LiveData<List<User>> {
        Coroutines.IO {

            if (!(CheckInternet.Connection(context))) {
                Coroutines.Main {
                    Toast.makeText(context,"Check Internet Connection.",Toast.LENGTH_SHORT).show()
                }
            }
            else {
                val response = userRepo.fetchUser()
                if (response.isSuccessful) {
                    var users = ArrayList<User>()
                    for (r in response.body()!!.results) {
                        var user = User(0,r.gender,r.name.title,r.name.first,r.name.last,r.location.city,r.location.state,r.location.country,r.picture.large,r.picture.medium,r.picture.thumbnail,r.dob.date,r.dob.age,0)
                        userRepo.insertUser(user)
                        users.add(user)
                    }
                    if (users.size > 0) {
                        newMutable.postValue(users)
                    }
                }
                else {
                    Coroutines.Main {
                        Toast.makeText(context,"Something Went Wrong.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return newMutable
    }


}