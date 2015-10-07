package com.keith.miniframework.util;

import android.view.View;

import com.keith.miniframework.data.Screen;
import com.keith.miniframework.data.Slot;

import java.util.Collections;
import java.util.List;

/**
 * Created by keith on 15/10/6.
 */
public class FocusHelper {

    private static final String TAG = "FocusHelper";

    public static void defineNextFocusId(List<Screen> screens) {
        defineNextFocusLeft(screens);
        defineNextFocusRight(screens);
    }

    public static void defineNextFocusRight(List<Screen> screens) {
        for (int i = 0; i < screens.size(); i++) {
            List<Slot> currentScreenSlots = screens.get(i).getSlots();
            List<Slot> nextScreenSlots = Collections.emptyList();
            if (i < screens.size() - 1) {
                nextScreenSlots = screens.get(i + 1).getSlots();
            }

            for (Slot slot : currentScreenSlots) {
                slot.rightId = findRightId(slot, currentScreenSlots) == View.NO_ID
                        ? findRightId(slot, nextScreenSlots) : View.NO_ID;
            }
        }
    }

    public static void defineNextFocusLeft(List<Screen> screens) {
        for (int i = 0; i < screens.size(); i++) {
            List<Slot> currentScreenSlots = screens.get(i).getSlots();
            List<Slot> previousScreenSlots = Collections.emptyList();
            if (i > 1) {
                previousScreenSlots = screens.get(i - 1).getSlots();
            }

            for (Slot slot : currentScreenSlots) {
                slot.leftId = findLeftId(slot, currentScreenSlots) == View.NO_ID
                        ? findLeftId(slot, previousScreenSlots) : View.NO_ID;
            }
        }
    }

    public static int findLeftId(Slot slot, List<Slot> slots) {
        double minDistance = 0.0;
        int leftId = View.NO_ID;
        int size = slots.size();
        for (int i = 0; i < size; i++) {
            Slot temp = slots.get(i);
            if (isLeft(slot, temp)) {
                double distance = getDistance(slot, temp);
                if (distance < minDistance || minDistance == 0.0) {
                    minDistance = distance;
                    leftId = temp.getId();
                }
            }
        }
        return leftId;
    }

    public static int findRightId(Slot slot, List<Slot> slots) {
        double minDistance = 0.0;
        int rightId = View.NO_ID;
        int size = slots.size();
        for (int i = 0; i < size; i++) {
            Slot temp = slots.get(i);
            if (isRight(slot, temp)) {
                double distance = getDistance(slot, temp);
                if (distance < minDistance || minDistance == 0.0) {
                    minDistance = distance;
                    rightId = temp.getId();
                }
            }
        }
        return rightId;
    }

    public static boolean isLeft(Slot s1, Slot s2) {
        return s2.right < s1.left;
    }

    public static boolean isRight(Slot s1, Slot s2) {
        return s2.left > s1.right;
    }

    public static double getDistance(Slot s1, Slot s2) {
        double[] center1 = getSlotCenter(s1);
        double[] center2 = getSlotCenter(s2);
        return Math.sqrt(Math.pow(center1[0] - center2[0], 2.0) + Math.pow(center1[1] - center2[1], 2.0));
    }

    public static double[] getSlotCenter(Slot slot) {
        double[] center = new double[2];
        center[0] = slot.left + (slot.right - slot.left) / 2;
        center[1] = slot.top + (slot.bottom - slot.top) / 2;
        return center;
    }

}
