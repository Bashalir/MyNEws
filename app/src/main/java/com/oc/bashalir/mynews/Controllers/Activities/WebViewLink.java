package com.oc.bashalir.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oc.bashalir.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewLink extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    private String mLinkURL;

    /**
     * open a webview with a URL
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mLinkURL = intent.getStringExtra("URL");
        webview.setWebViewClient(new WebViewClient());

        webview.loadUrl(mLinkURL);


    }
}
