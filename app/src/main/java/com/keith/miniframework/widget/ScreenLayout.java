package com.keith.miniframework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsoluteLayout;

public class ScreenLayout extends AbsoluteLayout implements Movable {

    public ScreenLayout(Context context) {
        super(context);
    }

    public ScreenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
