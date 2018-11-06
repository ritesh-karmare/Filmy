package rk.entertainment.filmy.modules.movieDetails;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rk.entertainment.filmy.R;
import rk.entertainment.filmy.models.moviesDetails.VideosData;
import rk.entertainment.filmy.utils.GlideApp;

class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<VideosData> videosDataList;

    VideosAdapter(Activity activity) {
        this.activity = activity;
        videosDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewLoading = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_videos, parent, false);
        return new VideosViewHolder(viewLoading);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        VideosViewHolder holder = (VideosViewHolder) viewHolder;
        VideosData data = videosDataList.get(position);
        String videoKey = data.getKey();
        if (videoKey != null) {
            String videoPosterUrl = "https://img.youtube.com/vi/" + videoKey + "/mqdefault.jpg";
            GlideApp.with(activity)
                    .load(videoPosterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .into(holder.ivVideoPoster);
        } else
            holder.ivVideoPoster.setImageResource(R.drawable.loading);

        holder.cvVideo.setOnClickListener(view -> {
            VideosData clickedData = videosDataList.get(position);
            if (clickedData.getKey() != null)
                activity.startActivity(new Intent(activity, TrailerActivity.class).putExtra("video_id", clickedData.getKey()));
            else
                Toast.makeText(activity, activity.getString(R.string.error_video_unavailable), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return videosDataList.size();
    }

    public void add(final VideosData r) {
        videosDataList.add(r);
        notifyItemInserted(videosDataList.size() - 1);
    }

    void addAll(List<VideosData> moveResults) {
        videosDataList.addAll(moveResults);
        notifyItemInserted(videosDataList.size() - 1);
    }

    private void remove(VideosData r) {
        int position = videosDataList.indexOf(r);
        if (position > -1) {
            videosDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    //  Remove all elements from the list.
    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem());
        }
    }

    private VideosData getItem() {
        return videosDataList.get(0);
    }


}
