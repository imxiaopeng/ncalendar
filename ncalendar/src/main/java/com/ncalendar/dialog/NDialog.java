package com.ncalendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.ncalendar.R;

/**
 * NDialog
 *
 * @author Created by cxp on 2020/9/16.
 */
public abstract class NDialog extends Dialog {
    public NDialog(Context context) {
        super(context);
    }

    public NDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            onTouchOutside(this);
        }
        return super.onTouchEvent(event);
    }

    protected abstract void onTouchOutside(Dialog dialog);

    private boolean isOutOfBounds(Context context, MotionEvent event) {
        View llRoot = findViewById(R.id.root);
        if (llRoot == null) {
            return false;
        }
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final View decorView = llRoot;
        int w = decorView.getWidth();
        int h = decorView.getHeight();
        int[] l = new int[2];
        decorView.getLocationOnScreen(l);
        int viewX = l[0];
        int viewY = l[1];
        int rightBounds = viewX + w;//右边缘
        int bottomBounds = viewY + h;//下边缘
        return x < viewX || x > rightBounds || y < viewY || y > bottomBounds;
    }
}
