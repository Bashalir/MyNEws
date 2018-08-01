package com.oc.bashalir.mynews.Controllers.Utils;



import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.Models.TopStories;

import java.util.HashMap;
import java.util.Map;
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
        Map<String,String> data=new HashMap<>();
        data.put("q",query);
        data.put("fq",category);
       if (begin!=null){
        data.put("begin_date",begin);}
        if (end!=null){
        data.put("end_date",end);}

        return nytService.getSearch(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}