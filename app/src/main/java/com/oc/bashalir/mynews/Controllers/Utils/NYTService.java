package com.oc.bashalir.mynews.Controllers.Utils;

import com.oc.bashalir.mynews.Controllers.Models.TopStories.TopStories;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NYTService {

    String API_KEY="68156e4ed78640bba3232b2be62044fc";

    @GET("home.json/"+API_KEY)
    Observable<List<TopStories>> getTopStories();

    public static final Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


}
