package com.example.shaadi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shaadi.databinding.ActivityMainBinding
import com.example.shaadi.models.Result
import com.example.shaadi.models.User
import com.example.shaadi.network.CheckInternet
import com.example.shaadi.network.MainViewModel
import com.example.shaadi.network.MyApi
import com.example.shaadi.utils.AdapterListener
import com.kira.store.mvvm.crm.factory.UserFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition
import kotlin.math.log

class MainActivity : AppCompatActivity(), AdapterListener, SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding : ActivityMainBinding
    lateinit var viewModel : MainViewModel
    lateinit var adapter: CardAdapter
    var users = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this,UserFactory(applicationContext)).get(MainViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recycle.layoutManager = LinearLayoutManager(this)
        adapter = CardAdapter(users,this)
        binding.recycle.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener(this)

        viewModel.newLiveData.observe(this, Observer {
            for (i in it) {
                users.add(0,i)
            }
            adapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        })

        viewModel.liveData.observe(this, Observer {
            users.clear()
            users.addAll(it)
            adapter.notifyDataSetChanged()
        })
        viewModel.getUsers()
    }

    override fun onStatusChanged(user: User, position: Int) {
        viewModel.changeStatus(user)
        adapter.notifyItemChanged(position)
    }

    override fun onRefresh() {
        if (!CheckInternet.Connection(this))
        {
            Toast.makeText(this,"Check Internet Connection.", Toast.LENGTH_SHORT).show()
            binding.swipeRefresh.isRefreshing = false
            return
        }
        viewModel.fetchUsers()
    }
}