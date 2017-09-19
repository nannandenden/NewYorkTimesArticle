package com.android.nanden.newyorktimesarticle.client;

import android.support.annotation.Nullable;
import android.text.TextUtils;

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
     'fq': "news_desk:(\"Sports\" \"Foreign\")",
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

    public void getFilterResult(@Nullable String category, @Nullable String startDate, @Nullable String
                                endDate, @Nullable int
            isSort, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("api-key", Constants.api_key);
        if (!TextUtils.isEmpty(startDate)) {
            params.put("begin_date", startDate);
        }
        if (!TextUtils.isEmpty(endDate)) {
            params.put("end_date", endDate);
        }
        if (!TextUtils.isEmpty(category)) {
            params.put("fq", "news_desk:(" + category + ")");
        }
        if (isSort == Constants.QUERY_SHORT_NEWEST) {
            params.put("sort", "newest");
        }
        if (isSort == Constants.QUERY_SHORT_OLDEST) {
            params.put("sort", "oldest");
        }
        client.get(Constants.url, params, handler);
    }



}
