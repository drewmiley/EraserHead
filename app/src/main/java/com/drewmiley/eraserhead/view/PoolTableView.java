package com.drewmiley.eraserhead.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.drewmiley.eraserhead.R;
import com.drewmiley.eraserhead.gesture.DoubleTapListener;
import com.drewmiley.eraserhead.item.WhiteBall;

public class PoolTableView extends View {

    private final double POOL_TABLE_OUTLINE_HEIGHT_UNIT_MULTIPLIER = 7;
    private final double POOL_TABLE_OUTLINE_WIDTH_UNIT_MULTIPLIER = 4;
    private final double POOL_TABLE_BAIZE_HEIGHT_UNIT_MULTIPLIER = 6.2;
    private final double POOL_TABLE_BAIZE_WIDTH_UNIT_MULTIPLIER = 3.2;
    private final double POOL_TABLE_POCKET_RADIUS_UNIT_MULTIPLIER = 0.12;
    private final double POOL_TABLE_LINE_RADIUS_UNIT_MULTIPIER = 0.02;
    private final double POOL_TABLE_LINE_BAULK_LINE_RATIO = 0.2;
    private final double POOL_TABLE_LINE_BLACK_SPOT_RATIO = 0.2;

    private int baizeColor;
    private int cushionColor;
    private int lineColor;
    private int pocketColor;

    private GestureDetector doubleTapDetector;

    private double contentHeight;
    private double contentWidth;
    private float poolTableUnit;

    public PoolTableView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PoolTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PoolTableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        doubleTapDetector = new GestureDetector(context, new DoubleTapListener());

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PoolTableView, defStyle, 0);

        baizeColor = a.getColor(
                R.styleable.PoolTableView_baizeColor,
                baizeColor);
        cushionColor = a.getColor(
                R.styleable.PoolTableView_cushionColor,
                cushionColor);
        lineColor = a.getColor(
                R.styleable.PoolTableView_lineColor,
                lineColor);
        pocketColor = a.getColor(
                R.styleable.PoolTableView_pocketColor,
                pocketColor);

        a.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            onTouch(motionEvent.getX(), motionEvent.getY());
        }
        if (action == MotionEvent.ACTION_MOVE) {
            onDrag(motionEvent.getX(), motionEvent.getY());
        }

        // Whether this event represents a double-tap
        boolean doubleTapped = doubleTapDetector.onTouchEvent(motionEvent);

        // If the cell is double tapped raise the event
        if(doubleTapped) {
            onDoubleTap(motionEvent.getX(), motionEvent.getY());
        }

        return true;
    }

    private void onTouch(float x, float y) {
        System.out.println("Touch");
        System.out.println(x);
        System.out.println(y);
    }

    private void onDrag(float x, float y) {
        System.out.println("Drag");
        System.out.println(x);
        System.out.println(y);
    }

    private void onDoubleTap(float x, float y) {
        System.out.println("DoubleTap");
        System.out.println(x);
        System.out.println(y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        contentHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        contentWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        poolTableUnit = calculatePoolTableUnit();

        drawTableCushions(canvas);
        drawTableBaize(canvas);
        drawTablePockets(canvas);
        drawTableLines(canvas);

        drawWhiteBall(canvas, (float) contentWidth / 2, (float) contentHeight / 2);
    }

    private float calculatePoolTableUnit() {
        double poolTableUnit = contentHeight * POOL_TABLE_OUTLINE_WIDTH_UNIT_MULTIPLIER > contentWidth * POOL_TABLE_OUTLINE_HEIGHT_UNIT_MULTIPLIER ?
                contentWidth / POOL_TABLE_OUTLINE_WIDTH_UNIT_MULTIPLIER :
                contentHeight / POOL_TABLE_OUTLINE_HEIGHT_UNIT_MULTIPLIER;
        return (float) poolTableUnit;
    }

    private void drawTableCushions(Canvas canvas) {
        double poolTableCushionsWidth = poolTableUnit * POOL_TABLE_OUTLINE_WIDTH_UNIT_MULTIPLIER;
        double poolTableCushionsHeight = poolTableUnit * POOL_TABLE_OUTLINE_HEIGHT_UNIT_MULTIPLIER;

        Paint paint = new Paint();
        paint.setColor(cushionColor);

        float left = (float) (contentWidth - poolTableCushionsWidth) / 2;
        float top = (float) (contentHeight - poolTableCushionsHeight) / 2;
        float right = (float) (contentWidth + poolTableCushionsWidth) / 2;
        float bottom = (float) (contentHeight + poolTableCushionsHeight) / 2;

        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawTableBaize(Canvas canvas) {
        double poolTableBaizeWidth = poolTableUnit * POOL_TABLE_BAIZE_WIDTH_UNIT_MULTIPLIER;
        double poolTableBaizeHeight = poolTableUnit * POOL_TABLE_BAIZE_HEIGHT_UNIT_MULTIPLIER;

        Paint paint = new Paint();
        paint.setColor(baizeColor);

        float left = (float) (contentWidth - poolTableBaizeWidth) / 2;
        float top = (float) (contentHeight - poolTableBaizeHeight) / 2;
        float right = (float) (contentWidth + poolTableBaizeWidth) / 2;
        float bottom = (float) (contentHeight + poolTableBaizeHeight) / 2;

        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawTablePockets(Canvas canvas) {
        double poolTableBaizeWidth = poolTableUnit * POOL_TABLE_BAIZE_WIDTH_UNIT_MULTIPLIER;
        double poolTableBaizeHeight = poolTableUnit * POOL_TABLE_BAIZE_HEIGHT_UNIT_MULTIPLIER;

        Paint paint = new Paint();
        paint.setColor(pocketColor);

        float left = (float) (contentWidth - poolTableBaizeWidth) / 2;
        float top = (float) (contentHeight - poolTableBaizeHeight) / 2;
        float right = (float) (contentWidth + poolTableBaizeWidth) / 2;
        float bottom = (float) (contentHeight + poolTableBaizeHeight) / 2;

        float rad = (float) (poolTableUnit * POOL_TABLE_POCKET_RADIUS_UNIT_MULTIPLIER);

        canvas.drawCircle(left, top, rad, paint);
        canvas.drawCircle(right, top, rad, paint);
        canvas.drawCircle(left, (top + bottom) / 2, rad, paint);
        canvas.drawCircle(right, (top + bottom) / 2, rad, paint);
        canvas.drawCircle(left, bottom, rad, paint);
        canvas.drawCircle(right, bottom, rad, paint);
    }

    private void drawTableLines(Canvas canvas) {
        double poolTableBaizeWidth = poolTableUnit * POOL_TABLE_BAIZE_WIDTH_UNIT_MULTIPLIER;
        double poolTableBaizeHeight = poolTableUnit * POOL_TABLE_BAIZE_HEIGHT_UNIT_MULTIPLIER;

        Paint paint = new Paint();
        paint.setColor(lineColor);

        float left = (float) (contentWidth - poolTableBaizeWidth) / 2;
        float top = (float) (contentHeight - poolTableBaizeHeight) / 2;
        float right = (float) (contentWidth + poolTableBaizeWidth) / 2;
        float bottom = (float) (contentHeight + poolTableBaizeHeight) / 2;

        float rad = (float) (poolTableUnit * POOL_TABLE_LINE_RADIUS_UNIT_MULTIPIER);

        canvas.drawRect(left,
                (float) ((1 - POOL_TABLE_LINE_BAULK_LINE_RATIO) * top + POOL_TABLE_LINE_BAULK_LINE_RATIO * bottom - rad / 2),
                right,
                (float) ((1 - POOL_TABLE_LINE_BAULK_LINE_RATIO) * top + POOL_TABLE_LINE_BAULK_LINE_RATIO * bottom + rad / 2),
                paint);
        canvas.drawCircle((left + right) / 2, (float) (POOL_TABLE_LINE_BLACK_SPOT_RATIO * top + (1 - POOL_TABLE_LINE_BLACK_SPOT_RATIO) * bottom), rad, paint);
    }

    private void drawWhiteBall(Canvas canvas, float x, float y) {
        WhiteBall whiteBall = new WhiteBall(x, y, poolTableUnit);

        canvas.drawCircle(whiteBall.getX(), whiteBall.getY(), whiteBall.getRad(), whiteBall.getPaint());
    }
}
