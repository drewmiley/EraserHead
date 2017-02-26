package com.drewmiley.eraserhead.gesture;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return true;
    }
}
