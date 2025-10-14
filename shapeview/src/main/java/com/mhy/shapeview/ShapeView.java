package com.mhy.shapeview;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created By Mahongyin
 * Date    2025/10/10 12:06
 */
public class ShapeView extends View {
    private ShapeProxy.Builder builder;

    public ShapeView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }
    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        builder = ShapeProxy.proxyShapeAttributes(this, context, attrs, defStyleAttr, defStyleRes);
    }
    public ShapeProxy.Builder newBuilder() {
       return builder;
    }
}
