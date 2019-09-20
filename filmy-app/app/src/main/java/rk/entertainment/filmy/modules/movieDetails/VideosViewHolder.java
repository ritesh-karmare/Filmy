package rk.entertainment.filmy.modules.movieDetails;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.utils.customViews.PosterImageView;

class VideosViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cv_item_video)
    MaterialCardView cvVideo;

    @BindView(R.id.iv_video_poster)
    PosterImageView ivVideoPoster;

    VideosViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}