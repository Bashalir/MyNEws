package com.oc.bashalir.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_page_news_title)
    TextView mTitle;
    @BindView(R.id.fragment_page_news_ariane)
    TextView mAriane;
    @BindView(R.id.fragment_page_news_date)
    TextView mDate;
    @BindView(R.id.fragment_page_news_img)
    ImageView mImg;

    public PopularHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithPopular(MostPopular.Result mostPopular) {



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        ParsePosition pos=new ParsePosition(0);
        Date dateNews = formatter.parse(mostPopular.getPublishedDate(),pos);

        String newsDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(dateNews);

        Log.d("TAG",mostPopular.getTitle());

        mTitle.setText(mostPopular.getTitle());
        mAriane.setText(mostPopular.getSection());
        mDate.setText(newsDate);


        String imgURL="https://www.nytco.com/wp-content/themes/nytco/images/nytco/sidebar-logo.png";

        if (!mostPopular.getMedia().isEmpty() ) {

            imgURL =mostPopular.getMedia().get(0).getMediaMetadata().get(6).getUrl();
        }

        Picasso.get().load(imgURL).into(mImg);
    }
}
