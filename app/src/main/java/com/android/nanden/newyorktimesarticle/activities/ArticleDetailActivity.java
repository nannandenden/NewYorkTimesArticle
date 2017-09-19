package com.android.nanden.newyorktimesarticle.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        final Article article = (Article) getIntent().getSerializableExtra("article");
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

}
