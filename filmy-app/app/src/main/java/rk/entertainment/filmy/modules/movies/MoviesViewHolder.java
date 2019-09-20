package rk.entertainment.filmy.modules.movies;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.utils.customViews.PosterImageView;

public class MoviesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cv_item_movie)
    public MaterialCardView cv_item_movie;

    @BindView(R.id.iv_movie_poster)
    public PosterImageView iv_movie_poster;

    @BindView(R.id.tv_movie_rating)
    public TextView tv_movie_rating;

    MoviesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
