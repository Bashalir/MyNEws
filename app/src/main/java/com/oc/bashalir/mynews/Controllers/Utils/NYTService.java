package com.oc.bashalir.mynews.Controllers.Utils;



import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.Models.TopStories;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NYTService {

    String API_KEY="68156e4ed78640bba3232b2be62044fc";

    @GET("topstories/v2/home.json?api-key="+API_KEY)
    Observable<TopStories> getTopStories();

    @GET("mostpopular/v2/mostviewed/all-sections/1.json?api-key="+API_KEY)
    Observable<MostPopular> getMostPopular();

    @GET("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=technology&fl=section_name,snippet,pub_date,web_url,multimedia&sort=newest&page=5&api-key="+API_KEY)
    Observable<ArticleSearch> getTechnology();


    public static final Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();




}
