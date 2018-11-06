package rk.entertainment.filmy.utils.rvUtils;


import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rk.entertainment.filmy.utils.UIUtils;

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;


    public VerticalItemDecoration(Context context) {
        this.space = UIUtils.dpToPx(8, context);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.right = space;
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.left = space*2;
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
            outRect.bottom = space*2;
    }
}