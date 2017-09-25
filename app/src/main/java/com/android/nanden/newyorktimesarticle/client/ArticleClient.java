package com.android.nanden.newyorktimesarticle.client;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.nanden.newyorktimesarticle.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by nanden on 9/19/17.
 */

public class ArticleClient {
    private static final String LOG_TAG = ArticleClient.class.getSimpleName();
    private AsyncHttpClient client;
    private RequestParams currentRequestNoPage;
    public ArticleClient() {
        this.client = new AsyncHttpClient();
    }

    public void getSearchResult(String query, JsonHttpResponseHandler handler, int pageCount
    ) {
        Log.d(LOG_TAG, "pageCount: " + pageCount);
        RequestParams params = new RequestParams();
        params.put("api-key", Constants.api_key);
        params.put("q", query);
        this.currentRequestNoPage = params;
        client.get(Constants.url, addPageCountToRequest(this.currentRequestNoPage, pageCount), handler);
    }

    /**
     * // Built by LucyBot. www.lucybot.com
     var url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
     url += '?' + $.param({
     'api-key': "ab29c860c7ac475fa441ce8f208f7ef1",
     'fq': "news_desk:("Sports" "Foreign")",
     'begin_date': "20170506",
     'end_date': "20170507",
     'sort': "newest"
     });
     $.ajax({
     url: url,
     method: 'GET',
     }).done(function(result) {
     console.log(result);
     }).fail(function(err) {
     throw err;
     });
     */

    public void getFilterResult(@Nullable String newDesk, @Nullable String startDate, @Nullable
            String sort, JsonHttpResponseHandler handler, int pageCount) {
        Log.d(LOG_TAG, "pageCount: " + pageCount);
        RequestParams params = new RequestParams();
        params.put("api-key", Constants.api_key);
        if (newDesk != null) {
            params.put(Constants.FQ, Constants.NEWS_DESK + ":(" + newDesk + ")");
        }
        if (startDate != null) {
            params.put(Constants.BEGIN_DATE, startDate);
        }
        if (sort != null) {
            params.put(Constants.SORT, Constants.NEWEST);
        }
        this.currentRequestNoPage = params;
        client.get(Constants.url, addPageCountToRequest(this.currentRequestNoPage, pageCount), handler);
    }

    public void getNextPageResult(JsonHttpResponseHandler handler, int page) {
        client.get(Constants.url, addPageCountToRequest(this.currentRequestNoPage, page), handler);

    }

    public RequestParams addPageCountToRequest(RequestParams param, int pageCount) {
        param.put("page", pageCount);
        return param;
    }





}
