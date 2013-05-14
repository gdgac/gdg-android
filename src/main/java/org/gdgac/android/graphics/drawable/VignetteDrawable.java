package org.gdgac.android.graphics.drawable;

import android.graphics.*;

/**
 * GDG Aachen
 * org.gdgac.android.graphics.drawable
 * <p/>
 * User: maui
 * Date: 22.04.13
 * Time: 17:19
 */
public class VignetteDrawable extends RoundCornerDrawable {
    private boolean mUseGradientOverlay;


    public VignetteDrawable(Bitmap bitmap, float cornerRadius, int margin) {
        super(bitmap, cornerRadius, margin);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        int[] colors = new int[] { 0,0, 0x7f000000 };
        float[] pos = new float[] { 0.0f, 0.7f, 1.0f };

        RadialGradient vignette = new RadialGradient(getRect().centerX(), getRect().centerY() * 1.0f / 0.7f, getRect().centerX() * 1.3f, colors, pos, Shader.TileMode.CLAMP);
        Matrix oval = new Matrix();
        oval.setScale(1.0f, 0.7f);
        vignette.setLocalMatrix(oval);

        getPaint().setShader(new ComposeShader(getBitmapShader(), vignette, PorterDuff.Mode.SRC_OVER));
    }
}
