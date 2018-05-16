package com.oc.bashalir.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHolder extends RecyclerView.ViewHolder {
   @BindView(R.id.fragment_page_new_title)    TextView mTitle;



    public NewsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithNews(TopStories.Result topStories){
        mTitle.setText(topStories.getTitle());
    }

}
