package com.android.nanden.newyorktimesarticle.activities;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by nanden on 9/24/17.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final String LOG_TAG = EndlessRecyclerViewScrollListener.class.getSimpleName();
    // the min amount of items to have below the current scroll position before loading more.
    private int visibleThreshold = 5;
    // the current page you are loading
    private int currentPage = 0;
    // total number of items that have been loaded
    private int previousTotalItemCount = 0;
    // true if we are still waiting for the last set of data to load
    private boolean loading = true;
    // starting page index....since it's not always 0...?
    private int startingPageIndex = 0;

    private RecyclerView.LayoutManager layoutManager;


    public EndlessRecyclerViewScrollListener(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager gridLayoutManager) {
        this.layoutManager = gridLayoutManager;
        visibleThreshold = visibleThreshold * gridLayoutManager.getSpanCount();
        Log.d(LOG_TAG, "gridview getSpanCount: " + gridLayoutManager.getSpanCount());
        Log.d(LOG_TAG, "visibleThreshold: " + visibleThreshold);

    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.layoutManager = staggeredGridLayoutManager;
        visibleThreshold = visibleThreshold * staggeredGridLayoutManager.getSpanCount();
        Log.d(LOG_TAG, "staggeredGrid getSpanCount: " + staggeredGridLayoutManager.getSpanCount());
        Log.d(LOG_TAG, "visibleThreshold: " + visibleThreshold);
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
                Log.d(LOG_TAG, "i == 0, maxSize: " + maxSize);
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
                Log.d(LOG_TAG, "i != 0, maxSize: " + maxSize);
            }
        }
        return maxSize;
    }
    // this method is called the second during the scrolling is happening.
    // first check if we are waiting for the previous load to finish
    @Override
    public void onScrolled(RecyclerView recyclerView, int verticalScrollCount, int
            horizontalScrollCount) {
        Log.d(LOG_TAG, "onScrolled called");
        int lastVisibleItemPosition = 0;
        // get the number of items in the adapter bound to the parent RecyclerView.
        int totalItemCount = this.layoutManager.getItemCount();

        if (this.layoutManager instanceof StaggeredGridLayoutManager) {
            // findLastVisibleItemPositions: get the adapter position of the last visible view
            // for each span.(span...?)
            // setting findLastVisibleItemPositions(null) means, create a new int array with span
            // length and return the array
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) this.layoutManager)
                    .findLastVisibleItemPositions(null);
            Log.d(LOG_TAG, "lastVisibleItemPositions: " + lastVisibleItemPositions.toString());
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
            Log.d(LOG_TAG, "lastVisibleItemPosition: " + lastVisibleItemPosition);
        } else if (this.layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) this.layoutManager).findLastVisibleItemPosition();
        } else if (this.layoutManager instanceof  LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) this.layoutManager).findLastVisibleItemPosition();
        }

        // if the total item count is zero and the previousTotalItemCount is not zero,
        // assume the list iis invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            Log.d(LOG_TAG, "totalItemCount < previousTotalItemCount");
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        // if it's loading, check if the loaded item count has changed (by checking
        // totalItemCount > previousTotalItemCount),
        // if it has changed we conclude that it has finish loading and update the current page
        // number and total item count
        if (loading && (totalItemCount > previousTotalItemCount)) {
            Log.d(LOG_TAG, "loading");
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // of item isn't currently loading, check if the list have broke the visibleThreshold( by
        // checking lastVisibleItemPosition+visibleThreshold) > totalItemCount) and
        // list need to reload more data.
        // if do need to load some more data, call the onLoadMore method to fetch more data
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition+visibleThreshold) > totalItemCount) {
            Log.d(LOG_TAG, "!loading");
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }
    }
    // call this method whenever performing new search
    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }
    // define the process for actually loading more data
    protected abstract void onLoadMore(int page, int totalItemCount, RecyclerView recyclerView);
}
