package com.oc.bashalir.mynews.Controllers.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.oc.bashalir.mynews.R;
import com.oc.bashalir.mynews.Views.Adapters.PageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.target.ViewTarget;


/**
 * Configure Toolbar & RecyclerView, Manage News
 */
public class MainActivity extends AppCompatActivity {

    private final String mTag = getClass().getSimpleName();


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_viewpager)
    ViewPager mPager;
    @BindView(R.id.activity_main_tabs)
    TabLayout mTabLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;



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

        //Configure actionbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        //configure TabLayout
        String[] tabs = getResources().getStringArray(R.array.tabs_array);

        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mPager.setAdapter(new PageAdapter(getSupportFragmentManager(), tabs));

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Log.d(mTag, "Menu");
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);


                        switch (menuItem.getItemId()) {
                            case R.id.top_stories:
                                Log.d(mTag, "Top");
                                mPager.setCurrentItem(0);

                                break;
                            case R.id.most_popular:
                                Log.d(mTag, "Popular");
                                mPager.setCurrentItem(1);

                                break;
                            case R.id.technology:
                                Log.d(mTag, "Popular");
                                mPager.setCurrentItem(2);
                                break;
                            case R.id.search:
                                Log.d(mTag, "Search");
                                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                                break;
                            case R.id.notification:
                                Log.d(mTag, "Search");
                                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                                break;

                        }
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        return true;
                    }

                });
    }
    /**
     * Manage Toolbar
     *
     * @param item : Toolbar Icon (Menu,Search and Params)
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;


        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.menu_activity_main_search:
                Log.d(mTag, "SEARCH");
                intent = new Intent(MainActivity.this, SearchActivity.class);
                this.startActivity(intent);
                return true;

            case R.id.menu_activity_main_notification:
                Log.d(mTag, "Notification");
                 intent = new Intent(MainActivity.this, NotificationActivity.class);
                this.startActivity(intent);
                return true;

            case R.id.menu_activity_main_help:
                Log.d(mTag, "Help");
                this.showCase();
                return true;

            case R.id.menu_activity_main_about:
                Log.d(mTag, "About");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showCase() {

        ShowcaseConfig config = new ShowcaseConfig();

        final MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);
        config.setDelay(200);
        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(mToolbar)
                        .setDismissText("NEXT")
                        .setContentText("This is the toolbar \n With 3 Buttons \n 1. Menu \n 2. Search \n 3. Options")
                        .withRectangleShape(true)
                        .renderOverNavigationBar()
                        .build()
        );


        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(mTabLayout)
                        .setDismissText("NEXT")
                        .setContentText("This is the Tabs \n With 3 choice for news category")
                        .withRectangleShape(true)
                        .renderOverNavigationBar()
                        .build()
        );

   /*     IShowcaseListener listener = new IShowcaseListener() {


            @Override
            public void onShowcaseDisplayed(MaterialShowcaseView materialShowcaseView) {
                sequence.setOnItemDismissedListener();
            }

            @Override
            public void onShowcaseDismissed(MaterialShowcaseView materialShowcaseView) {
            sequence.hasFired();
            }
        };*/

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(mPager)
                        .setDismissText("END")
                        .setContentText("This is the news list for the selected category \n Select one news and you see them")
                        .setDismissOnTargetTouch(true)
                        .setDismissOnTouch(true)
                        .setTargetTouchable(true)
                        .setShapePadding(-60)
                        .build()
        );

        sequence.start();

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
