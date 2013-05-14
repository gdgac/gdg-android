package org.gdgac.android.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * GDG Aachen
 * org.gdgac.android.view
 * <p/>
 * User: maui
 * Date: 23.04.13
 * Time: 23:42
 */
public class AnimationImageView extends ImageView {

    public AnimationImageView(Context context) {
        super(context);
        initAnimation();
    }

    public AnimationImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimation();
    }

    public AnimationImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAnimation();
    }

    private void initAnimation() {
        if(getDrawable() != null && getDrawable() instanceof AnimationDrawable) {
            AnimationDrawable frameAnimation = (AnimationDrawable) getDrawable();
            frameAnimation.start();
        }
    }
}
