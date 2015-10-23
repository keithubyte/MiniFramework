package com.keith.miniframework.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SlotHelper {

    private static final String TAG = "SlotHelper";

    private static final List<Slot> sSlots = new ArrayList<>();

    public static List<Slot> createSlots(int width, int height, int paddingW, int paddingH) {
        float radioW = (width - (paddingW * 2)) / 1080.0f;
        float radioH = (height - (paddingH * 2)) / 720.0f;
        // Log.e(TAG, "radioW = " + radioW + ", radioH = " + radioH);
        if (sSlots.size() == 0) {
            for (int id = 0; id < Images.sData.length; id++) {
                int sid = id / 12;
                int x = (int) ((((id % 4 + 1) * 64 + id % 4 * 400) * radioW) / 1.5f);
                int y = (int) (((((id % 12) / 4 * 300 + ((id % 12) / 4 + 1) * 45)) * radioH) / 1.5f);
                int sw = (int) ((400 * radioW) / 1.5f);
                int sh = (int) ((300 * radioH) / 1.5f);
                Slot slot = new Slot(id, sid, x, y, sw, sh, Images.sData[id]);
                sSlots.add(slot);
            }
        }
        for (Slot slot : sSlots) {
            Log.e(TAG, slot.toString());
        }
        return sSlots;
    }

}
