package com.keith.miniframework.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Screen implements Serializable {

    private static final int UID = 1024;

    private int id;
    private List<Slot> slots;

    public Screen(int id) {
        this.id = id;
        this.slots = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public void addSlot(Slot slot) {
        slots.add(slot);
    }
}
