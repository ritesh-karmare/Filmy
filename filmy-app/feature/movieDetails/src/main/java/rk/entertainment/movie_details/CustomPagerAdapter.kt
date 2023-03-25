package rk.entertainment.movie_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rk.entertainment.common.models.moviesDetails.BackdropData
import rk.entertainment.common.utils.BACKDROP_IMAGE_SIZE
import rk.entertainment.common.utils.GlideApp
import rk.entertainment.common.utils.IMAGE_BASE_URL
import rk.entertainment.common.utils.Logs
import rk.entertainment.movie_details.databinding.PagerItemBinding


class CustomPagerAdapter(
    private val mContext: Context,
    private val backdropDataList: ArrayList<BackdropData>
) : RecyclerView.Adapter<CustomPagerAdapter.ImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PagerItemBinding.inflate(inflater, parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(backdropDataList[position])
    }

    override fun getItemCount(): Int {
        return backdropDataList.size
    }

    inner class ImagesViewHolder(val binding: PagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: BackdropData) {
            if(data.filePath.isNotEmpty()) {
                val backDropUrl =
                    IMAGE_BASE_URL + BACKDROP_IMAGE_SIZE + backdropDataList[position].filePath
                Logs.d("instantiateItem backDrop %s", backDropUrl)
                GlideApp.with(mContext)
                    .load(backDropUrl)
                    .into(binding.ivPager)
            }
        }

    }
}