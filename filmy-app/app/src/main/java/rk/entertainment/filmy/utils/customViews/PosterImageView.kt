package rk.entertainment.filmy.utils.customViews

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.appcompat.widget.AppCompatImageView
import rk.entertainment.filmy.view.features.movieDetails.MovieDetailsActivity

class PosterImageView : AppCompatImageView {
    private var phoneWidth = 0
    private var phoneHeight = 0

    constructor(context: Context?) : super(context!!) {
        getDisplayWidthHeight()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        getDisplayWidthHeight()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        getDisplayWidthHeight()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        val posterProportion = 1.5f
        if (context is MovieDetailsActivity) viewWidth = (phoneWidth * 0.25).toInt()
        val finalViewHeight = (viewWidth * posterProportion).toInt()
        setMeasuredDimension(viewWidth, finalViewHeight)
    }

    private fun getDisplayWidthHeight() {
        val displayMetrics = DisplayMetrics()
        if (context is Activity)
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        else if (context is ContextWrapper)
            ((context as ContextWrapper).baseContext as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        phoneWidth = displayMetrics.widthPixels
        phoneHeight = displayMetrics.heightPixels
    }
}