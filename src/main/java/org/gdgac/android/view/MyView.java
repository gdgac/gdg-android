package org.gdgac.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

/**
 * GDG Aachen
 * org.gdgac.android.view
 * <p/>
 * User: maui
 * Date: 29.04.13
 * Time: 16:56
 */
public class MyView extends AbsListView {

    private final static String LOG_TAG = "GDG-MyView";

    private ListAdapter mAdapter;

    private LinearLayout mRoot;
    private ArrayList<ListView> mColumns;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void initLayout() {
        mRoot = new LinearLayout(getContext());
        mRoot.setOrientation(LinearLayout.HORIZONTAL);
        mRoot.setBackgroundColor(Color.BLUE);

        // Defining the LinearLayout layout parameters to fill the parent.
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mRoot.setLayoutParams(llp);

        for(int i = 0; i < 2; i++) {
            ListView list = new ListView(getContext());

            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            l.weight = 1;
            if(i == 0)
                list.setBackgroundColor(Color.BLUE);
            else
                list.setBackgroundColor(Color.RED);
            mRoot.addView(list, i, l);
        }

        addViewInLayout(mRoot, -1, llp, false);

        mRoot.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        mRoot.layout(0,0,getWidth(),getHeight());
        Log.d(LOG_TAG, "layoutInit()");
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        Log.d(LOG_TAG, "setAdapter()");
    }

    @Override
    public ListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setSelection(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            widthMode = MeasureSpec.EXACTLY;
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightMode = MeasureSpec.EXACTLY;
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initLayout();
    }

    @Override
    public void requestLayout() {
        if(mRoot != null)
            mRoot.requestLayout();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //return super.onInterceptTouchEvent(ev);
        return true;
    }

    public class WrapAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getItem(int i) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public long getItemId(int i) {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
