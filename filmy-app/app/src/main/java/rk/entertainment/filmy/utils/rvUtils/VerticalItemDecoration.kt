package rk.entertainment.filmy.utils.rvUtils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import rk.entertainment.filmy.utils.UIUtils

class VerticalItemDecoration(context: Context) : ItemDecoration() {

    private val space: Int = UIUtils.dpToPx(8f, context)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,state: RecyclerView.State) {

        outRect.right = space
        if (parent.getChildAdapterPosition(view) == 0) outRect.left = space * 2
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) outRect.bottom = space * 2
    }

}