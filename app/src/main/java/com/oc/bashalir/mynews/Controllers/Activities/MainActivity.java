package com.oc.bashalir.mynews.Controllers.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.oc.bashalir.mynews.Views.Adapters.PageAdapter;
import com.oc.bashalir.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Configure Toolbar & RecyclerView, Manage News
 */
public class MainActivity extends AppCompatActivity {

    private final String mTag = getClass().getSimpleName();

    @BindView(R.id.toolbar)    Toolbar mToolbar;
    @BindView(R.id.activity_main_viewpager)    ViewPager mPager;
    @BindView(R.id.activity_main_tabs)    TabLayout mTabLayout;

    /**
     * Add Startup Create ToolBar
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load the view
        ButterKnife.bind(this);

        //load the Toolbar
        setSupportActionBar(mToolbar);

        //configure TabLayout
        String[] tabs = getResources().getStringArray(R.array.tabs_array);

        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mPager.setAdapter(new PageAdapter(getSupportFragmentManager(), tabs));
    }


    /**
     * Manage Toolbar
     *
     * @param item : Toolbar Icon (Search and Params)
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_activity_main_params:
                Log.d(mTag, "PARAMS");
                return true;
            case R.id.menu_activity_main_search:
                Log.d(mTag, "SEARCH");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Load the Menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }
}
