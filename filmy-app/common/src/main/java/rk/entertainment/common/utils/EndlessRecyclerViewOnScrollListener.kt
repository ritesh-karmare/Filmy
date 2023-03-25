package rk.entertainment.common.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewOnScrollListener(private val gridLayoutManager: GridLayoutManager)
    : RecyclerView.OnScrollListener() {

    private val VISIBLE_THRESHOLD = 5
    private var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = gridLayoutManager.itemCount
        val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()
        val endHasBeenReached = lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount

        if (!loading && totalItemCount > 0 && endHasBeenReached) {
            loading = true
            onLoadMore()
        }
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    abstract fun onLoadMore()
}