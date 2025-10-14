package com.mhy.shapeview


import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import org.xmlpull.v1.XmlPullParser
import com.mhy.shapeview.sample.R

class ShadowDrawable : Drawable() {

    private var paint: Paint = Paint()
    private var mRect: Rect = Rect()
    private var newPaint = Paint()
    private var shadeTop = 0
    private var shadeBottom = 0
    private var shadeRight = 0
    private var shadeLeft = 0
    private var cornerRadius = 0
    private var shadeRadius = 0
    private var shadeX = 0
    private var shadeY = 0
    private var solidColor = Color.TRANSPARENT
    private var shadeColor = Color.TRANSPARENT

    override fun inflate(
        r: Resources,
        parser: XmlPullParser,
        attrs: AttributeSet,
        theme: Resources.Theme?
    ) {
        super.inflate(r, parser, attrs, theme)
        val attributes = r.obtainAttributes(attrs, R.styleable.shadow_drawable)
        shadeTop = attributes.getDimensionPixelSize(R.styleable.shadow_drawable_top, 0)
        shadeLeft = attributes.getDimensionPixelSize(R.styleable.shadow_drawable_left, 0)
        shadeRight = attributes.getDimensionPixelSize(R.styleable.shadow_drawable_right, 0)
        shadeBottom = attributes.getDimensionPixelSize(R.styleable.shadow_drawable_bottom, 0)
        solidColor = attributes.getColor(R.styleable.shadow_drawable_solid_color, Color.TRANSPARENT)
        shadeColor = attributes.getColor(R.styleable.shadow_drawable_shade_color, Color.GRAY)
        shadeX = attributes.getColor(R.styleable.shadow_drawable_shadeOffsetX, 0)
        shadeY = attributes.getColor(R.styleable.shadow_drawable_shadeOffsetY, 0)
        cornerRadius = attributes.getDimensionPixelSize(R.styleable.shadow_drawable_corner_radius, 0)
        shadeRadius = attributes.getDimensionPixelSize(R.styleable.shadow_drawable_shade_radius, 0)
        attributes.recycle()
    }

    override fun draw(canvas: Canvas) {
        newPaint.setColor(solidColor)
        //阴影绘制区域
        mRect.set(
            bounds.left + shadeLeft,
            bounds.top + shadeTop,
            bounds.right - shadeRight,
            bounds.bottom - shadeBottom
        )
        //y向上偏移shadeRadius
        newPaint.setShadowLayer(shadeRadius.toFloat(), 0F, -shadeRadius.toFloat(), shadeColor)
        //将Drawable绘制出来。
        // canvas.drawRoundRect(RectF(mRect), shadeCorner.toFloat(), shadeCorner.toFloat(), newPaint)
        // 使用 Path 绘制顶部圆角
        val path = Path()
        path.moveTo(mRect.left.toFloat(), mRect.top + cornerRadius.toFloat())
        path.quadTo(
            mRect.left.toFloat(), mRect.top.toFloat(),
            mRect.left + cornerRadius.toFloat(), mRect.top.toFloat()
        )
        path.lineTo(mRect.right - cornerRadius.toFloat(), mRect.top.toFloat())
        path.quadTo(
            mRect.right.toFloat(), mRect.top.toFloat(),
            mRect.right.toFloat(), mRect.top + cornerRadius.toFloat()
        )
        path.lineTo(mRect.right.toFloat(), mRect.bottom.toFloat())
        path.lineTo(mRect.left.toFloat(), mRect.bottom.toFloat())
        path.close()

        canvas.drawPath(path, newPaint)
    }


    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.setColorFilter(colorFilter)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}