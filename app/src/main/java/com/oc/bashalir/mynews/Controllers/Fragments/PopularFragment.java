package com.oc.bashalir.mynews.Controllers.Fragments;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.bashalir.mynews.Controllers.Activities.ItemClickSupport;
import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Models.MostPopular;
import com.oc.bashalir.mynews.Models.TopStories;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.NewsAdapter;
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

    private List<MostPopular.Result> mMostPopular;
    private final String mTag = getClass().getSimpleName();
    private PopularAdapter mAdapter;
    private Disposable mDisp;

    @BindView(R.id.fragment_popular_tv)
    TextView textView;
    @BindView(R.id.fragment_popular_listnews_rv)
    RecyclerView recyclerView;



    public PopularFragment() {
        // Required empty public constructor
    }
    public static PopularFragment newInstance(int position) {
        return (new PopularFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result= inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this,result);

        this.configureRecyclerView();
       this.requestPopular();
        this.configureOnClickRecyclerView();

        return result;
    }

    private void configureRecyclerView() {
        mMostPopular = new ArrayList<>();
        mAdapter = new PopularAdapter(mMostPopular);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_page_news)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {



                        Toast.makeText(getActivity(),mMostPopular.get(position).getUrl(), Toast.LENGTH_SHORT).show();


                    }
                });
    }

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


    private void updateUIWhenStartingRequest() {
        this.textView.setText("Downloading...");
    }


    private void updateUIWithList(MostPopular mostPopular) {

        mMostPopular.addAll(mostPopular.getResults());
        mAdapter.notifyDataSetChanged();
    }


}
