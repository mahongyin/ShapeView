package com.mhy.shapeview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created By Mahongyin
 * Date    2025/10/10 12:06
 */
public class ShapeView extends View {
    private final ShapeProxy.Builder builder;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        builder = ShapeProxy.proxyShapeAttributes(this, context, attrs, defStyleAttr, defStyleRes);
    }

    public ShapeProxy.Builder newBuilder() {
       return builder;
    }
}
