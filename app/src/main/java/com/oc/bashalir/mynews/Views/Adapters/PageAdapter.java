package com.oc.bashalir.mynews.Views.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oc.bashalir.mynews.Controllers.Fragments.PageFragment;

import java.util.Observable;

import io.reactivex.observers.DisposableObserver;

/**
 * Show News on Tab
 */
public class PageAdapter extends FragmentPagerAdapter {

    String[] mTabs = null;

    /**
     * Constructor
     *
     * @param fm   :  FragmentManager
     * @param tabs : ArrayString name of tabs
     */
    public PageAdapter(FragmentManager fm, String[] tabs) {
        super(fm);
        mTabs = tabs;
    }

    /**
     * Get the size of tabs
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return (PageFragment.newInstance(position));
    }

    /**
     * Get the size of array tabs
     *
     * @return size
     */
    @Override
    public int getCount() {
        return mTabs.length;
    }

    /**
     * Get the title of Tabs
     *
     * @param position
     * @return : Title name
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mTabs[position];

    }

    /**
     * Observable
     * @return
     */




}
