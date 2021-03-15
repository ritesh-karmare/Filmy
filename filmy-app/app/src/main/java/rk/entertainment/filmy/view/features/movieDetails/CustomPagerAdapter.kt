package rk.entertainment.filmy.view.features.movieDetails

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.moviesDetails.BackdropData
import rk.entertainment.filmy.data.repository.APIUtils
import rk.entertainment.filmy.utils.GlideApp
import timber.log.Timber

class CustomPagerAdapter internal constructor(private val mContext: Context, private val backdropDataList: ArrayList<BackdropData>) : PagerAdapter() {

    private val mLayoutInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun addAll(backdropDataList: List<BackdropData>) {
        this.backdropDataList.addAll(backdropDataList)
        if (this.backdropDataList.isEmpty())
            this.backdropDataList.add(0, BackdropData())
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return backdropDataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false)
        try {
            val imageView = itemView.findViewById<ImageView>(R.id.iv_pager)
            val data = backdropDataList[position]

            if (data.filePath.isNotEmpty()) {

                val backDropUrl = APIUtils.IMAGE_BASE_URL + APIUtils.BACKDROP_IMAGE_SIZE + backdropDataList[position].filePath
                Timber.d("instantiateItem backDrop %s", backDropUrl)

                GlideApp.with(mContext)
                        .asBitmap()
                        .load(backDropUrl)
                        .placeholder(R.drawable.loading)
                        .transition(BitmapTransitionOptions.withCrossFade())
                        .listener(object : RequestListener<Bitmap?> {
                            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap?>, isFirstResource: Boolean): Boolean {
                                return false
                            }

                            override fun onResourceReady(resource: Bitmap?, model: Any, target: Target<Bitmap?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                                imageView.scaleType = ImageView.ScaleType.FIT_XY
                                imageView.setImageBitmap(resource)
                                return false
                            }
                        })
                        .into(imageView)

            } else {
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                imageView.setImageResource(R.drawable.loading)
            }

            container.addView(itemView)
        } catch (e: Exception) {
            Timber.e(e)
        }
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}