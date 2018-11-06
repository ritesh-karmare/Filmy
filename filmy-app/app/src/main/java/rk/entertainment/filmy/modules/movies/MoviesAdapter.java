package rk.entertainment.filmy.modules.movies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rk.entertainment.filmy.R;
import rk.entertainment.filmy.models.movieList.MoviesListData;
import rk.entertainment.filmy.network.APIUtils;
import rk.entertainment.filmy.modules.movieDetails.MovieDetailsActivity;
import rk.entertainment.filmy.utils.GlideApp;
import rk.entertainment.filmy.utils.UIUtils;

public class MoviesAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MoviesListData> moviesList;

    public MoviesAdapter(Context context) {
        this.context = context;
        this.moviesList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewLoading = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MoviesViewHolder(viewLoading);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rv_holder, int position) {

        MoviesViewHolder holder = (MoviesViewHolder) rv_holder;
        MoviesListData data = moviesList.get(position);
        String voteAverage = data.getVoteAverage().toString();
        String posterPath = data.getPosterPath();
        String posterUrl = APIUtils.IMAGE_BASE_URL + APIUtils.POSTER_IMAGE_SIZE + posterPath;

        if (voteAverage != null)
            holder.tv_movie_rating.setText(voteAverage);

        if (posterPath != null) {
            GlideApp.with(context)
                    .load(posterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.iv_movie_poster);
        } else
            holder.iv_movie_poster.setImageResource(R.drawable.loading);

        Drawable img = context.getApplicationContext().getResources().getDrawable(R.drawable.ic_star);
        img.setBounds(0, 0, UIUtils.dpToPx(20, context), UIUtils.dpToPx(20, context));
        holder.tv_movie_rating.setCompoundDrawables(img, null, null, null);

        holder.cv_item_movie.setOnClickListener(view -> {
            MoviesListData data1 = moviesList.get(position);
            context.startActivity(new Intent(context, MovieDetailsActivity.class).
                    putExtra("movieId", data1.getId()));
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void add(final MoviesListData r) {
        moviesList.add(r);
        notifyItemInserted(moviesList.size() - 1);
    }

    public void addAll(List<MoviesListData> moveResults) {
        moviesList.addAll(moveResults);
        notifyItemInserted(moviesList.size() - 1);
    }

    private void remove(MoviesListData r) {
        int position = moviesList.indexOf(r);
        if (position > -1) {
            moviesList.remove(position);
            notifyItemRemoved(position);
        }
    }

    //  Remove all elements from the list.
    public void clear() {
        while (getItemCount() > 0)
            remove(getItem());
    }

    private MoviesListData getItem() {
        return moviesList.get(0);
    }
}
