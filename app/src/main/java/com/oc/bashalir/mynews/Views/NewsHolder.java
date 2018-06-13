package com.oc.bashalir.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oc.bashalir.mynews.Models.MostPopular;
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

    public void updateWithNews(TopStories.Result topStories) {


        String ariane;

        if (topStories.getSubsection().isEmpty()) {
            ariane = topStories.getSection();
        } else {
            ariane = topStories.getSection() + " > " + topStories.getSubsection();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");

        ParsePosition pos=new ParsePosition(0);
        Date dateNews = formatter.parse(topStories.getUpdatedDate(),pos);

        String newsDate = DateFormat.getDateInstance(DateFormat.SHORT,Locale.FRANCE).format(dateNews);

        mTitle.setText(topStories.getTitle());
        mAriane.setText(ariane);
        mDate.setText(newsDate);

        String imgURL="https://www.nytco.com/wp-content/themes/nytco/images/nytco/sidebar-logo.png";

        if (!topStories.getMultimedia().isEmpty()) {

            imgURL =topStories.getMultimedia().get(1).getUrl();
        }

        Picasso.get().load(imgURL).into(mImg);
    }



}