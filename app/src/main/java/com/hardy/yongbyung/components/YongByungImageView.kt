package com.hardy.yongbyung.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hardy.yongbyung.extensions.dpToIntPx

class YongByungImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatImageView(
    context, attrs, defStyleAttr
) {
    companion object {
        @JvmStatic
        @BindingAdapter(
            value = ["src", "isCircular", "radius", "placeholder", "cacheable"],
            requireAll = false
        )
        fun bind(
            imageView: YongByungImageView,
            src: String?,
            isCircular: Boolean? = null,
            radius: Float = 0f,
            placeholder: Drawable? = null,
            cacheable: Boolean = true
        ) {
            Glide.with(imageView)
                .load(src)
                .placeholder(placeholder)
                .error(placeholder)
                .apply {
                    if (!cacheable) {
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                        skipMemoryCache(true)
                    }
                    if (isCircular == true) {
                        apply(RequestOptions.circleCropTransform())
                    } else if (radius > 0) {
                        transform(RoundedCorners(imageView.context.dpToIntPx(radius)))
                    }
                }.into(imageView)
        }

        @JvmStatic
        @BindingAdapter(
            value = ["src", "isCircular", "radius", "placeholder"],
            requireAll = false
        )
        fun bind(
            imageView: YongByungImageView,
            src: Int?,
            isCircular: Boolean? = null,
            radius: Float = 0f,
            placeholder: Drawable? = null,
        ) {
            Glide.with(imageView)
                .load(src)
                .placeholder(placeholder)
                .error(placeholder)
                .apply {
                    if (isCircular == true) {
                        apply(RequestOptions.circleCropTransform())
                    } else if (radius > 0) {
                        transform(RoundedCorners(imageView.context.dpToIntPx(radius)))
                    }
                }.into(imageView)
        }

        @JvmStatic
        @BindingAdapter(
            value = ["src", "isCircular", "radius", "placeholder", "cacheable"],
            requireAll = false
        )
        fun bind(
            imageView: YongByungImageView,
            src: Uri?,
            isCircular: Boolean? = null,
            radius: Float = 0f,
            placeholder: Drawable? = null,
            cacheable: Boolean = true
        ) {
            Glide.with(imageView)
                .load(src)
                .placeholder(placeholder)
                .error(placeholder)
                .apply {
                    if (!cacheable) {
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                        skipMemoryCache(true)
                    }
                    if (isCircular == true) {
                        apply(RequestOptions.circleCropTransform())
                    } else if (radius > 0) {
                        transform(RoundedCorners(imageView.context.dpToIntPx(radius)))
                    }
                }.into(imageView)
        }

        @JvmStatic
        @BindingAdapter(
            value = ["src", "isCircular", "radius", "placeholder"],
            requireAll = false
        )
        fun bind(
            imageView: YongByungImageView,
            src: Bitmap?,
            isCircular: Boolean? = null,
            radius: Float = 0f,
            placeholder: Drawable? = null,
        ) {
            Glide.with(imageView)
                .load(src)
                .placeholder(placeholder)
                .error(placeholder)
                .apply {
                    if (isCircular == true) {
                        apply(RequestOptions.circleCropTransform())
                    } else if (radius > 0) {
                        transform(RoundedCorners(imageView.context.dpToIntPx(radius)))
                    }
                }.into(imageView)
        }

        @JvmStatic
        @BindingAdapter(
            value = ["src", "isCircular", "radius", "placeholder"],
            requireAll = false
        )
        fun bindResize(
            imageView: YongByungImageView,
            src: String?,
            isCircular: Boolean? = null,
            radius: Float = 0f,
            placeholder: Drawable? = null,
        ) {
            Glide.with(imageView)
                .load(src)
                .placeholder(placeholder)
                .error(placeholder)
                .apply {
                    if (isCircular == true) {
                        apply(RequestOptions.circleCropTransform())
                    } else if (radius > 0) {
                        transform(RoundedCorners(imageView.context.dpToIntPx(radius)))
                    }
                }.into(imageView)
        }
    }
}