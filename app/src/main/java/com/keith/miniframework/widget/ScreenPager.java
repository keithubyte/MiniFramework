package com.keith.miniframework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.keith.miniframework.ChangeScreenEvent;
import com.keith.miniframework.R;

import java.lang.reflect.Field;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class ScreenPager extends ViewPager implements Movable {

    private ScreenScroller mScroller;

    private int mState = SCROLL_STATE_IDLE;

    public ScreenPager(Context context) {
        super(context);
        init(context, null);
    }

    public ScreenPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mScroller = new ScreenScroller(context, new AccelerateDecelerateInterpolator());
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScreenPager);
            int duration = array.getInt(R.styleable.ScreenPager_scrollDuration, ScreenScroller.DEFAULT_DURATION);
            mScroller.setDuration(duration);
            array.recycle();
        }
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mField.set(this, mScroller);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        addOnPageChangeListener(new OnScreenChangeListener());

        EventBus.getDefault().register(this);
    }

    public void setScrollDuration(int duration) {
        mScroller.setDuration(duration);
    }

    public boolean isScrolling() {
        return mState != SCROLL_STATE_IDLE;
    }

    @Subscribe
    public void onEvent(ChangeScreenEvent event) {
        setCurrentItem(event.getSid(), true);
    }

    @Override
    public boolean isMoving() {
        return isScrolling();
    }

    private class ScreenScroller extends Scroller {

        private static final int DEFAULT_DURATION = 1000;

        private int mDuration = DEFAULT_DURATION;

        public ScreenScroller(Context context) {
            super(context);
        }

        public ScreenScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ScreenScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        public void setDuration(int duration) {
            mDuration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    private class OnScreenChangeListener extends SimpleOnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            mState = state;
        }

    }

}
