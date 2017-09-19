package com.android.nanden.newyorktimesarticle.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nanden.newyorktimesarticle.R;
import com.android.nanden.newyorktimesarticle.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nanden on 9/19/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article>{

    public ArticleArrayAdapter(@NonNull Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = this.getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }
        ImageView ivThumbNail = convertView.findViewById(R.id.ivThumbnail);

        ivThumbNail.setImageResource(0);
        TextView tvHeadline = convertView.findViewById(R.id.tvHeadline);
        tvHeadline.setText(article.getHeadline());
        String thumbnail = article.getThumbNail();
        if (!TextUtils.isEmpty(thumbnail)) {
            System.out.println("thumbnail: " + thumbnail);
            Picasso.with(getContext()).load(thumbnail).fit().into(ivThumbNail);
        }

        return convertView;

    }
}
