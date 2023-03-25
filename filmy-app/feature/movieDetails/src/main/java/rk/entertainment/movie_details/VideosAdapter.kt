package rk.entertainment.movie_details

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import rk.entertainment.common.models.moviesDetails.VideosData
import rk.entertainment.common.utils.GlideApp
import rk.entertainment.movie_details.databinding.ItemVideosBinding


class VideosAdapter(private val activity: Activity, private val videosList: ArrayList<VideosData>) :
    RecyclerView.Adapter<VideosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding = ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val data = videosList[position]
        val videoKey = data.key

        if(videoKey != null) {
            val videoPosterUrl = "https://img.youtube.com/vi/$videoKey/mqdefault.jpg"
            GlideApp.with(activity)
                .load(videoPosterUrl)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(holder.binding.ivVideoPoster)
        } else
            holder.binding.ivVideoPoster.setImageResource(R.drawable.loading)

        holder.binding.cvItemVideo.setOnClickListener {
            val clickedData = videosList[position]
            if(clickedData.key != null)
                activity.startActivity(
                    Intent(
                        activity,
                        TrailerActivity::class.java
                    ).putExtra("video_id", clickedData.key)
                )
            else
                Toast.makeText(
                    activity,
                    activity.getString(R.string.error_video_unavailable),
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    fun add(r: VideosData) {
        videosList.add(r)
        notifyItemInserted(videosList.size - 1)
    }

    fun addAll(moveResults: List<VideosData>?) {
        videosList.addAll(moveResults!!)
        notifyItemInserted(videosList.size - 1)
    }
}

class VideosViewHolder(val binding: ItemVideosBinding) : RecyclerView.ViewHolder(binding.root)