package com.keith.miniframework.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.keith.miniframework.R;
import com.keith.miniframework.data.Screen;
import com.keith.miniframework.data.ScreenHelper;
import com.keith.miniframework.util.FocusHelper;

import java.util.List;

public class SimpleActivity extends FragmentActivity {

    private static final String TAG = "SimpleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        List<Screen> screens = ScreenHelper.createScreens();

        long begin = System.currentTimeMillis();
        FocusHelper.defineNextFocusId(ScreenHelper.createScreens());
        long end = System.currentTimeMillis();
        Log.e(TAG, "focus define take " + (end - begin) + " ms.");

        ScreenAdapter adapter = new ScreenAdapter(getSupportFragmentManager(), screens);
        pager.setPageTransformer(true, new CubeOutTransformer());
        pager.setAdapter(adapter);

    }
}
