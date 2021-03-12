package com.example.shaadi.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("image")
fun setImageUrl(imageView: CircleImageView, url: String?) {
    Picasso.get().load(url).into(imageView)
}
