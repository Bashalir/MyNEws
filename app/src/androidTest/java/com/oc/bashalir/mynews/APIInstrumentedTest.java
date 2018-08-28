package com.oc.bashalir.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.Models.TopStories;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class APIInstrumentedTest {
    @Test
    public void connexionTopStories() throws Exception{

        Observable<TopStories> topStoriesObservable = NYTStreams.streamFetchTopStories();
        TestObserver<TopStories> observer = new TestObserver<>();
        topStoriesObservable.subscribeWith(observer)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TopStories topStoriesFetched = observer.values().get(0);


        assertNotNull(topStoriesFetched.getResults().get(0).getTitle());

    }
    @Test
    public void connexionMostPopular() throws Exception{

        Observable<MostPopular> mostPopularObservable = NYTStreams.streamFetchMostPopular();
        TestObserver<MostPopular> observer = new TestObserver<>();
        mostPopularObservable.subscribeWith(observer)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        MostPopular mostPopularFetched = observer.values().get(0);


        assertNotNull(mostPopularFetched.getResults().get(0).getTitle());

    }
}
