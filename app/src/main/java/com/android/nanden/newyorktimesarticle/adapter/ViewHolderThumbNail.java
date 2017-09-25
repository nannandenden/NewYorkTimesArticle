package com.android.nanden.newyorktimesarticle.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nanden.newyorktimesarticle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nanden on 9/25/17.
 */

public class ViewHolderThumbNail extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.ivThumbnail)
    ImageView ivThumbNail;

    @Nullable
    @BindView(R.id.tvHeadline)
    TextView tvHeadline;

    public ViewHolderThumbNail(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getThumbNail() {
        return ivThumbNail;
    }

    public TextView getHeadline() {
        return tvHeadline;
    }
}
