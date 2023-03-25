package rk.entertainment.common.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import rk.entertainment.common.R
import rk.entertainment.common.databinding.ItemMovieBinding
import rk.entertainment.common.databinding.ItemRecommendedMovieBinding
import rk.entertainment.common.models.movieList.MoviesListData
import rk.entertainment.common.utils.GlideApp
import rk.entertainment.common.utils.IMAGE_BASE_URL
import rk.entertainment.common.utils.POSTER_IMAGE_SIZE
import rk.entertainment.common.utils.UIUtils

class MoviesListingAdapter(
    val context: Context,
    val listener: MovieClickListener, private val isFromMovieDetail: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val moviesList = ArrayList<MoviesListData>()

    val starDrawable: Drawable? by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_star)
    }

    val px20: Int by lazy {
        UIUtils.dpToPx(20f, context)
    }

    val drawableLoading: Int by lazy {
        R.drawable.loading
    }

    val dp100: Int by lazy {
        UIUtils.dpToPx(100f, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var holder : RecyclerView.ViewHolder? = null
        if(isFromMovieDetail){
         val recommendedMovieBinding = ItemRecommendedMovieBinding.inflate(inflater,parent,false)
         holder = RecommendedMoviesViewHolder(recommendedMovieBinding)
        }else {
            val movieItemBinding = ItemMovieBinding.inflate(inflater, parent, false)
            holder = MoviesViewHolder(movieItemBinding)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MoviesViewHolder) {
            val data = moviesList[position]
            holder.bind(data)
        } else if(holder is RecommendedMoviesViewHolder) {
            val data = moviesList[position]
            holder.bind(data)
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

    //  Remove all elements from the list.
    fun clear() {
        moviesList.clear()
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MoviesListData) {

            with(binding) {

                val voteAverage = data.voteAverage.toString()
                val posterPath = data.posterPath
                val posterUrl = IMAGE_BASE_URL + POSTER_IMAGE_SIZE + posterPath

                this.tvMovieRating.text = voteAverage

                if(posterPath != null) {
                    GlideApp.with(context)
                        .load(posterUrl)
                        .centerCrop()
                        .placeholder(drawableLoading)
                        .into(this.ivMoviePoster)
                } else
                    this.ivMoviePoster.setImageResource(drawableLoading)

                val img = starDrawable
                img?.setBounds(0, 0, px20, px20)
                this.tvMovieRating.setCompoundDrawables(img, null, null, null)

                this.cvItemMovie.setOnClickListener {
                    data.id?.let { listener.onMovieItemClicked(it) }
                }
            }
        }
    }

    inner class RecommendedMoviesViewHolder(val binding: ItemRecommendedMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MoviesListData) {

            with(binding) {

                val voteAverage = data.voteAverage.toString()
                val posterPath = data.posterPath
                val posterUrl = IMAGE_BASE_URL + POSTER_IMAGE_SIZE + posterPath

                this.tvMovieRating.text = voteAverage

                if(posterPath != null) {
                    GlideApp.with(context)
                        .load(posterUrl)
                        .centerCrop()
                        .placeholder(drawableLoading)
                        .into(this.ivMoviePoster)
                } else
                    this.ivMoviePoster.setImageResource(drawableLoading)

                val img = starDrawable
                img?.setBounds(0, 0, px20, px20)
                this.tvMovieRating.setCompoundDrawables(img, null, null, null)

                this.cvItemMovie.setOnClickListener {
                    data.id?.let { listener.onMovieItemClicked(it) }
                }
            }
        }
    }

}

interface MovieClickListener {
    fun onMovieItemClicked(movieId: Int)
}