package com.keith.miniframework.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.keith.miniframework.data.Screen;

import java.util.List;

public class ScreenAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ScreenAdapter";

    private List<Screen> screens;

    public ScreenAdapter(FragmentManager fm, List<Screen> screens) {
        super(fm);
        this.screens = screens;
    }

    @Override
    public Fragment getItem(int position) {
        Screen screen = screens.get(position);
        if (screen != null) {
            return ScreenFragment.newInstance(screen);
        }
        return null;
    }

    @Override
    public int getCount() {
        return screens.size();
    }
}
