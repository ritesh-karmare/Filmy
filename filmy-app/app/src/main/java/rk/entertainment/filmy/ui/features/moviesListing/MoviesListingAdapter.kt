package rk.entertainment.filmy.ui.features.moviesListing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import rk.entertainment.filmy.R
import rk.entertainment.filmy.data.models.movieList.MoviesListData
import rk.entertainment.filmy.data.repository.APIUtils
import rk.entertainment.filmy.databinding.ItemMovieBinding
import rk.entertainment.filmy.ui.features.movieDetails.MovieDetailsActivity
import rk.entertainment.filmy.utils.GlideApp
import rk.entertainment.filmy.utils.UIUtils.dpToPx
import java.util.*

class MoviesListingAdapter(private val context: Context) : RecyclerView.Adapter<MoviesViewHolder>() {

    private val moviesList: MutableList<MoviesListData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val movieItemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val data = moviesList[position]
        val voteAverage = data.voteAverage?.toString()
        val posterPath = data.posterPath
        val posterUrl = APIUtils.IMAGE_BASE_URL + APIUtils.POSTER_IMAGE_SIZE + posterPath

        holder.itemBinding.tvMovieRating.text = voteAverage

        if (posterPath != null) {
            GlideApp.with(context)
                    .load(posterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.itemBinding.ivMoviePoster)

        } else
            holder.itemBinding.ivMoviePoster.setImageResource(R.drawable.loading)

        val img = ContextCompat.getDrawable(context,R.drawable.ic_star)
        img?.setBounds(0, 0, dpToPx(20f, context), dpToPx(20f, context))

        holder.itemBinding.tvMovieRating.setCompoundDrawables(img, null, null, null)

        holder.itemBinding.cvItemMovie.setOnClickListener {
            val clickedData = moviesList[position]
            context.startActivity(Intent(context, MovieDetailsActivity::class.java).putExtra("movieId", clickedData.id))
        }
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
        while (itemCount > 0) remove(item)
    }

    private val item: MoviesListData
        private get() = moviesList[0]

    init {
        moviesList = ArrayList()
    }
}

class MoviesViewHolder(val itemBinding: ItemMovieBinding) : RecyclerView.ViewHolder(itemBinding.root)