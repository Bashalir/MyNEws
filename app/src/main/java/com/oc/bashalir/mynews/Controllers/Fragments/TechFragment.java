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

import com.oc.bashalir.mynews.Controllers.Activities.WebViewLink;
import com.oc.bashalir.mynews.Controllers.Utils.ItemClickSupport;
import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    private final String mTag = getClass().getSimpleName();
    @BindView(R.id.fragment_tech_tv)
    TextView textView;
    @BindView(R.id.fragment_tech_listnews_rv)
    RecyclerView recyclerView;
    private Disposable mDisp;
    private List<ArticleSearch.Response.Doc> mTechnology;
    private SearchAdapter mAdapter;

    /**
     * Empty constructor
     */
    public TechFragment() {
        // Required empty public constructor
    }

    public static TechFragment newInstance(int position) {
        return (new TechFragment());
    }

    /**
     * When Fragment is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.destroyDispose();
    }

    /**
     * Configure Tech Fragment
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
        View result = inflater.inflate(R.layout.fragment_tech, container, false);
        ButterKnife.bind(this, result);

        this.configureRecyclerView();
        this.requestTechnology();
        this.configureOnClickRecyclerView();

        return result;
    }

    /**
     * Configure RecyclerView
     */
    private void configureRecyclerView() {
        mTechnology = new ArrayList<>();
        mAdapter = new SearchAdapter(mTechnology);
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
                        webViewActivity.putExtra("URL", mTechnology.get(position).getWebUrl());
                        startActivityForResult(webViewActivity, 0);

                    }
                });
    }

    /**
     * Manage Stream Request
     */
    private void requestTechnology() {
        this.updateUIWhenStartingRequest();
        mDisp = NYTStreams.streamFetchTechnology().subscribeWith(new DisposableObserver<ArticleSearch>() {


            @Override
            public void onNext(ArticleSearch articleSearch) {
                Log.d(mTag, "NEXT");
                updateUIWithList(articleSearch);
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
     * @param articleSearch
     */
    private void updateUIWithList(ArticleSearch articleSearch) {

        mTechnology.addAll(articleSearch.getResponse().getDocs());
        mAdapter.notifyDataSetChanged();
    }

}
