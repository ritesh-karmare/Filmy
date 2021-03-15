package rk.entertainment.filmy.view.features.movieDetails

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.moviesDetails.VideosData
import rk.entertainment.filmy.databinding.ItemVideosBinding
import rk.entertainment.filmy.utils.GlideApp

class VideosAdapter(private val activity: Activity) : RecyclerView.Adapter<VideosViewHolder>() {

    private val videosDataList: ArrayList<VideosData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding = ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val data = videosDataList[position]
        val videoKey = data.key

        if (videoKey != null) {
            val videoPosterUrl = "https://img.youtube.com/vi/$videoKey/mqdefault.jpg"
            GlideApp.with(activity)
                    .load(videoPosterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.binding.ivVideoPoster)
        } else
            holder.binding.ivVideoPoster.setImageResource(R.drawable.loading)

        holder.binding.cvItemVideo.setOnClickListener {
            val clickedData = videosDataList[position]
            if (clickedData.key != null)
                activity.startActivity(Intent(activity, TrailerActivity::class.java).putExtra("video_id", clickedData.key))
            else
                Toast.makeText(activity, activity.getString(R.string.error_video_unavailable), Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return videosDataList.size
    }

    fun add(r: VideosData) {
        videosDataList.add(r)
        notifyItemInserted(videosDataList.size - 1)
    }

    fun addAll(moveResults: List<VideosData>?) {
        videosDataList.addAll(moveResults!!)
        notifyItemInserted(videosDataList.size - 1)
    }

    private fun remove(r: VideosData) {
        val position = videosDataList.indexOf(r)
        if (position > -1) {
            videosDataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    //  Remove all elements from the list.
    fun clear() {
        while (itemCount > 0) {
            remove(item)
        }
    }

    private val item: VideosData
        get() = videosDataList[0]
}

class VideosViewHolder(val binding: ItemVideosBinding) : RecyclerView.ViewHolder(binding.root)