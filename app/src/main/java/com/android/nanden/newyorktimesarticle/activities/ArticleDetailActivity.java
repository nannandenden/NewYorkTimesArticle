package com.android.nanden.newyorktimesarticle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.nanden.newyorktimesarticle.R;
import com.android.nanden.newyorktimesarticle.model.Article;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.nanden.newyorktimesarticle.Constants.url;

public class ArticleDetailActivity extends AppCompatActivity {

    @BindView(R.id.wvArticleDetail)
    WebView webView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Article Detail");

        article = (Article) getIntent().getSerializableExtra("article");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        System.out.println("url:" + url);
        webView.loadUrl(article.getWebUrl());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT, article.getWebUrl());
        shareActionProvider.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);
    }

}
