package com.keith.miniframework.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreenHelper {

    private static List<Screen> sScreens;

    public static List<Screen> createScreens() {
        if (sScreens == null) {
            sScreens = new ArrayList<>();
            Map<Integer, Screen> screenMap = new HashMap<>();
            List<Slot> slots = SlotHelper.createSlots(1080, 720, 0, 0);
            for (Slot slot : slots) {
                int id = slot.getSid();
                Screen screen = screenMap.get(id);
                if (screen == null) {
                    screen = new Screen(id);
                    screenMap.put(id, screen);
                }
                screen.addSlot(slot);
            }
            sScreens.addAll(screenMap.values());
        }
        return sScreens;
    }

}
