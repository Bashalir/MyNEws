package com.oc.bashalir.mynews.Controllers.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oc.bashalir.mynews.Controllers.Utils.ItemClickSupport;
import com.oc.bashalir.mynews.Controllers.Activities.WebViewLink;
import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.TopAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    private final String mTag = getClass().getSimpleName();
    private Disposable mDisp;
    private List<TopStories.Result> mTopStories;
    private TopAdapter mAdapter;

    @BindView(R.id.fragment_top_tv)
    TextView textView;
    @BindView(R.id.fragment_top_listnews_rv)
    RecyclerView recyclerView;

    /**
     * When Fragment is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.destroyDispose();
    }

    /**
     * Empty constructor
     */
    public TopFragment() {
        // Required empty public constructor
  }

    public static TopFragment newInstance(int position) {
          return (new TopFragment());
    }

    /**
     * Configure Top Stories Fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_top, container, false);
        ButterKnife.bind(this, result);


        this.configureRecyclerView();

        // Load item from NYT Api
        this.requestTopStories();

        //Start Webview on item clicked
        this.configureOnClickRecyclerView();

         return result;
    }


    /**
     *  Configure RecyclerView
     */
    private void configureRecyclerView() {
        mTopStories = new ArrayList<>();
        mAdapter = new TopAdapter(mTopStories);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    /**
     * Start Webview on item clicked
     */
    private void configureOnClickRecyclerView() {


        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView, R.layout.fragment_page_news)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Intent webViewActivity = new Intent(getActivity(), WebViewLink.class);
                        webViewActivity.putExtra("URL",mTopStories.get(position).getUrl());
                        startActivityForResult(webViewActivity,0);

                    }
                });
    }

    /**
     *  Manage Stream Request
     */
    private void requestTopStories() {
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
                Log.d(mTag, "On Complete !!");
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

    /**
     * Display a text when Starting Request
     */
    private void updateUIWhenStartingRequest() {
        this.textView.setText("Downloading...");
    }

    /**
     * Add item in mTopStories with the stream request
     *
     * @param topStories
     */
    private void updateUIWithList(TopStories topStories) {

        mTopStories.addAll(topStories.getResults());
        mAdapter.notifyDataSetChanged();
    }

}
