package com.oc.bashalir.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oc.bashalir.mynews.Controllers.Utils.Utilities;
import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.TechAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.fragment_page_news_title)
    TextView mTitle;
    @BindView(R.id.fragment_page_news_ariane)
    TextView mAriane;
    @BindView(R.id.fragment_page_news_date)
    TextView mDate;
    @BindView(R.id.fragment_page_news_img)
    ImageView mImg;

    public SearchHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithNews(ArticleSearch.Response.Doc articleSearch) {

        String articleDate="";

        Log.d("TAG",articleSearch.getPubDate() );

        if (articleSearch.getPubDate().contains("Z")){
            Log.e("TAG","KOUKOU" );
            articleDate = new Utilities().DateShortFormatter(articleSearch.getPubDate(), "yyyy-MM-dd'T'HH:mm:ss'Z'");}
            else{
            articleDate = new Utilities().DateShortFormatter(articleSearch.getPubDate(), "yyyy-MM-dd'T'HH:mm:ssZZZZZ");}

        String articleTitle=articleSearch.getSnippet();

        if (!articleSearch.getHeadline().getMain().isEmpty() ){
            articleTitle=articleSearch.getHeadline().getMain();
        }

        mTitle.setText(articleTitle);
        mAriane.setText(articleSearch.getSectionName());
        mDate.setText(articleDate);

        String imgURL="https://www.nytco.com/wp-content/themes/nytco/images/nytco/sidebar-logo.png";

        if (!articleSearch.getMultimedia().isEmpty() && articleSearch.getMultimedia() !=null) {

            imgURL ="https://www.nytimes.com/"+articleSearch.getMultimedia().get(0).getUrl();
        }

        Log.d("TAG",imgURL );

        Picasso.get().load(imgURL).into(mImg);

    }


}
