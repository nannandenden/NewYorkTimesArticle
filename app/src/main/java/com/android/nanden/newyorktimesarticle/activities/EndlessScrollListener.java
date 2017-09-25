package com.android.nanden.newyorktimesarticle.activities;

import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by nanden on 9/24/17.
 */

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
    private static final String LOG_TAG = EndlessScrollListener.class.getSimpleName();
    // min number of item to have below the current scroll position
    private int visibleThreshold = 5;
    // the current offset index of data you have loaded
    private int currentPage = 0;
    // the total number of items in the dataset after the last load // last load....?
    private int previousTotalItemCount = 0;
    // true if we are still loading the last set of data to load
    private boolean isLoading = true;
    // set the start page index
    private int startingPageIndex = 0;

    public EndlessScrollListener() {
    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startingPageIndex) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startingPageIndex;
        // set the current offset index of data you have loaded to initially startingPage
        this.currentPage = startingPageIndex;
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {
        // if the totalItemCount is zero and previousTotalItemCount is not zero,
        // assume that the list is invalidated and should reset back to the initial state
        if (totalItemCount < previousTotalItemCount) {
            Log.d(LOG_TAG, "totalItemCount: " + totalItemCount + "\tpreviousTotalItemCount: " +
                    previousTotalItemCount);
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true;
            }
        }
        // if it is loading we check if total data set has changed
        // if total data set has changed, we conclude that it has been finish loading and we
        // update the current page number and total item count
        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            Log.d(LOG_TAG, "isLoading: " + isLoading + "\ttotalItemCount: " + totalItemCount +
                    "\tpreviousTotalItemCount: " + previousTotalItemCount);
            // conclude it has been finish loading and set the isLoading to false
            isLoading = false;
            // update the total item count
            previousTotalItemCount = totalItemCount;
            // update the current page number
            currentPage++;
        }

        // if it's not loading, check if item exceed the visibleThreshHold limit and need to load
        // more data. if we do need to load more data, we call onLoadMore method to fetch more data
        if (!isLoading && (firstVisibleItem+visibleItemCount+visibleThreshold >= totalItemCount)) {
            Log.d(LOG_TAG, "firstVisibleItem: " + firstVisibleItem + "\tvisibleItemCount: " +
                    visibleThreshold +
                    "\ttotalItemCount: " + totalItemCount);
            isLoading = onLoadMore(currentPage+1, totalItemCount);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }
    // define the process for loading the data for each list
    // return true if data is being loaded, false if no more data to load
    public abstract boolean onLoadMore(int page, int totalItemsCount);
}
