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
import com.oc.bashalir.mynews.Controllers.Utils.Utilities;
import com.oc.bashalir.mynews.Models.ArticleSearch;
import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.TechAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * show a list of news
 */
public class ListSearchActivity extends AppCompatActivity {

    final String ID_SEARCH = "ID_SEARCH";
    final String DATE_SEARCH = "DATE_SEARCH";
    final String NOTIFY = "NOTIFY";
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

    /**
     * at startup configure the list of selected news
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);

        //load attribute from parent activity
        Intent intent = getIntent();
        mQuery = intent.getStringExtra("query");
        mCategory = intent.getStringExtra("category");
        mBegin = intent.getStringExtra("begin");
        mEnd = intent.getStringExtra("end");
        mNotif = intent.getBooleanExtra("notif", false);

        //load the view
        ButterKnife.bind(this);

        Log.e(mTag, mQuery + mCategory + mBegin + mEnd);

        this.configureToolbar();
        this.configureRecyclerView();
        this.requestSearchList();
        this.configureOnClickRecyclerView();

    }

    /**
     * Configure the toolbar
     */
    private void configureToolbar() {

        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Set the toolbar
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // choose the title relative to the parent activity
        if (!mNotif) {
            ab.setTitle("Search Results");
        } else {
            ab.setTitle("Notification Results");
        }

    }

    /**
     * Configure RecyclerView
     */
    private void configureRecyclerView() {

        mSearch = new ArrayList<>();
        mAdapter = new TechAdapter(mSearch);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * item click management
     */
    private void configureOnClickRecyclerView() {

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerView, R.layout.fragment_page_news)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

                    /**
                     * configure click on an item
                     * @param recyclerView
                     * @param position
                     * @param v
                     */
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        //open a webview with the news
                        Intent webViewActivity = new Intent(getApplicationContext(), WebViewLink.class);
                        webViewActivity.putExtra("URL", mSearch.get(position).getWebUrl());
                        startActivityForResult(webViewActivity, 0);
                    }
                });
    }

    /**
     * Launch the stream request
     */
    private void requestSearchList() {
        this.updateUIWhenStartingRequest();

        mDisp = NYTStreams.streamFetchSearch(mQuery, mCategory, mBegin, mEnd).subscribeWith(new DisposableObserver<ArticleSearch>() {


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

                if (mSearch.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "No Articles", Toast.LENGTH_SHORT).show();
                    Log.e(mTag, "No Articles");

                    textView.setText("No Articles");

                } else {
                    //Record id and the date of first item if is a notification request
                    if (mNotif) {

                        String idFirstSearch = mSearch.get(0).getId();
                        String dateFistSearch = new Utilities().DateShortFormatterSearch(mSearch.get(0).getPubDate());
                        Log.e(mTag, idFirstSearch);

                        //record id and date
                        SharedPreferences sharedPref = getApplication().getSharedPreferences(NOTIFY, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(ID_SEARCH, idFirstSearch);
                        editor.putString(DATE_SEARCH, dateFistSearch);

                        editor.commit();
                    }
                }
            }
        });
    }

    /**
     * Indicates that the download is in progress
     */
    private void updateUIWhenStartingRequest() {
        this.textView.setText("Downloading...");
    }

    /**
     * Load the stream
     *
     * @param articleSearch
     */
    private void updateUIWithList(ArticleSearch articleSearch) {

        mSearch.addAll(articleSearch.getResponse().getDocs());
        mAdapter.notifyDataSetChanged();
    }
}