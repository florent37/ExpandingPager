package com.qslll.library;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.qslll.library.fragments.ExpandingFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Qs on 16/6/20.
 */
public class ExpandingViewPager {

    final ViewPager viewPager;

    public ExpandingViewPager(@NonNull ViewPager viewPager){
        this.viewPager = viewPager;
    }

    @Nullable WeakReference<ExpandingFragment> currentFragmentWeakReference;

    private ExpandingFragment getCurrentFragment(){
        if (viewPager.getAdapter() instanceof ExpandingViewPagerAdapter) {
            ExpandingViewPagerAdapter adapter = (ExpandingViewPagerAdapter) viewPager.getAdapter();
            Fragment fragment = adapter.getCurrentFragment();
            if (fragment instanceof ExpandingFragment) {
                return (ExpandingFragment)fragment;
            }
        }
        return null;
    }

    public void setupViewPager() {
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.width = ((Activity) viewPager.getContext()).getWindowManager().getDefaultDisplay().getWidth() / 7 * 5;
        layoutParams.height = (int) ((layoutParams.width / 0.75));

        viewPager.setOffscreenPageLimit(2);

        if (viewPager.getParent() instanceof ViewGroup) {
            ViewGroup viewParent = ((ViewGroup) viewPager.getParent());
            viewParent.setClipChildren(false);
            viewPager.setClipChildren(false);
        }

        viewPager.setPageTransformer(true, new ExpandingViewPagerTransformer());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ExpandingFragment expandingFragment = getCurrentFragment();
                if(expandingFragment != null && expandingFragment.isOpenend()){
                    expandingFragment.close();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public boolean onBackPressed() {
        ExpandingFragment expandingFragment = getCurrentFragment();
        if(expandingFragment != null && expandingFragment.isOpenend()){
            expandingFragment.close();
            return true;
        }
        return false;
    }
}
