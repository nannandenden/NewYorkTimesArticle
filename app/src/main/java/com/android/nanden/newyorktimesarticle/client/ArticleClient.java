package com.android.nanden.newyorktimesarticle.client;

import android.support.annotation.Nullable;

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
            String sort, JsonHttpResponseHandler handler) {
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
        client.get(Constants.url, params, handler);
    }



}
