package ru.geekbrain.android.translator.domain

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.geekbrain.android.translator.model.ImageLoader

class ImageLoaderImpl: ImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container)
            .load(url)
            .into(container)
    }
}