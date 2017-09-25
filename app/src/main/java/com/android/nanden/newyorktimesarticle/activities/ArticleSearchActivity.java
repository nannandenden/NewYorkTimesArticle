package com.android.nanden.newyorktimesarticle.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.nanden.newyorktimesarticle.Constants;
import com.android.nanden.newyorktimesarticle.R;
import com.android.nanden.newyorktimesarticle.Utils;
import com.android.nanden.newyorktimesarticle.adapter.ArticleAdapter;
import com.android.nanden.newyorktimesarticle.client.ArticleClient;
import com.android.nanden.newyorktimesarticle.fragments.FilterDialogFragment;
import com.android.nanden.newyorktimesarticle.model.Article;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.android.nanden.newyorktimesarticle.R.id.miActionFilter;

public class ArticleSearchActivity extends AppCompatActivity implements FilterDialogFragment
        .FilterDialogListener {

    private static final String LOG_TAG = ArticleSearchActivity.class.getSimpleName();
    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private ArticleClient client = new ArticleClient();
    private List<Article> articles;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_search);
        ButterKnife.bind(this);

        setViews();
//        defineViewEventsFunction();
    }

    private void setViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New York Times Articles");
        articles = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, articles);
        rvArticle.setAdapter(articleAdapter);
        rvArticle.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
    }

//    private void defineViewEventsFunction() {
//        // for opening the clicked article in new webview
//        gvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(ArticleSearchActivity.this, ArticleDetailActivity.class);
//                Article article = articles.get(i);
//                intent.putExtra("article", article);
//                startActivity(intent);
//            }
//        });
//        // for filling the items automatically (infinite scrolling)
//        // onLoadMore method wil be triggered when use exceed the set visibleThreshHold limit
//        gvArticle.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount) {
//                // triggered only when new data needs to be appended to the gridview list
//                // add code for append new item to adapter view
//                // page: next page to load
//                Log.d(LOG_TAG, "onLoadMore:totalItemsCount: " + totalItemsCount);
//                Log.d(LOG_TAG, "onLoadMore:page: " + page);
//                loadNextDataFromApi(page);
//                // return true only if there is a new item to load
//                return true;
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(LOG_TAG, "onQueryTextSubmit clicked. query: " + query);
                JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d(LOG_TAG, "response: " + response.toString());
                        JSONArray articleJsonResults = null;

                        try {
                            articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                            if (!articles.isEmpty()) {
                                // clear the previous search result
                                articles.clear();
                            }
                            articles.addAll(Article.fromJsonArray(articleJsonResults));
                            articleAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.d(LOG_TAG, "error: " + e.getMessage());
                        }
                    }
                };
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    client.getSearchResult(query, handler, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
                }
                // remove keyboard focus
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == miActionFilter) {
            showFilterDialog();
        }
            return true;

    }

    private void showFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance("Filter");
        filterDialogFragment.show(fm, "filter_dialog_fragment");
    }

    @Override
    public void onFilterDialog(Map<String, String> filterValue) {
        Log.d(LOG_TAG, "onFilterDialog called");
        String newsDesk = filterValue.containsKey(Constants.NEWS_DESK) ? filterValue.get
                (Constants.NEWS_DESK) : null;
        String beginDate = filterValue.containsKey(Constants.BEGIN_DATE) ? filterValue.get
                (Constants.BEGIN_DATE) : null;
        String sort = filterValue.containsKey(Constants.SORT) ? filterValue.get(Constants.SORT) :
                null;
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(LOG_TAG, "fail");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(LOG_TAG, "response: " + response.toString());
                JSONArray filterResults;
                try {
                    if (!articles.isEmpty()) {
                        // clear the previous search result
                        articles.clear();
                    }
                    filterResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJsonArray(filterResults));
                    articleAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "error: " + e.getMessage());
                }
            }
        };
        if (Utils.isNetworkAvailable(this)) {
            client.getFilterResult(newsDesk, beginDate, sort, handler, 0);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }
    // append next set of data into the adapter
    // 1. send network request
    // 2. append the data to the adapter
    private void loadNextDataFromApi(int page) {
        // send appropriate data to get the paginated data
        // send the request data including the page number and get the request back
        // deserialize and construct the new data model
        // append the new data objects to the existing set of items inside the array of items and
        // notify adapter
        Log.d(LOG_TAG, "loadNextDataFromApi called");
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(LOG_TAG, "response: " + response.toString());
                JSONArray nextRequestResults;
                try {
                    nextRequestResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJsonArray(nextRequestResults));
                    articleAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(LOG_TAG, "fail");
            }
        };
        if (Utils.isNetworkAvailable(this)) {
            client.getNextPageResult(handler, page);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

}
