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
import android.webkit.WebView;
import android.widget.TextView;


import com.oc.bashalir.mynews.Controllers.Utils.ItemClickSupport;
import com.oc.bashalir.mynews.Controllers.Activities.WebViewLink;
import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.PopularAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

    private final String mTag = getClass().getSimpleName();
    @BindView(R.id.fragment_popular_tv)
    TextView textView;
    @BindView(R.id.fragment_popular_listnews_rv)
    RecyclerView recyclerView;

   private  WebView mWebView;
    private List<MostPopular.Result> mMostPopular;
    private PopularAdapter mAdapter;
    private Disposable mDisp;

    /**
     * Empty Constructor
     */
    public PopularFragment() {
        // Required empty public constructor
    }

    public static PopularFragment newInstance(int position) {
        return (new PopularFragment());
    }

    /**
     * Configure Popular Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this, result);

        this.configureRecyclerView();

        // Load item from NYT Api
        this.requestPopular();

        //Start Webview on item clicked
        this.configureOnClickRecyclerView();

        return result;
    }

    /**
     *  Configure RecyclerView
     */
    private void configureRecyclerView() {
        mMostPopular = new ArrayList<>();
        mAdapter = new PopularAdapter(mMostPopular);
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
                        webViewActivity.putExtra("URL",mMostPopular.get(position).getUrl());
                        startActivityForResult(webViewActivity,0);

                    }
                });
    }

    /**
     *  Manage Stream Request
     */
    private void requestPopular() {
        this.updateUIWhenStartingRequest();
        mDisp = NYTStreams.streamFetchMostPopular().subscribeWith(new DisposableObserver<MostPopular>() {

            @Override
            public void onNext(MostPopular mostPopular) {
                Log.d(mTag, "NEXT");
                updateUIWithList(mostPopular);
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
     * Display a text when Starting Request
     */
    private void updateUIWhenStartingRequest() {
        this.textView.setText("Downloading...");
    }

    /**
     * Add item in mMostPopular with the stream request
     * @param mostPopular
     */
    private void updateUIWithList(MostPopular mostPopular) {

        mMostPopular.addAll(mostPopular.getResults());
        mAdapter.notifyDataSetChanged();
    }


}
