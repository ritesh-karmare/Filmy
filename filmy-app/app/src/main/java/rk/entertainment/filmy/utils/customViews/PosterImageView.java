package rk.entertainment.filmy.utils.customViews;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import rk.entertainment.filmy.modules.movieDetails.MovieDetailsActivity;

public class PosterImageView extends AppCompatImageView {

    int phoneWidth;
    int phoneHeight;
    DisplayMetrics displayMetrics;

    public PosterImageView(Context context) {
        super(context);
        getDisplayWidthHeight();
    }

    public PosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getDisplayWidthHeight();
    }

    public PosterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getDisplayWidthHeight();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        float posterProportion = 1.5f;

        if (getContext() instanceof MovieDetailsActivity) {


            viewWidth = (int) (phoneWidth * 0.25);
        }


        int finalViewHeight = (int) (viewWidth * posterProportion);
        setMeasuredDimension(viewWidth, finalViewHeight);
    }

    void getDisplayWidthHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        phoneWidth = displayMetrics.widthPixels;
        phoneHeight = displayMetrics.heightPixels;

    }

}
