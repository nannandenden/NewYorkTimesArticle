package com.android.nanden.newyorktimesarticle.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.nanden.newyorktimesarticle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nanden on 9/25/17.
 */

public class ViewHolderSnippet extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.tvHeadline)
    TextView tvHeadline;

    @Nullable
    @BindView(R.id.tvSnippet) TextView tvSnippet;
    public ViewHolderSnippet(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> {
            if (ArticleAdapter.getOnItemClickListener() != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ArticleAdapter.getOnItemClickListener().onItemClick(itemView, position);
                }
            }

        });
    }

    public TextView getHeadline() {
        return tvHeadline;
    }

    public TextView getSnippet() {
        return tvSnippet;
    }
}
