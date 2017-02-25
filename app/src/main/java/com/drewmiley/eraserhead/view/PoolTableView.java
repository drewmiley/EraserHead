package com.drewmiley.eraserhead.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.drewmiley.eraserhead.R;

public class PoolTableView extends View {

    private static final double POOL_TABLE_HEIGHT_WIDTH_RATIO = 1.75;

    private int baizeColor;
    private int cushionColor;
    private int lineColor;
    private int pocketColor;

    private final int paddingLeft;
    private final int paddingTop;
    private final int paddingRight;
    private final int paddingBottom;

    public PoolTableView(Context context) {
        super(context);
        init(null, 0);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
    }

    public PoolTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
    }

    public PoolTableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
    }

    private void init(AttributeSet attrs, int defStyle) {
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTableCushions(canvas);
    }

    private void drawTableCushions(Canvas canvas) {
        double contentWidth = getMeasuredWidth() - paddingLeft - paddingRight;
        double contentHeight = getMeasuredHeight() - paddingTop - paddingBottom;

        double poolTableWidth = contentHeight / contentWidth > POOL_TABLE_HEIGHT_WIDTH_RATIO ?
                contentWidth :
                contentHeight / POOL_TABLE_HEIGHT_WIDTH_RATIO;
        double poolTableHeight = contentHeight / contentWidth > POOL_TABLE_HEIGHT_WIDTH_RATIO ?
                contentWidth * POOL_TABLE_HEIGHT_WIDTH_RATIO :
                contentHeight;

        Paint paint = new Paint();
        paint.setColor(cushionColor);

        float left = (float) (contentWidth - poolTableWidth) / 2;
        float top = (float) (contentHeight - poolTableHeight) / 2;
        float right = (float) (contentWidth + poolTableWidth) / 2;
        float bottom = (float) (contentHeight + poolTableHeight) / 2;

        canvas.drawRect(left, top, right, bottom, paint);
    }
}
