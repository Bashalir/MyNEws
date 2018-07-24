package com.oc.bashalir.mynews.Controllers.Utils;



import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.Models.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStreams {


    public static Observable<TopStories> streamFetchTopStories(){
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        return nytService.getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MostPopular> streamFetchMostPopular(){
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        return nytService.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

 /*   public static Observable<TopStories> streamFetchTechnology(){
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        return nytService.getTechnology()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }*/

    public static Observable<ArticleSearch> streamFetchTechnology(){
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        return nytService.getTechnology()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ArticleSearch> streamFetchSearch(String query, String category, String begin, String end){
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        return nytService.getSearch(query, category, begin, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}