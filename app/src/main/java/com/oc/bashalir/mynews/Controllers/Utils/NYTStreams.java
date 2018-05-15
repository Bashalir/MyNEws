package com.oc.bashalir.mynews.Controllers.Utils;

<<<<<<< HEAD
import com.oc.bashalir.mynews.Models.TopStories;
=======
import com.oc.bashalir.mynews.Controllers.Models.TopStories.TopStories;
>>>>>>> parent of 61f2c37... Fix Connexion API NYT

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStreams {


    public static Observable<List<TopStories>> streamFetchTopStories(){
        NYTService nytService = NYTService.retrofit.create(NYTService.class);
        return nytService.getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }


}
