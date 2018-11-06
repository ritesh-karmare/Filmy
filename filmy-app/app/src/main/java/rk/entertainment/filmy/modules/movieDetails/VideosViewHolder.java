package rk.entertainment.filmy.modules.movieDetails;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import rk.entertainment.filmy.R;
import rk.entertainment.filmy.utils.customViews.PosterImageView;

class VideosViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cv_item_video)
    CardView cvVideo;

    @BindView(R.id.iv_video_poster)
    PosterImageView ivVideoPoster;

    VideosViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}