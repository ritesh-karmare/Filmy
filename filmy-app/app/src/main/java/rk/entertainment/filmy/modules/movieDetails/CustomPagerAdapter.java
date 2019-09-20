package rk.entertainment.filmy.modules.movieDetails;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import rk.entertainment.filmy.R;
import rk.entertainment.filmy.models.moviesDetails.BackdropData;
import rk.entertainment.filmy.network.APIUtils;
import rk.entertainment.filmy.utils.GlideApp;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<BackdropData> backdropDataList;

    CustomPagerAdapter(Context context, List<BackdropData> backdropDataList) {
        mContext = context;
        this.backdropDataList = backdropDataList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addAll(List<BackdropData> backdropDataList) {
        this.backdropDataList.addAll(backdropDataList);
        if (this.backdropDataList.size() == 0)
            this.backdropDataList.add(0, new BackdropData());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return backdropDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.iv_pager);
        BackdropData data = backdropDataList.get(position);
        if (data.getFilePath() != null) {
            String backDropUrl = APIUtils.IMAGE_BASE_URL
                    + APIUtils.BACKDROP_IMAGE_SIZE + backdropDataList.get(position).getFilePath();

            Log.i("CustomPagerAdapter ", "instantiateItem backDrop "+backDropUrl);

            GlideApp.with(mContext)
                    .asBitmap()
                    .load(backDropUrl)
                    .placeholder(R.drawable.loading)
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setImageBitmap(resource);
                            return false;
                        }
                    })
                    .into(imageView);
        } else {
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageResource(R.drawable.loading);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}