package com.mhy.shapeview;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created By Mahongyin
 * Date    2025/10/13 15:15
 * android 24才开始支持 xml自定义drawable
 */
public class XmlDrawable extends GradientDrawable {

    public XmlDrawable() {
        super();
    }

    @Override
    public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) throws IOException, XmlPullParserException {
        super.inflate(r, parser, attrs, theme);
        TypedArray ta;
        if (theme == null) {
            ta = r.obtainAttributes(attrs, R.styleable.ShapeView);
        } else {
            ta = theme.obtainStyledAttributes(attrs, R.styleable.ShapeView, 0, 0);
        }
        initAttr(ta);
        ta.recycle();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        super.setBounds(bounds);
    }

    private void initAttr(TypedArray typedArray) {
        //是否有渐变色
        if (typedArray.hasValue(R.styleable.ShapeView_shape_gradientStartColor) && typedArray.hasValue(R.styleable.ShapeView_shape_gradientEndColor)) {
            int gradientStartColor = typedArray.getColor(R.styleable.ShapeView_shape_gradientStartColor, 0);
            int gradientEndColor = typedArray.getColor(R.styleable.ShapeView_shape_gradientEndColor, 0);
            int[] colors;
            if (typedArray.hasValue(R.styleable.ShapeView_shape_gradientCenterColor)) {
                int gradientCenterColor = typedArray.getColor(R.styleable.ShapeView_shape_gradientCenterColor, 0);
                colors = new int[]{gradientStartColor, gradientCenterColor, gradientEndColor};
            } else {
                colors = new int[]{gradientStartColor, gradientEndColor};
            }
            int gradientAngle = typedArray.getInt(R.styleable.ShapeView_shape_gradientAngle, 0);
            setColors(colors);
            setOrientation(getAngle(gradientAngle));
        }
        //默认矩形
        int shape = typedArray.getInt(R.styleable.ShapeView_shape_Type, GradientDrawable.RECTANGLE);
        setShape(shape);
        float cornersRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersRadius, 0.0F);
        float cornersTopLeftRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersTopLeftRadius, 0.0F);
        float cornersTopRightRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersTopRightRadius, 0.0F);
        float cornersBottomLeftRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersBottomLeftRadius, 0.0F);
        float cornersBottomRightRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersBottomRightRadius, 0.0F);

        if (hasValue(cornersRadius)) {
            setCornerRadius(cornersRadius);
        } else if (hasValue(cornersTopLeftRadius) || hasValue(cornersTopRightRadius) || hasValue(cornersBottomLeftRadius) || hasValue(cornersBottomRightRadius)) {
            setCornerRadii(new float[]{cornersTopLeftRadius, cornersTopLeftRadius, cornersTopRightRadius, cornersTopRightRadius, cornersBottomRightRadius, cornersBottomRightRadius, cornersBottomLeftRadius, cornersBottomLeftRadius});
        }
        float strokeWidth = typedArray.getDimension(R.styleable.ShapeView_shape_strokeWidth, 0.0F);
        // 虚线间隙
        float strokeDashGap = typedArray.getDimension(R.styleable.ShapeView_shape_strokeDashGap, 0.0F);
        float strokeDashWidth = typedArray.getDimension(R.styleable.ShapeView_shape_strokeDashWidth, 0.0F);
        int strokeColor = typedArray.getColor(R.styleable.ShapeView_shape_strokeColor, 0);
        if (hasValue(strokeWidth)) {
            if (!hasValue(strokeDashGap) && !hasValue(strokeDashWidth)) {
                setStroke(Math.round(strokeWidth), strokeColor);
            } else {
                setStroke(Math.round(strokeWidth), strokeColor, strokeDashWidth, strokeDashGap);
            }
        }

        //无默认 0线性
        int gradientType = typedArray.getInt(R.styleable.ShapeView_shape_gradientType, GradientDrawable.LINEAR_GRADIENT);
        float gradientCenterX = typedArray.getFloat(R.styleable.ShapeView_shape_gradientCenterX, 0.0F);
        float gradientCenterY = typedArray.getFloat(R.styleable.ShapeView_shape_gradientCenterY, 0.0F);
        float gradientRadius = typedArray.getDimension(R.styleable.ShapeView_shape_gradientRadius, 0.0F);
        boolean useLevel = typedArray.getBoolean(R.styleable.ShapeView_shape_gradientUseLevel, false);

        if (hasValue(gradientType)) {
            setGradientType(gradientType);
            if (hasValue(gradientCenterX) || hasValue(gradientCenterY)) {
                setGradientCenter(gradientCenterX, gradientCenterY);
            }
            if (hasValue(gradientRadius)) {
                setGradientRadius(gradientRadius);
            }
            setUseLevel(useLevel);
        }

        if (typedArray.hasValue(R.styleable.ShapeView_shape_solidColor)) {
            int solidColor = typedArray.getColor(R.styleable.ShapeView_shape_solidColor, 0);
            setColor(solidColor);
        }
//        typedArray.recycle();
    }

    private boolean hasValue(float value) {
        return value > 0.0F;
    }

    private GradientDrawable.Orientation getAngle(int angle) {
        switch (angle) {
            case 0:
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case 45:
                return GradientDrawable.Orientation.BL_TR;
            case 90:
                return GradientDrawable.Orientation.BOTTOM_TOP;
            case 135:
                return GradientDrawable.Orientation.BR_TL;
            case 180:
                return GradientDrawable.Orientation.RIGHT_LEFT;
            case 225:
                return GradientDrawable.Orientation.TR_BL;
            case 270:
                return GradientDrawable.Orientation.TOP_BOTTOM;
            case 315:
                return GradientDrawable.Orientation.TL_BR;
            default:
                return GradientDrawable.Orientation.TOP_BOTTOM;
        }
    }

}
