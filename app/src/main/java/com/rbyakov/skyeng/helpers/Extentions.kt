package com.rbyakov.skyeng.helpers

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rbyakov.skyeng.R
import com.rbyakov.skyeng.di.DI
import com.rbyakov.skyeng.di.ViewModelFactory


fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(
        this,
        toothpick.Toothpick.openScope(DI.APP_SCOPE)
            .getInstance(ViewModelFactory::class.java)
    )
        .get(viewModelClass)

fun ImageView.loadImage(preview: String?, image: String?) {

    val thumbnailRequest: RequestBuilder<Drawable> =
        Glide
            .with(this)
            .load(normalizeUrl(preview))

    Glide.with(this)
        .load(normalizeUrl(image))
        .thumbnail(thumbnailRequest)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .into(this)
}

private fun normalizeUrl(url: String?): String? {
    return "https:$url"
}