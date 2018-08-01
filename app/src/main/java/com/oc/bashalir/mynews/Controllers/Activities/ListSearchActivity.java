package com.oc.bashalir.mynews.Controllers.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.bashalir.mynews.Controllers.Utils.ItemClickSupport;
import com.oc.bashalir.mynews.Controllers.Utils.NYTStreams;
import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.TechAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ListSearchActivity extends AppCompatActivity {

    private final String mTag = getClass().getSimpleName();
    @BindView(R.id.activity_list_search_tv)
    TextView textView;
    @BindView(R.id.activity_list_search_rv)
    RecyclerView recyclerView;
    private List<ArticleSearch.Response.Doc> mSearch;
    private TechAdapter mAdapter;
    private Disposable mDisp;
    private String mQuery;
    private String mCategory;
    private String mBegin;
    private String mEnd;
    private Boolean mNotif;
    final String ID_SEARCH = "ID_SEARCH";
    final String NOTIFY = "NOTIFY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);

        Intent intent =getIntent();
        mQuery=intent.getStringExtra("query");
        mCategory=intent.getStringExtra("category");
        mBegin=intent.getStringExtra("begin");
        mEnd=intent.getStringExtra("end");
        mNotif=intent.getBooleanExtra("notif",false);



        //load the view
        ButterKnife.bind(this);

        Log.e(mTag, mQuery+mCategory+mBegin+mEnd);
        this.configureToolbar();
        this.configureRecyclerView();
        this.requestSearchList();
        this.configureOnClickRecyclerView();


    }

    private void configureToolbar() {

        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set the toolbar
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Search Results");

    }

    private void configureRecyclerView() {
        mSearch = new ArrayList<>();
        mAdapter = new TechAdapter(mSearch);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void configureOnClickRecyclerView() {


        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView, R.layout.fragment_page_news)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Intent webViewActivity = new Intent(getApplicationContext(), WebViewLink.class);
                        webViewActivity.putExtra("URL", mSearch.get(position).getWebUrl());
                        startActivityForResult(webViewActivity, 0);

                    }
                });
    }

    private void requestSearchList() {
        this.updateUIWhenStartingRequest();


        mDisp = NYTStreams.streamFetchSearch(mQuery,mCategory,mBegin,mEnd).subscribeWith(new DisposableObserver<ArticleSearch>() {


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
                Log.e(mTag, "On Complete !!");
                textView.setVisibility(View.INVISIBLE);



            }
        });
    }

    private void updateUIWhenStartingRequest() {
        this.textView.setText("Downloading...");
    }

    private void updateUIWithList(ArticleSearch articleSearch) {


        mSearch.addAll(articleSearch.getResponse().getDocs());
        if (articleSearch.getResponse().getMeta().getHits()==0) {
            Toast.makeText(getApplicationContext(), "No Articles", Toast.LENGTH_SHORT).show();
            Log.e(mTag, "No Articles");
        }else
        {
            if (mNotif) {
                String idFirstSearch = mSearch.get(0).getId();
                Log.e(mTag, idFirstSearch);
                SharedPreferences sharedPref = getApplication().getSharedPreferences(NOTIFY, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =sharedPref.edit();
                editor.putString(ID_SEARCH,idFirstSearch);
                editor.commit();
            }
        }

        mAdapter.notifyDataSetChanged();
    }
}