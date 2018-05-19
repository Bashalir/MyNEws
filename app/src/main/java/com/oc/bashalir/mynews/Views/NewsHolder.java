package com.oc.bashalir.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHolder extends RecyclerView.ViewHolder {
   @BindView(R.id.fragment_page_news_title)    TextView mTitle;
    @BindView(R.id.fragment_page_news_img)    ImageView mImg;


    public NewsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithNews(TopStories.Result topStories){
        mTitle.setText(topStories.getTitle());
       if (!topStories.getMultimedia().isEmpty()){  Picasso.get().load(topStories.getMultimedia().get(0).getUrl()).into(mImg);}
    }

}
