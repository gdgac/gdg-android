package org.gdgac.android.graphics.drawable;

import android.graphics.*;
import android.graphics.drawable.Drawable;

/**
 * GDG Aachen
 * org.gdgac.android.graphics.drawable
 * <p/>
 * User: maui
 * Date: 22.04.13
 * Time: 17:21
 */
public class RoundCornerDrawable extends Drawable {

    private float mCornerRadius;
    private Paint mPaint;
    private int mMargin;
    private BitmapShader mBitmapShader;
    private RectF mRect = new RectF();

    public RoundCornerDrawable(Bitmap bitmap, float cornerRadius, int margin) {
        mCornerRadius = cornerRadius;
        mMargin = margin;
        mBitmapShader = new BitmapShader (bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);
    }

    public RectF getRect() {
        return mRect;
    }

    public BitmapShader getBitmapShader() {
        return mBitmapShader;
    }

    public Paint getPaint() {
        return mPaint;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRect.set(mMargin, mMargin, bounds.width() - mMargin, bounds.height() - mMargin);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mPaint);
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
