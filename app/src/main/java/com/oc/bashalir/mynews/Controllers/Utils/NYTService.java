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
import retrofit2.http.Query;

public interface NYTService {

    String API_KEY="68156e4ed78640bba3232b2be62044fc";

    @GET("topstories/v2/home.json?api-key="+API_KEY)
    Observable<TopStories> getTopStories();

    @GET("mostpopular/v2/mostviewed/all-sections/1.json?api-key="+API_KEY)
    Observable<MostPopular> getMostPopular();

    @GET("search/v2/articlesearch.json?page=2&q=technology&fq=news_desk:technology(\"technology\" \"business\" \"science\")&sort=newest&api-key="+API_KEY)
    Observable<ArticleSearch> getTechnology();

    @GET("search/v2/articlesearch.json?page=2&sort=newest&api-key="+API_KEY)
    Observable<ArticleSearch> getSearch(@Query("q") String query,
                                        @Query("fq") String category,
                                        @Query("begin_date") String begin,
                                        @Query("end_date") String end);


    public static final Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


}
