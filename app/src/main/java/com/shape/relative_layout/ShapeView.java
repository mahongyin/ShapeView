package com.shape.relative_layout;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mhy.shapeview.ShapeProxy;

/**
 * Created By Mahongyin
 * Date    2025/10/15 12:27
 */
public class ShapeView extends RelativeLayout {
    public ShapeView(@NonNull Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ShapeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public ShapeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public ShapeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }


    private ShapeProxy.Builder builder;
    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        builder = ShapeProxy.proxyShapeAttributes(this, context, attrs, defStyleAttr, defStyleRes);
    }
    public ShapeProxy.Builder newBuilder() {
        return builder;
    }
}
