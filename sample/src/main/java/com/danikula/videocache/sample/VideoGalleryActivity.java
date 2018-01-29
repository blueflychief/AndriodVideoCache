package com.danikula.videocache.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

public class VideoGalleryActivity extends FragmentActivity {

    ViewPager viewPager;
    CirclePageIndicator viewPagerIndicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);
        viewPager = findViewById(R.id.viewPager);
        viewPagerIndicator = findViewById(R.id.viewPagerIndicator);
        ViewsPagerAdapter viewsPagerAdapter = new ViewsPagerAdapter(this);
        viewPager.setAdapter(viewsPagerAdapter);
        viewPagerIndicator.setViewPager(viewPager);
    }

    private static final class ViewsPagerAdapter extends FragmentStatePagerAdapter {

        public ViewsPagerAdapter(FragmentActivity activity) {
            super(activity.getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            Video video = Video.values()[position];
            return GalleryVideoFragment.newInstance(video.url);
        }

        @Override
        public int getCount() {
            return Video.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Video.values()[position].name();
        }
    }
}
