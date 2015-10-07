package com.keith.miniframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.keith.miniframework.ChangeScreenEvent;
import com.keith.miniframework.data.Slot;

import de.greenrobot.event.EventBus;

public class SlotView extends ImageView implements Movable, View.OnFocusChangeListener {

    private static final String TAG = "SlotView";

    public SlotView(Context context) {
        super(context);
        init();
    }

    public SlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnFocusChangeListener(this);
    }

    @Override
    public boolean isMoving() {
        Object object = getParent();
        if (object != null && object instanceof Movable) {
            Movable movable = (Movable) getParent();
            return movable.isMoving();
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (getTag() != null && getTag() instanceof Slot) {
                Slot slot = (Slot) getTag();
                EventBus.getDefault().post(new ChangeScreenEvent(slot.getSid()));
            }
        }
    }
}
