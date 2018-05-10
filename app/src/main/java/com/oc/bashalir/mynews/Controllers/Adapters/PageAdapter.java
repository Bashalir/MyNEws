package com.oc.bashalir.mynews.Controllers.Adapters;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oc.bashalir.mynews.Controllers.Fragments.PageFragment;
import com.oc.bashalir.mynews.R;

/**
 *
 */
public class PageAdapter extends FragmentPagerAdapter {

    String[] mTabs = null;

    /**
     * @param fm
     */
    public PageAdapter(FragmentManager fm,String[] tabs) {
        super(fm);
        mTabs=tabs;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return (PageFragment.newInstance(position));
    }

    /**
     * @return
     */
    @Override
    public int getCount() {
        return mTabs.length;
    }

    /**
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mTabs[position];

    }
}
