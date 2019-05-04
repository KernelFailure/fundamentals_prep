package com.example.fundamentals;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class SquareImageViewHeight extends AppCompatImageView {
    public SquareImageViewHeight(Context context) {
        super(context);
    }

    public SquareImageViewHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageViewHeight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
