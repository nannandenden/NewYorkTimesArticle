package com.android.nanden.newyorktimesarticle.client;

import com.android.nanden.newyorktimesarticle.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by nanden on 9/19/17.
 */

public class ArticleClient {

    private AsyncHttpClient client;
    public ArticleClient() {
        this.client = new AsyncHttpClient();
    }

    public void getSearchResult(String query, JsonHttpResponseHandler handler
    ) {
        RequestParams params = new RequestParams();
        params.put("api-key", Constants.api_key);
        params.put("page", 0);
        params.put("q", query);
        client.get(Constants.url, params, handler);
    }



}
