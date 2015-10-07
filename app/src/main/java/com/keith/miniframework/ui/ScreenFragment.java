package com.keith.miniframework.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keith.miniframework.R;
import com.keith.miniframework.data.Screen;
import com.keith.miniframework.data.Slot;
import com.keith.miniframework.widget.ScreenLayout;
import com.keith.miniframework.widget.SlotView;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class ScreenFragment extends Fragment {

    private static final int[] COLORS = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY, Color.CYAN,
            Color.DKGRAY, Color.MAGENTA, Color.LTGRAY, Color.WHITE};
    private static final Random RANDOM = new Random();

    public static ScreenFragment newInstance(Screen screen) {
        ScreenFragment fragment = new ScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("SCREEN", screen);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScreenLayout root = (ScreenLayout) inflater.inflate(R.layout.fragment_screen, container, false);

        Screen screen = (Screen) getArguments().getSerializable("SCREEN");
        if (screen != null) {
            for (Slot slot : screen.getSlots()) {
                ScreenLayout.LayoutParams lp = new ScreenLayout.LayoutParams(slot.getWidth(), slot.getHeight(), slot.getX(), slot.getY());
                SlotView slotView = new SlotView(getContext());
                slotView.setLayoutParams(lp);
                slotView.setId(slot.getId());
                slotView.setNextFocusLeftId(slot.leftId);
                slotView.setNextFocusRightId(slot.rightId);
                slotView.setBackgroundColor(Color.LTGRAY + 16 * screen.getId());
                slotView.setFocusable(true);
                slotView.setFocusableInTouchMode(true);
                slotView.setTag(slot);
                Picasso.with(getContext()).load(slot.getImage()).into(slotView);
                root.addView(slotView);
            }
        }
        root.setBackgroundColor(COLORS[RANDOM.nextInt(100000) % COLORS.length]);
        return root;
    }

}
