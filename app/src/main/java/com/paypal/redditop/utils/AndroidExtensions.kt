package com.paypal.redditop.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.paypal.redditop.core.RedditopConstants.validImageExtensions
import java.util.concurrent.atomic.AtomicBoolean


fun ImageView.load(
    context: Context,
    url: String,
    thumbnailUrl: String? = null,
    cornerRadius: Int? = null,
    fitCenter: Boolean = false
) {
    var builder = Glide.with(context).load(url)
    thumbnailUrl?.let {
        builder.thumbnail(
            Glide.with(context)
                .load(thumbnailUrl)
        )
    }


    cornerRadius?.let {
        builder
            .transform(RoundedCorners(it))
    }

    builder
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun String.isValidImageUrl(): Boolean {
    return validImageExtensions.find { this.contains(it) } != null
}

/**
 * Method that replaces the [View.setOnClickListener] adding a safe check to avoid fast double
 * clicking on the view.
 * This code was extracted from
 * @see <a href="https://medium.com/@masaaki.iwaguchi/android-how-to-prevent-multiple-view-clicks-using-databinding-and-kotlin-extensions-5d5859071b4b">medium link</a>
 */
fun View.setOnSingleClickListener(clickListener: ((View) -> Unit)?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

/**
 * Class created to complete the [View.setOnSingleClickListener] functionality. It will hold
 * the value of the last time a view was clicked and if its less than [intervalMs] it will ignore
 * such action. If it's greater it will execute [clickListener]
 */
class OnSingleClickListener(
    private val clickListener: (View) -> Unit,
    private val intervalMs: Long = 1000
) : View.OnClickListener {

    //Atomic boolean used in case the operations happens on different threads.
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.invoke(v)
            }
        }
    }
}
