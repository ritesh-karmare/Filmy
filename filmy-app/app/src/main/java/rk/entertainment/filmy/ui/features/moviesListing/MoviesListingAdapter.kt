package rk.entertainment.filmy.ui.features.moviesListing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.data.network.IMAGE_BASE_URL
import rk.entertainment.filmy.data.network.POSTER_IMAGE_SIZE
import rk.entertainment.filmy.databinding.ItemMovieBinding
import rk.entertainment.filmy.utils.GlideApp
import rk.entertainment.filmy.utils.UIUtils

class MoviesListingAdapter(val listener: MovieClickListener) :
    RecyclerView.Adapter<MoviesListingAdapter.MoviesViewHolder>() {

    private val moviesList = ArrayList<MoviesListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val movieItemBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val data = moviesList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun add(r: MoviesListData) {
        moviesList.add(r)
        notifyItemInserted(moviesList.size - 1)
    }

    fun addAll(moveResults: List<MoviesListData>?) {
        moviesList.addAll(moveResults!!)
        notifyItemInserted(moviesList.size - 1)
    }

    private fun remove(r: MoviesListData) {
        val position = moviesList.indexOf(r)
        if (position > -1) {
            moviesList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    //  Remove all elements from the list.
    fun clear() {
        moviesList.clear()
        notifyDataSetChanged()
        // while (itemCount > 0) remove(item)
    }

    private val item: MoviesListData
        get() = moviesList[0]

    inner class MoviesViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MoviesListData) {

            val context = binding.root.context

            val voteAverage = data.voteAverage?.toString()
            val posterPath = data.posterPath
            val posterUrl = IMAGE_BASE_URL + POSTER_IMAGE_SIZE + posterPath

            binding.tvMovieRating.text = voteAverage

            if(posterPath != null) {
                GlideApp.with(context)
                    .load(posterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(binding.ivMoviePoster)

            } else
                binding.ivMoviePoster.setImageResource(R.drawable.loading)

            val img = ContextCompat.getDrawable(context, R.drawable.ic_star)
            img?.setBounds(
                0, 0,
                UIUtils.dpToPx(20f, context),
                UIUtils.dpToPx(20f, context)
            )

            binding.tvMovieRating.setCompoundDrawables(img, null, null, null)

            binding.cvItemMovie.setOnClickListener {
                data.id?.let { listener.onMovieItemClicked(it) }
            }


        }
    }
}

interface MovieClickListener {
    fun onMovieItemClicked(movieId: Int)
}

