package com.android.nanden.newyorktimesarticle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.android.nanden.newyorktimesarticle.R;
import com.android.nanden.newyorktimesarticle.adapter.ArticleArrayAdapter;
import com.android.nanden.newyorktimesarticle.client.ArticleClient;
import com.android.nanden.newyorktimesarticle.fragments.FilterDialogFragment;
import com.android.nanden.newyorktimesarticle.model.Article;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class ArticleSearchActivity extends AppCompatActivity {
    @BindView(R.id.etSearchItem)
    EditText etSearchItem;
    @BindView(R.id.gvArticle)
    GridView gvArticle;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private ArticleClient client = new ArticleClient();
    private List<Article> articles;
    private ArticleArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setViews();
        gvArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ArticleSearchActivity.this, ArticleDetailActivity.class);
                Article article = articles.get(i);
                intent.putExtra("article", article);
                startActivity(intent);
            }
        });
    }

    private void setViews() {
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvArticle.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_search, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//            JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    System.out.println("response: " + response.toString());
//                    JSONArray filterJsonResult = null;
//                    try {
//                        filterJsonResult = response.getJSONObject("response").getJSONArray("docs");
//                        articles.addAll(Article.fromJsonArray(filterJsonResult));
//                        adapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
//            };
//
//            client.getFilterResult("education", "20170506", null, 0, handler);
//            return true;
//
//    }
    @OnClick(R.id.btnSearch)
    public void onArticleSearch(View view) {
        String query = etSearchItem.getText().toString();
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("response: " + response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJsonArray(articleJsonResults));
                    adapter.notifyDataSetChanged();
                    System.out.println(articles.toString());
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        client.getSearchResult(query, handler);
    }

    public void onFilterAction(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance("Enter " +
                "Category");
        filterDialogFragment.show(fm, "fragment_filter_dialog");
    }
}
