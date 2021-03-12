package com.example.shaadi

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.shaadi.databinding.CardViewBinding
import com.example.shaadi.models.User
import com.example.shaadi.utils.AdapterListener
import com.example.shaadi.utils.Constants
import com.squareup.picasso.Picasso

class CardAdapter(val users : List<User>, context: Context): RecyclerView.Adapter<CardAdapter.CardHolder>() {

    val adapterListener : AdapterListener = context as AdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        var params = view.layoutParams
        params.height = (parent.width + (parent.width * 0.2)).toInt()
        view.layoutParams = params
        return CardHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.card_view,parent
                ,false))
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.userBinding.user = users.get(position)
        holder.userBinding.name.text = users.get(position).title + ". "+ users.get(position).first + " " + users.get(position).last
        holder.userBinding.age.text = users.get(position).age.toString() + ", " + users.get(position).gender
        holder.userBinding.address.text = users.get(position).city + ", "+ users.get(position).state + ", " + users.get(position).country

        if (users.get(position).status == Constants.PENDING) {
            holder.userBinding.requestBox.visibility = VISIBLE
            holder.userBinding.requestStatus.visibility = GONE
        } else {
            holder.userBinding.requestBox.visibility = GONE
            holder.userBinding.requestStatus.visibility = VISIBLE
            if (users.get(position).status == Constants.ACCEPTED) {
                holder.userBinding.requestStatus.text = "Member Accepted"
            } else {
                holder.userBinding.requestStatus.text = "Member Declined"
            }
        }

        holder.userBinding.declineBox.setOnClickListener {
            var user = users.get(position)
            user.status = Constants.DECLINED
            adapterListener.onStatusChanged(user,position)
        }

        holder.userBinding.acceptBox.setOnClickListener {
            var user = users.get(position)
            user.status = Constants.ACCEPTED
            adapterListener.onStatusChanged(user,position)
        }
    }

    override fun getItemCount(): Int = users.size

    inner class CardHolder(val userBinding: CardViewBinding) : RecyclerView.ViewHolder(userBinding.root) {
    }
}