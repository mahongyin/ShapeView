package com.mhy.shapeview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.core.view.ViewCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created By Mahongyin
 * Date    2024/3/19 12:20
 * issues:
 * 设置background无效，因为代码最后也会设置background, 用solidColor设置填充色 即可
 * backgroundTint 有效
 * use:
 * 自定义View中构造函数调用
 * ShapeProxy.proxyShapeAttributes(this, context, attrs, defStyleAttr);
 * 不能和 View.ClipToOutline混用
 */

public class ShapeProxy {
    private ShapeProxy() {
    }

    public static Builder proxyShapeAttributes(View view, Context context, AttributeSet attrs) {
        return proxyShapeAttributes(view, context, attrs, 0, 0);
    }

    public static Builder proxyShapeAttributes(View view, Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Builder builder = new Builder(view);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeView, defStyleAttr, defStyleRes);
        //默认矩形
        int shape = typedArray.getInt(R.styleable.ShapeView_shape_Type, SHAPE_TYPE_RECTANGLE);
        builder.setShapeType(shape);
        //是否有渐变色
        if (hasValue(typedArray, R.styleable.ShapeView_shape_gradientStartColor) && hasValue(typedArray, R.styleable.ShapeView_shape_gradientEndColor)) {
            int gradientStartColor = typedArray.getColor(R.styleable.ShapeView_shape_gradientStartColor, 0);
            int gradientEndColor = typedArray.getColor(R.styleable.ShapeView_shape_gradientEndColor, 0);
            int[] colors;
            if (hasValue(typedArray, R.styleable.ShapeView_shape_gradientCenterColor)) {
                int gradientCenterColor = typedArray.getColor(R.styleable.ShapeView_shape_gradientCenterColor, 0);
                colors = new int[]{gradientStartColor, gradientCenterColor, gradientEndColor};
            } else {
                colors = new int[]{gradientStartColor, gradientEndColor};
            }
            builder.setGradientColor(colors);

            int gradientAngle = typedArray.getInt(R.styleable.ShapeView_shape_gradientAngle, 0);
            builder.setGradientAngle(gradientAngle);
        }
        //GradientDrawable drawable;
//        if (hasValue(typedArray, R.styleable.ShapeView_shape_gradientStartColor) && hasValue(typedArray, R.styleable.ShapeView_shape_gradientEndColor)) {
//            int[] colors;
//            if (hasValue(typedArray, R.styleable.ShapeView_shape_gradientCenterColor)) {
//                colors = new int[]{gradientStartColor, gradientCenterColor, gradientEndColor};
//            } else {
//                colors = new int[]{gradientStartColor, gradientEndColor};
//            }
//            drawable = new GradientDrawable(getAngle(gradientAngle), colors);
//        } else {
//            drawable = new GradientDrawable();
//        }

        float cornersRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersRadius, 0.0F);
        float cornersTopLeftRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersTopLeftRadius, 0.0F);
        float cornersTopRightRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersTopRightRadius, 0.0F);
        float cornersBottomLeftRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersBottomLeftRadius, 0.0F);
        float cornersBottomRightRadius = typedArray.getDimension(R.styleable.ShapeView_shape_cornersBottomRightRadius, 0.0F);
        builder.setShapeCornersRadius(cornersRadius);
        builder.setShapeCornersTopLeftRadius(cornersTopLeftRadius);
        builder.setShapeCornersTopRightRadius(cornersTopRightRadius);
        builder.setShapeCornersBottomLeftRadius(cornersBottomLeftRadius);
        builder.setShapeCornersBottomRightRadius(cornersBottomRightRadius);

        float strokeWidth = typedArray.getDimension(R.styleable.ShapeView_shape_strokeWidth, 0.0F);
        builder.setShapeStrokeWidth(strokeWidth);
        if (hasValue(typedArray, R.styleable.ShapeView_shape_strokeColor)) {
            builder.setShapeStrokeColor(typedArray.getColor(R.styleable.ShapeView_shape_strokeColor, 0));
        }
        // 虚线间隙
        float strokeDashGap = typedArray.getDimension(R.styleable.ShapeView_shape_strokeDashGap, 0.0F);
        float strokeDashWidth = typedArray.getDimension(R.styleable.ShapeView_shape_strokeDashWidth, 0.0F);
        builder.setShapeStrokeDashGap(strokeDashGap);
        builder.setShapeStrokeDashWidth(strokeDashWidth);
//        if (hasValue(strokeWidth) && hasValue(typedArray, R.styleable.ShapeView_shape_strokeColor)) {
//            if (!hasValue(strokeDashGap) && !hasValue(strokeDashWidth)) {
//                drawable.setStroke(Math.round(strokeWidth), strokeColor);
//            } else {
//                drawable.setStroke(Math.round(strokeWidth), strokeColor, strokeDashWidth, strokeDashGap);
//            }
//        }

        //无默认 0线性
        int gradientType = typedArray.getInt(R.styleable.ShapeView_shape_gradientType, GRADIENT_TYPE_LINEAR);
        float gradientCenterX = typedArray.getFloat(R.styleable.ShapeView_shape_gradientCenterX, 0.0F);
        float gradientCenterY = typedArray.getFloat(R.styleable.ShapeView_shape_gradientCenterY, 0.0F);
        float gradientRadius = typedArray.getDimension(R.styleable.ShapeView_shape_gradientRadius, 0.0F);
        boolean useLevel = typedArray.getBoolean(R.styleable.ShapeView_shape_gradientUseLevel, false);
//        if (hasValue(typedArray, R.styleable.ShapeView_shape_solidColor)) {
//            drawable.setColor(solidColor);
//        }
//
//        if (hasValue(gradientType)) {
//            setGradientType(drawable, gradientType);
//            if (hasValue(gradientCenterX) || hasValue(gradientCenterY)) {
//                drawable.setGradientCenter(gradientCenterX, gradientCenterY);
//            }
//
//            if (hasValue(gradientRadius)) {
//                drawable.setGradientRadius(gradientRadius);
//            }
//
//            drawable.setUseLevel(useLevel);
//        }
//
//        setShape(drawable, shape);
        builder.setGradientType(gradientType);
        builder.setGradientCenterX(gradientCenterX);
        builder.setGradientCenterY(gradientCenterY);
        builder.setGradientRadius(gradientRadius);
        builder.setGradientUseLevel(useLevel);
        if (hasValue(typedArray, R.styleable.ShapeView_shape_solidColor)) {
            builder.setShapeSolidColor(typedArray.getColor(R.styleable.ShapeView_shape_solidColor, 0));
        }
//        if (hasValue(cornersRadius)) {
//            drawable.setCornerRadius(cornersRadius);
//        } else if (hasValue(cornersTopLeftRadius) || hasValue(cornersTopRightRadius) || hasValue(cornersBottomLeftRadius) || hasValue(cornersBottomRightRadius)) {
//            drawable.setCornerRadii(new float[]{cornersTopLeftRadius, cornersTopLeftRadius, cornersTopRightRadius, cornersTopRightRadius, cornersBottomRightRadius, cornersBottomRightRadius, cornersBottomLeftRadius, cornersBottomLeftRadius});
//        }
        //水波纹
        if (hasValue(typedArray, R.styleable.ShapeView_shape_rippleColor)) {
            int rippleColor = typedArray.getColor(R.styleable.ShapeView_shape_rippleColor, 0);
            builder.setShapeRippleColor(rippleColor);
        }
//        if (rippleColor != 0) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                //不需ripple背景
//                /*GradientDrawable backgrounD = new GradientDrawable();
//                backgrounD.setColor(Color.parseColor("#9977ff"));*/
//                //需点击可用，且touch流程没被拦截才有效  无限涟漪:后面俩null  rippleColor应该是带状态的press
//                RippleDrawable rippleDrawable = new RippleDrawable(
//                        ColorStateList.valueOf(rippleColor), drawable, null/*backgrounD*/);
//                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (hasValue(cornersRadius)) {//圆角会使变小球 , 不用它
//                        rippleDrawable.setRadius((int) cornersRadius);
//                    }
//                }*/
//                ViewCompat.setBackground(view, rippleDrawable);
//            } else {
//                /*GradientDrawable maskDrawable = new GradientDrawable();
//                maskDrawable.setColor(rippleColor);
//                setShape(maskDrawable, shape);
//                if (hasValue(cornersRadius)) {
//                    maskDrawable.setCornerRadius(cornersRadius);
//                } else if (hasValue(cornersTopLeftRadius) || hasValue(cornersTopRightRadius) || hasValue(cornersBottomLeftRadius) || hasValue(cornersBottomRightRadius)) {
//                    maskDrawable.setCornerRadii(new float[]{cornersTopLeftRadius, cornersTopLeftRadius, cornersTopRightRadius, cornersTopRightRadius, cornersBottomRightRadius, cornersBottomRightRadius, cornersBottomLeftRadius, cornersBottomLeftRadius});
//                }
////                Drawable rippleDrawable = DrawableCompat.wrap(maskDrawable);
////                DrawableCompat.setTintList(rippleDrawable, ColorStateList.valueOf(rippleColor));
//                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable, maskDrawable});
//                ViewCompat.setBackground(view, layerDrawable);*/
//
//                GradientDrawable pressedDrawable = new GradientDrawable();
//                pressedDrawable.setColor(rippleColor);
//                setShape(pressedDrawable, shape);
//                if (hasValue(cornersRadius)) {
//                    pressedDrawable.setCornerRadius(cornersRadius);
//                } else if (hasValue(cornersTopLeftRadius) || hasValue(cornersTopRightRadius) || hasValue(cornersBottomLeftRadius) || hasValue(cornersBottomRightRadius)) {
//                    pressedDrawable.setCornerRadii(new float[]{cornersTopLeftRadius, cornersTopLeftRadius, cornersTopRightRadius, cornersTopRightRadius, cornersBottomRightRadius, cornersBottomRightRadius, cornersBottomLeftRadius, cornersBottomLeftRadius});
//                }
//                ViewUtilsKt.setSelector(view, drawable, pressedDrawable,
//                        null, null, null, null, null, null);
//            }
//        } else {
//            ViewCompat.setBackground(view, drawable);
//        }
        builder.build();
        typedArray.recycle();
        return builder;
    }

    private static boolean hasValue(float value) {
        return value > 0.0F;
    }

    private static boolean hasValueColor(int value) {
        return value != 0;
    }

    private static boolean hasValue(TypedArray typedArray, @StyleableRes int res) {
        return typedArray.hasValue(res);
    }

    private static void setShape(GradientDrawable drawable, int shape) {
        switch (shape) {
            case 0:
                drawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case 1:
                drawable.setShape(GradientDrawable.OVAL);
                break;
            case 2:
                drawable.setShape(GradientDrawable.LINE);
                break;
            case 3:
                drawable.setShape(GradientDrawable.RING);
        }

    }

    private static void setGradientType(GradientDrawable drawable, int gradientType) {
        switch (gradientType) {
            case 0:
                drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;
            case 1:
                drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                break;
            case 2:
                drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        }

    }

    private static Orientation getAngle(int angle) {
        switch (angle) {
            case 0:
                return Orientation.LEFT_RIGHT;
            case 45:
                return Orientation.BL_TR;
            case 90:
                return Orientation.BOTTOM_TOP;
            case 135:
                return Orientation.BR_TL;
            case 180:
                return Orientation.RIGHT_LEFT;
            case 225:
                return Orientation.TR_BL;
            case 270:
                return Orientation.TOP_BOTTOM;
            case 315:
                return Orientation.TL_BR;
            default:
                return Orientation.TOP_BOTTOM;
        }
    }

    public static final int SHAPE_TYPE_RECTANGLE = 0;
    public static final int SHAPE_TYPE_OVAL = 1;
    public static final int SHAPE_TYPE_LINE = 2;
    public static final int SHAPE_TYPE_RING = 3;
    //渐变类型
    public static final int GRADIENT_TYPE_LINEAR = 0;
    public static final int GRADIENT_TYPE_SWEEP = 1;
    public static final int GRADIENT_TYPE_RADIAL = 2;

    @IntDef({SHAPE_TYPE_RECTANGLE, SHAPE_TYPE_OVAL, SHAPE_TYPE_LINE, SHAPE_TYPE_RING})
    @Retention(RetentionPolicy.SOURCE)
    private @interface SHAPE_TYPE {
    }

    @IntDef({GRADIENT_TYPE_LINEAR, GRADIENT_TYPE_SWEEP, GRADIENT_TYPE_RADIAL})
    @Retention(RetentionPolicy.SOURCE)
    private @interface GRADIENT_TYPE {
    }

    public static class Builder {
        private final View view;
        private int shapeType = SHAPE_TYPE_RECTANGLE;
        private float shapeCornersRadius = 0f;
        private float shapeCornersTopLeftRadius = 0f;
        private float shapeCornersTopRightRadius = 0f;
        private float shapeCornersBottomLeftRadius = 0f;
        private float shapeCornersBottomRightRadius = 0f;
        //线框
        private float shapeStrokeWidth = 0f;
        //虚线框 间隙
        private float shapeStrokeDashGap = 0f;
        private float shapeStrokeDashWidth = 0f;
        private int gradientType = GRADIENT_TYPE_LINEAR;
        @ColorInt
        private int shapeStrokeColor = 0;
        private int[] gradientColor;
        //背景填充色 不要用background-->
        @ColorInt
        private int shapeSolidColor = 0;
        //水波纹颜色
        @ColorInt
        private int shapeRippleColor = 0;
        private int gradientAngle = 0;//角度
        private float gradientCenterX = 0f;
        private float gradientCenterY = 0f;
        private float gradientRadius = 0f;
        private boolean gradientUseLevel = false;

        Builder(View view) {
            this.view = view;
        }

        /**
         * 默认 矩形
         */
        public Builder setShapeType(@SHAPE_TYPE int shapeType) {
            this.shapeType = shapeType;
            return this;
        }

        /**
         * 圆角
         */
        public Builder setShapeCornersRadius(float shapeCornersRadius) {
            this.shapeCornersRadius = shapeCornersRadius;
            return this;
        }

        public Builder setShapeCornersTopLeftRadius(float shapeCornersTopLeftRadius) {
            this.shapeCornersTopLeftRadius = shapeCornersTopLeftRadius;
            return this;
        }

        public Builder setShapeCornersTopRightRadius(float shapeCornersTopRightRadius) {
            this.shapeCornersTopRightRadius = shapeCornersTopRightRadius;
            return this;
        }

        public Builder setShapeCornersBottomLeftRadius(float shapeCornersBottomLeftRadius) {
            this.shapeCornersBottomLeftRadius = shapeCornersBottomLeftRadius;
            return this;
        }

        public Builder setShapeCornersBottomRightRadius(float shapeCornersBottomRightRadius) {
            this.shapeCornersBottomRightRadius = shapeCornersBottomRightRadius;
            return this;
        }

        public Builder setShapeStrokeWidth(float shapeStrokeShapeWidth) {
            this.shapeStrokeWidth = shapeStrokeShapeWidth;
            return this;
        }

        public Builder setShapeStrokeColor(@ColorInt int shapeStrokeShapeColor) {
            this.shapeStrokeColor = shapeStrokeShapeColor;
            return this;
        }

        public Builder setShapeStrokeDashGap(float shapeStrokeDashGap) {
            this.shapeStrokeDashGap = shapeStrokeDashGap;
            return this;
        }

        public Builder setShapeStrokeDashWidth(float shapeStrokeDashWidth) {
            this.shapeStrokeDashWidth = shapeStrokeDashWidth;
            return this;
        }

        /**
         * 无默认
         *
         * @param gradientType
         * @return
         */
        public Builder setGradientType(@GRADIENT_TYPE int gradientType) {
            this.gradientType = gradientType;
            return this;
        }

        public Builder setGradientColor(int[] gradientColor) {
            this.gradientColor = gradientColor;
            return this;
        }

        public Builder setGradientAngle(int gradientAngle) {
            this.gradientAngle = gradientAngle;
            return this;
        }

        public Builder setGradientCenterX(float gradientCenterX) {
            this.gradientCenterX = gradientCenterX;
            return this;
        }

        public Builder setGradientCenterY(float gradientCenterY) {
            this.gradientCenterY = gradientCenterY;
            return this;
        }

        public Builder setGradientRadius(float gradientRadius) {
            this.gradientRadius = gradientRadius;
            return this;
        }

        /**
         * 渐变色
         */
        public Builder setGradientUseLevel(boolean useLevel) {
            this.gradientUseLevel = useLevel;
            return this;
        }

        /**
         * 填充色
         */
        public Builder setShapeSolidColor(@ColorInt int shapeSolidColor) {
            this.shapeSolidColor = shapeSolidColor;
            return this;
        }

        /**
         * 按压水波纹色
         */
        public Builder setShapeRippleColor(@ColorInt int shapeRippleColor) {
            this.shapeRippleColor = shapeRippleColor;
            return this;
        }

        public void build() {
            GradientDrawable drawable = getGradientDrawable();
            //边框
            if (hasValue(shapeStrokeWidth)) {
                if (!hasValue(shapeStrokeDashGap) && !hasValue(shapeStrokeDashWidth)) {
                    drawable.setStroke(Math.round(shapeStrokeWidth), shapeStrokeColor);
                } else {
                    drawable.setStroke(Math.round(shapeStrokeWidth), shapeStrokeColor, shapeStrokeDashWidth, shapeStrokeDashGap);
                }
            }
            if (this.gradientColor != null) {//有渐变色>渐变类型
                ShapeProxy.setGradientType(drawable, gradientType);
                if (hasValue(gradientCenterX) || hasValue(gradientCenterY)) {
                    drawable.setGradientCenter(gradientCenterX, gradientCenterY);
                }
                //渐变半径
                if (hasValue(gradientRadius)) {
                    drawable.setGradientRadius(gradientRadius);
                }
                //使用level-list里定义的不同级别对应不同的图形或颜色
                drawable.setUseLevel(gradientUseLevel);//使用层级
                //drawable.setLevel(0);
            }

            setShape(drawable, shapeType);
            // 圆角
            if (hasValue(shapeCornersRadius)) {
                drawable.setCornerRadius(shapeCornersRadius);
            } else if (hasValue(shapeCornersTopLeftRadius) || hasValue(shapeCornersTopRightRadius) || hasValue(shapeCornersBottomLeftRadius) || hasValue(shapeCornersBottomRightRadius)) {
                //四个圆角半径，依次是左上x、左上y、右上x、右上y、右下x、右下y、左下x、左下y
                drawable.setCornerRadii(new float[]{shapeCornersTopLeftRadius, shapeCornersTopLeftRadius, shapeCornersTopRightRadius, shapeCornersTopRightRadius, shapeCornersBottomRightRadius, shapeCornersBottomRightRadius, shapeCornersBottomLeftRadius, shapeCornersBottomLeftRadius});
            }

            /*
             * 代码执行顺序问题：如果你在设置颜色之后立即对 GradientDrawable 进行了其他操作（比如改变大小、形状等）
             * 那么这些操作可能会覆盖你之前设置的颜色。确保在设置颜色之后没有其他的代码会改变 GradientDrawable 的状态。
             */
            drawable.setColor(shapeSolidColor);

            /*水波纹*/
            if (hasValueColor(shapeRippleColor)) {
                //view.setFocusable(true);
                view.setClickable(true);//需点击可用且touch触摸流程没被拦截才有效
                getRippleDrawable(drawable);
            } else {
                ViewCompat.setBackground(view, drawable);
            }
        }

        private void setSelector(View view,
                                 Drawable normalDrawable,
                                 Drawable pressedDrawable,
                                 Drawable focusedDrawable,
                                 Drawable selectedDrawable,
                                 Drawable enabledDrawable,
                                 Drawable unabledDrawable,
                                 Drawable checkedDrawable,
                                 Drawable checkableDrawable
        ) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            if (pressedDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
            }
            if (selectedDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
            }
            if (checkedDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
            }
            if (focusedDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focusedDrawable);
            }
            if (checkableDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_checkable}, checkableDrawable);
            }
            if (enabledDrawable != null) {
                stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, enabledDrawable);
            }
            if (unabledDrawable != null) {
                stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, unabledDrawable);
            }
            //定义的默认 要放在最下面
            stateListDrawable.addState(new int[]{}, normalDrawable);
            ViewCompat.setBackground(view, stateListDrawable);
        }

        @NonNull
        private GradientDrawable getGradientDrawable() {
            GradientDrawable drawable;
            if (this.gradientColor != null) {
                drawable = new GradientDrawable(getAngle(gradientAngle), gradientColor);
            } else {
                drawable = new GradientDrawable();
            }
            return drawable;
        }

        private void getRippleDrawable(GradientDrawable drawable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                //参数1水波纹色，参数2内容，参数3 ripple背景 null则不需要背景，后边俩参数为null就无限涟漪
                /*GradientDrawable backgrounD = new GradientDrawable();
                backgrounD.setColor(Color.parseColor("#9977ff"));*/
                //需点击可用且touch触摸流程没被拦截才有效  无限涟漪:后面俩null  rippleColor应该是带状态的press
                RippleDrawable rippleDrawable = new RippleDrawable(
                        ColorStateList.valueOf(shapeRippleColor), drawable, null/*backgrounD*/);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (hasValue(shapeCornersRadius)) {//水波纹半径,
                        rippleDrawable.setRadius((int) shapeCornersRadius);
                    }
                }*/
                ViewCompat.setBackground(view, rippleDrawable);
            } else {
                GradientDrawable pressedDrawable = new GradientDrawable(
                        Orientation.LEFT_RIGHT,
                        new int[]{
                                colorAlpha(shapeRippleColor, 0.25),
                                colorAlpha(shapeRippleColor, 0.35),
                                colorAlpha(shapeRippleColor, 0.25)
                        });
                //setGradientType(GRADIENT_TYPE_RADIAL);
                setShape(pressedDrawable, shapeType);
                if (hasValue(shapeStrokeWidth)) {
                    if (!hasValue(shapeStrokeDashGap) && !hasValue(shapeStrokeDashWidth)) {
                        pressedDrawable.setStroke(Math.round(shapeStrokeWidth), shapeStrokeColor);
                    } else {
                        pressedDrawable.setStroke(Math.round(shapeStrokeWidth), shapeStrokeColor, shapeStrokeDashWidth, shapeStrokeDashGap);
                    }
                }
                if (hasValue(shapeCornersRadius)) {
                    pressedDrawable.setCornerRadius(shapeCornersRadius);
                } else if (hasValue(shapeCornersTopLeftRadius) || hasValue(shapeCornersTopRightRadius) || hasValue(shapeCornersBottomLeftRadius) || hasValue(shapeCornersBottomRightRadius)) {
                    //四个圆角半径，依次是左上x、左上y、右上x、右上y、右下x、右下y、左下x、左下y
                    pressedDrawable.setCornerRadii(new float[]{shapeCornersTopLeftRadius, shapeCornersTopLeftRadius, shapeCornersTopRightRadius, shapeCornersTopRightRadius, shapeCornersBottomRightRadius, shapeCornersBottomRightRadius, shapeCornersBottomLeftRadius, shapeCornersBottomLeftRadius});
                }
                //pressedDrawable.setColor(shapeRippleColor);
                setSelector(view, drawable, pressedDrawable,
                        null, null, null, null, null, null);
            }
        }
    }

    /**
     * 转换颜色亮度
     *
     * @param color
     * @param factor 转换亮度为原来的 %  0-1
     * @return
     */
    public static int changeBrightness(int color, @FloatRange(from = 0.00, to = 1.00) double factor) {
        int A = Color.alpha(color);
        int R = Color.red(color);
        int G = Color.green(color);
        int B = Color.blue(color);

        // Adjust brightness
        R = Math.max(0, Math.min(255, (int) (R * factor)));
        G = Math.max(0, Math.min(255, (int) (G * factor)));
        B = Math.max(0, Math.min(255, (int) (B * factor)));

        return Color.argb(A, R, G, B);
    }

    public static int colorRemoveAlpha(int color) {
        return 0xFFFFFF & color;
    }

    /**
     * 给颜色加透明度
     *
     * @param color
     * @param alpha 不透明度 %,[0.35就是35%,  0全透明，1不透明]
     * @return
     */
    public static int colorAlpha(int color, @FloatRange(from = 0.00, to = 1.00) double alpha) {
        int alphaInt = (int) (alpha * 255);// alpha 值转换为 0-255 范围
        return (alphaInt << 24) | (0xFFFFFF & color);
    }

    public static String colorToHex(@ColorInt int color, boolean removeAlpha) {
        if (removeAlpha) {
            //直接不透明了
            return String.format("#%06X", (0xFFFFFF & color));
        } else {
            //return "#"+Integer.toHexString(color);
            return String.format("#%08X", color);
        }
    }
}