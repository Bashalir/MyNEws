package com.oc.bashalir.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oc.bashalir.mynews.Models.News;
import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_page_news_title)
    TextView mTitle;
    @BindView(R.id.fragment_page_news_ariane)
    TextView mAriane;
    @BindView(R.id.fragment_page_news_date)
    TextView mDate;
    @BindView(R.id.fragment_page_news_img)
    ImageView mImg;


    public NewsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithNews(News news) {


        mTitle.setText(news.getNewsTitle());
        mAriane.setText(news.getNewsSection());
        mDate.setText(news.getNewsDate());

        Picasso.get().load(news.getNewsPhoto()).into(mImg);
    }

}