package com.oc.bashalir.mynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;




public class MainActivity extends AppCompatActivity {

    private final String mTag = getClass().getSimpleName();

    @BindView(R.id.activity_main_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //load the Toolbar
        setSupportActionBar(mToolbar);
    }


    //Manage the Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                Log.d(mTag,"PARAMS");
                return true;
            case R.id.menu_activity_main_search:
                Log.d(mTag,"SEARCH");
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return true;
    }
}
