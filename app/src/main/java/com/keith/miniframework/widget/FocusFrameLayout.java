package com.keith.miniframework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.keith.miniframework.R;
import com.keith.miniframework.util.ViewHelper;

import java.lang.ref.SoftReference;

/**
 * @author liangjunfeng
 * @since 2015/8/18 19:19
 */
public class FocusFrameLayout extends FrameLayout {

    private static final String TAG = "FocusFrameLayout";

    private Handler mHandler;

    private Drawable mFocusDrawable;
    private Rect mDrawablePaddingRect;

    private Interpolator mInterpolator;

    private boolean isFocusDrawableVisible;

    int tFrame;
    int mFrame;

    int initL;
    int initT;
    int initR;
    int initB;

    float deltaL;
    float deltaT;
    float deltaR;
    float deltaB;

    public FocusFrameLayout(Context context) {
        super(context);
        init(context, null);
    }

    public FocusFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public FocusFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FocusFrameLayout);
            mFocusDrawable = array.getDrawable(R.styleable.FocusFrameLayout_focusDrawable);
            array.recycle();
        } else {
            mFocusDrawable = context.getResources().getDrawable(R.drawable.ytm_common_focus);
        }
        if (mFocusDrawable != null) {
            mFocusDrawable.setBounds(0, 0, 0, 0);
            mDrawablePaddingRect = new Rect();
            mFocusDrawable.getPadding(mDrawablePaddingRect);
        }
        mHandler = new FocusHandler(this);
        mInterpolator = new AnticipateOvershootInterpolator(1.0f);

        getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    /**
                     *  焦点框出现的时候，希望它位于首个获取焦点的控件的中心
                     */
                    if (!isFocusDrawableVisible) {
                        isFocusDrawableVisible = true;
                        Rect rect = ViewHelper.getBounds(newFocus);
                        rect.left = (rect.right - rect.left) / 2 + rect.left;
                        rect.top = (rect.bottom - rect.top) / 2 + rect.top;
                        rect.right = rect.left;
                        rect.bottom = rect.top;
                        mFocusDrawable.setBounds(rect);
                    }

                    if (newFocus instanceof Movable) {
                        Movable movable = (Movable) newFocus;
                        waitIdle(movable);
                    } else {
                        mHandler.obtainMessage(MSG_ANIM, newFocus).sendToTarget();
                    }
                }
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mFocusDrawable.draw(canvas);
    }

    private void anim(int frame, Rect dstRect) {
        tFrame = frame;
        mFrame = 0;

        Rect srcRect = mFocusDrawable.getBounds();

        dstRect.left = dstRect.left - mDrawablePaddingRect.left;
        dstRect.top = dstRect.top - mDrawablePaddingRect.top;
        dstRect.right = dstRect.right + mDrawablePaddingRect.right;
        dstRect.bottom = dstRect.bottom + mDrawablePaddingRect.bottom;

        initL = srcRect.left;
        initT = srcRect.top;
        initR = srcRect.right;
        initB = srcRect.bottom;

        deltaL = (dstRect.left - initL) / (float) frame;
        deltaT = (dstRect.top - initT) / (float) frame;
        deltaR = (dstRect.right - initR) / (float) frame;
        deltaB = (dstRect.bottom - initB) / (float) frame;

        move();
    }

    private void move() {
        if (mFrame++ < tFrame) {
            Rect srcRect = mFocusDrawable.getBounds();

            float radio = mInterpolator.getInterpolation((float) mFrame / (float) tFrame);
            srcRect.left = (int) (initL + (deltaL * mFrame * radio));
            srcRect.top = (int) (initT + (deltaT * mFrame * radio));
            srcRect.right = (int) (initR + (deltaR * mFrame * radio));
            srcRect.bottom = (int) (initB + (deltaB * mFrame * radio));

            mHandler.sendEmptyMessage(MSG_MOVE);
        }
    }

    private void waitIdle(Movable movable) {
        mHandler.postDelayed(new CheckMovable(movable), 30L);
    }

    private class CheckMovable implements Runnable {

        private Movable movable;

        public CheckMovable(Movable movable) {
            this.movable = movable;
        }

        @Override
        public void run() {
            if (movable.isMoving()) {
                waitIdle(movable);
            }
            mHandler.obtainMessage(MSG_ANIM, movable).sendToTarget();
        }

    }

    private static final int MSG_MOVE = 0;
    private static final int MSG_ANIM = 1;

    private static class FocusHandler extends Handler {
        private SoftReference<FocusFrameLayout> mRef;

        public FocusHandler(FocusFrameLayout layout) {
            mRef = new SoftReference<>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
            FocusFrameLayout layout = mRef.get();
            if (layout != null) {
                switch (msg.what) {
                    case MSG_MOVE:
                        layout.invalidate();
                        layout.move();
                        break;
                    case MSG_ANIM:
                        View view = (View) msg.obj;
                        Rect rect = ViewHelper.getBounds(view);
                        layout.anim(24, rect);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}