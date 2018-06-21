package com.oc.bashalir.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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
public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private final String mTag = getClass().getSimpleName();


    @BindView(R.id.toolbar)    Toolbar mToolbar;
    @BindView(R.id.activity_main_viewpager)    ViewPager mPager;
    @BindView(R.id.activity_main_tabs)    TabLayout mTabLayout;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;


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

        this.configureClickDrawer();

        //load the Toolbar
        setSupportActionBar(mToolbar);

        //Configure actionbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);



        //configure TabLayout
        String[] tabs = getResources().getStringArray(R.array.tabs_array);

        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mPager.setAdapter(new PageAdapter(getSupportFragmentManager(), tabs));
    }



    public void configureClickDrawer(){

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.menu_activity_main_search:
                Log.d(mTag, "SEARCH");
                return true;
            case R.id.menu_activity_main_notification:
                Log.d(mTag, "Notification");
                Intent intent = new Intent(MainActivity.this, ParameterActivity.class);
                this.startActivity(intent);
                return true;
                        case R.id.menu_activity_main_help:
                Log.d(mTag, "Help");
                return true;
            case R.id.menu_activity_main_about:
                Log.d(mTag, "About");
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




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();

        switch(menuItem.getItemId()){
            case R.id.top_stories :
                Log.d(mTag, "Top");
                mPager.setCurrentItem(0);

                break;
            case R.id.most_popular :
                Log.d(mTag, "Popular");
                mPager.setCurrentItem(2);

                break;
        }



        return true;
    }
}
