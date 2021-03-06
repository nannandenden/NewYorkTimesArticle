package com.android.nanden.newyorktimesarticle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nanden.newyorktimesarticle.R;
import com.android.nanden.newyorktimesarticle.model.Article;
import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int THUMBNAIL = 0;
    private final int SNIPPET = 1;
    private Context context;
    private List<Article> articles;
    // attaching click handler using listeners
    // defining the interface
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View viewItem, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        // link the listener from activity to here
        this.listener = listener;
    }
    public static OnItemClickListener getOnItemClickListener() {
        return listener;
    }
    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        if (viewType == THUMBNAIL) {
            View thumbnailView = inflater.inflate(R.layout.item_article_result_thumbnail,
                    parent, false);
            viewHolder = new ViewHolderThumbNail(thumbnailView);
        } else if (viewType == SNIPPET) {
            View snippetView = inflater.inflate(R.layout.item_article_result_snippet,
                    parent, false);
            viewHolder = new ViewHolderSnippet(snippetView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == THUMBNAIL) {
            ViewHolderThumbNail vhThumbnail = (ViewHolderThumbNail) holder;
            bindThumbnail(vhThumbnail, position);
        } else if (getItemViewType(position) == SNIPPET) {
            ViewHolderSnippet vhSnippet = (ViewHolderSnippet) holder;
            bindSnippet(vhSnippet, position);
        }

    }

    private void bindThumbnail(ViewHolderThumbNail holder, int position) {
        Article article = articles.get(position);
        holder.getHeadline().setText(article.getHeadline());
        Glide.with(this.context).load(article.getThumbNail()).into(holder.getThumbNail());
    }

    private void bindSnippet(ViewHolderSnippet holder, int position) {
        Article article = articles.get(position);
        holder.getHeadline().setText(article.getHeadline());
        holder.getSnippet().setText(article.getSnippet());
    }

    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(articles.get(position).getThumbNail())) {
            return THUMBNAIL;
        } else if (TextUtils.isEmpty(articles.get(position).getThumbNail())) {
            return SNIPPET;
        }
        return -1;
    }
}
