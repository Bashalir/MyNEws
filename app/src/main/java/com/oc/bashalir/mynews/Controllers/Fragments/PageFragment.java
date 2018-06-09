package com.oc.bashalir.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Models.News;
import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.NewsAdapter;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.Icepick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    private final String mTag = getClass().getSimpleName();
    private Disposable mDisp;
    private List<TopStories.Result> mTopStories;
    private NewsAdapter mAdapter;
    private List<News> mListNews;

    @BindView(R.id.fragment_page_tv)
    TextView textView;
    @BindView(R.id.fragment_page_listnews_rv)
    RecyclerView recyclerView;

    //  @State int mPosition;




    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(int position) {

        PageFragment frag = new PageFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        //  mPosition=position;
        return (frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, result);
        Icepick.restoreInstanceState(this, savedInstanceState);

        int position = getArguments().getInt(KEY_POSITION, -1);

        textView.setText("Page n° " + position);

        switch (position){
            case 0:
                  this.RequestTopStories();

                break;
            case 1:
                break;
            case 2:
                break;
        }


        Log.d(mTag, "Page n° " + position);


        return result;
    }



    private void RequestTopStories() {
        this.updateUIWhenStartingRequest();
        mDisp = NYTStreams.streamFetchTopStories().subscribeWith(new DisposableObserver<TopStories>() {


            @Override
            public void onNext(TopStories topStories) {
                Log.d(mTag, "NEXT");
                updateUIWithList(topStories);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(mTag, "On Error " + Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e(mTag, "On Complete !!");
                textView.setVisibility(View.INVISIBLE);
            }
        });
    }


    /**
     * Dispose subscription
     */
    private void destroyDispose() {
        if (mDisp != null && !mDisp.isDisposed()) mDisp.dispose();
    }


    private void updateUIWhenStartingRequest() {
        this.textView.setText("Downloading...");
    }


    private void updateUIWithList(TopStories topStories) {
        mTopStories.addAll(topStories.getResults());
        mListNews=new ArrayList<News>();


        for(int i = 0; i <topStories.getResults().size(); i++){

            String newsTitle=topStories.getResults().get(i).getTitle();
            String newsURL=topStories.getResults().get(i).getUrl();
            String newsSection=null;

            Log.e("TAG",newsTitle);

            //Photo

            String newsPhoto="https://www.nytco.com/wp-content/themes/nytco/images/nytco/sidebar-logo.png";

            if (!topStories.getResults().get(i).getMultimedia().isEmpty()) {

                newsPhoto =topStories.getResults().get(i).getMultimedia().get(1).getUrl();
            }

            // Section

            if (topStories.getResults().get(i).getSubsection().isEmpty()) {
                newsSection = topStories.getResults().get(i).getSection();
            } else {
                newsSection = topStories.getResults().get(i).getSection() + " > " + topStories.getResults().get(i).getSubsection();
            }

            // Date

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");

            ParsePosition pos=new ParsePosition(0);
            Date dateNews = formatter.parse(topStories.getResults().get(i).getUpdatedDate(),pos);

            String newsDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(dateNews);

            mListNews.add(new News(newsTitle, newsURL, newsPhoto, newsSection, newsDate));

        }
        mAdapter.notifyDataSetChanged();

      //  mAdapter = new NewsAdapter(mListNews);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}