package com.android.nanden.newyorktimesarticle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nanden.newyorktimesarticle.R;
import com.android.nanden.newyorktimesarticle.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private Context context;
    private List<Article> articles;
    // attaching click handler using listeners
    // defining the interface
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View viewItem, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);
        return new ViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);

        ImageView ivThumbNail = holder.ivThumbnail;
        String thumbnail = article.getThumbNail();
        if (!TextUtils.isEmpty(thumbnail)) {
            System.out.println("thumbnail: " + thumbnail);
            Picasso.with(context).load(thumbnail).fit().into(ivThumbNail);
        }

        TextView tvHeadline = holder.tvHeadline;
        tvHeadline.setText(article.getHeadline());

    }

    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivThumbnail)
        ImageView ivThumbnail;
        @BindView(R.id.tvHeadline)
        TextView tvHeadline;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
