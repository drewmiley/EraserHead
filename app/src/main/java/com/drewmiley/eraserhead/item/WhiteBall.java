package com.drewmiley.eraserhead.item;

import android.graphics.Color;
import android.graphics.Paint;

public class WhiteBall {

    private final double BALL_RADIUS_UNIT_MULTIPLIER = 0.1;

    private int color = Color.WHITE;
    private Paint paint;
    private float x;
    private float y;
    private float rad;
    private float destX;
    private float destY;

    public WhiteBall(float x, float y, float poolTableUnit) {
        setX(x);
        setY(y);
        this.rad = (float) (poolTableUnit * BALL_RADIUS_UNIT_MULTIPLIER);
        Paint paint = new Paint();
        paint.setColor(color);
        this.paint = paint;

        setDestX(x);
        setDestY(y);
    }

    public Paint getPaint() {
        return paint;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRad() {
        return rad;
    }

    public float getDestX() {
        return destX;
    }

    public void setDestX(float destX) {
        this.destX = destX;
    }

    public float getDestY() {
        return destY;
    }

    public void setDestY(float destY) {
        this.destY = destY;
    }
}
