package com.example.shaadi.network

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment1.util.Coroutines
import com.example.shaadi.models.Result
import com.example.shaadi.models.User
import com.example.shaadi.room.UserDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val context: Context): ViewModel() {

    var mutableList : MutableLiveData<List<User>> = MutableLiveData()
    var liveData: LiveData<List<User>> = mutableList
    var newMutable : MutableLiveData<List<User>> = MutableLiveData()
    var newLiveData: LiveData<List<User>> = newMutable
    var userDao = UserDatabase.buildDatabase(context).userDao()

    fun getUsers() {
        Coroutines.IO {
            val users = userDao.getAllUsers()
            if (!(users.isNotEmpty())) {
                fetchUsers()
            } else {
                mutableList.postValue(users)
            }
        }
    }

    fun changeStatus(user: User) {
        userDao.updateUser(user)
    }

    fun fetchUsers() {
        Coroutines.IO {

            if (!(checkNetWorkConnection(context))) {
                Coroutines.Main {
                    Toast.makeText(context,"Check Internet Connection.",Toast.LENGTH_SHORT).show()
                }
            }
            else {

                MyApi.invoke().getData().enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        var users = ArrayList<User>()
                        for (r in response.body()!!.results) {
                            var user = User(0,r.gender,r.name.title,r.name.first,r.name.last,r.location.city,r.location.state,r.location.country,r.picture.large,r.picture.medium,r.picture.thumbnail,r.dob.date,r.dob.age,0)
                            userDao.insertUser(user)
                            users.add(user)
                        }
                        if (users.size > 0) {
                            newMutable.value = users
                        }
                    }

                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        Toast.makeText(context,"Something Went Wrong.",Toast.LENGTH_SHORT).show()
                    }

                })
            }


        }
    }

    @SuppressLint("ServiceCast")
    fun checkNetWorkConnection(context: Context): Boolean{
        val connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

}