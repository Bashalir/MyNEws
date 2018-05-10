package com.oc.bashalir.mynews.Controllers.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oc.bashalir.mynews.Controllers.Fragments.PageFragment;

/**
 *
 */
public class PageAdapter extends FragmentPagerAdapter {
    /**
     *
     * @param fm
     */
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return (PageFragment.newInstance(position));
    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return (3);
    }

    /**
     *
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page "+position;
    }
}
