package org.gdgac.android.activity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.actionbarsherlock.app.ActionBar;
import com.viewpagerindicator.TitlePageIndicator;
import org.gdgac.android.adapter.ChapterAdapter;
import org.gdgac.android.app.App;
import org.gdgac.android.R;
import org.gdgac.android.api.ApiException;
import org.gdgac.android.api.GroupDirectory;
import org.gdgac.android.api.model.Chapter;
import org.gdgac.android.api.model.Directory;
import org.gdgac.android.fragment.EventFragment;
import org.gdgac.android.fragment.InfoFragment;
import org.gdgac.android.fragment.NewsFragment;
import org.gdgac.android.task.Builder;
import org.gdgac.android.task.CommonAsyncTask;
import org.gdgac.android.utils.Utils;
import roboguice.inject.InjectView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends GdgActivity implements android.app.ActionBar.OnNavigationListener {

    private static String LOG_TAG = "GDG-MainActivity";
    private ChapterAdapter mSpinnerAdapter;
    private MyAdapter mViewPagerAdapter;

    @InjectView(R.id.pager)
    private ViewPager mViewPager;

    @InjectView(R.id.titles)
    private TitlePageIndicator mIndicator;

    private CommonAsyncTask<Void,Directory> mFetchChaptersTask;

    private LocationManager mLocationManager;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "onCreate");
        setContentView(R.layout.activity_main);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //if(!Utils.isEmulator())
        //    Log.d(LOG_TAG, mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).toString());

        mViewPagerAdapter = new MyAdapter(this, getSupportFragmentManager());
        mSpinnerAdapter = new ChapterAdapter(MainActivity.this, android.R.layout.simple_list_item_1);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, MainActivity.this);

        mFetchChaptersTask = new Builder<Void, Directory>(Void.class, Directory.class)
                .setOnBackgroundExecuteListener(new CommonAsyncTask.OnBackgroundExecuteListener<Void, Directory>() {
                    @Override
                    public Directory doInBackground(Void... params) {
                        try {
                            Directory directory = (Directory) App.getInstance().getModelCache().get(24*60, "chapter_list");

                            if(directory == null) {
                                directory = new GroupDirectory().getDirectory();
                                App.getInstance().getModelCache().put("chapter_list", directory);
                            }
                            return directory;
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .setOnPostExecuteListener(new CommonAsyncTask.OnPostExecuteListener<Directory>() {
                    @Override
                    public void onPostExecute(Directory directory) {
                        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, MainActivity.this);

                        ArrayList<Chapter> chapters = directory.getGroups();
                        Collections.sort(chapters);
                        mSpinnerAdapter.addAll(chapters);

                        mViewPagerAdapter.setSelectedChapter(chapters.get(0));

                        mViewPager.setAdapter(mViewPagerAdapter);
                        mIndicator.setViewPager(mViewPager);
                    }
                }).build();

        if(savedInstanceState == null) {
            Directory directory = (Directory) App.getInstance().getModelCache().get(24*60, "chapter_list");

            if(directory != null) {
                mSpinnerAdapter.addAll(directory.getGroups());

                mViewPagerAdapter.setSelectedChapter(directory.getGroups().get(0));
                mViewPager.setAdapter(mViewPagerAdapter);
                mIndicator.setViewPager(mViewPager);
            } else {
                mFetchChaptersTask.execute();
            }
        } else {

            if(savedInstanceState.containsKey("chapters")) {
                ArrayList<Chapter> chapters = savedInstanceState.getParcelableArrayList("chapters");
                mSpinnerAdapter.addAll(chapters);

                if(savedInstanceState.containsKey("selected_chapter")) {
                    Chapter selectedChapter = savedInstanceState.getParcelable("selected_chapter");
                    mViewPagerAdapter.setSelectedChapter(selectedChapter);
                    getSupportActionBar().setSelectedNavigationItem(mSpinnerAdapter.getPosition(selectedChapter));
                } else {
                    mViewPagerAdapter.setSelectedChapter(chapters.get(0));
                }

                mViewPager.setAdapter(mViewPagerAdapter);
                mIndicator.setViewPager(mViewPager);
            } else {
                mFetchChaptersTask.execute();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mSpinnerAdapter.getCount() > 0)
            outState.putParcelableArrayList("chapters", mSpinnerAdapter.getAll());
        if(mViewPagerAdapter.getSelectedChapter() != null)
            outState.putParcelable("selected_chapter", mViewPagerAdapter.getSelectedChapter());
    }

    @Override
    public boolean onNavigationItemSelected(int position, long l) {
        Chapter previous = mViewPagerAdapter.getSelectedChapter();
        mViewPagerAdapter.setSelectedChapter(mSpinnerAdapter.getItem(position));
        if(!previous.equals(mSpinnerAdapter.getItem(position))) {
            Log.d(LOG_TAG, "Switching chapter!");
            mViewPagerAdapter.notifyDataSetChanged();
        }
        return true;
    }

    public class MyAdapter extends FragmentStatePagerAdapter {
        private Context mContext;
        private Chapter mSelectedChapter;

        public MyAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            mContext = ctx;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            if(mSelectedChapter == null)
                return 0;
            else
                return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return NewsFragment.newInstance(mSelectedChapter.getGplusId());
                case 1:
                    return InfoFragment.newInstance(mSelectedChapter.getGplusId());
                case 2:
                    return EventFragment.newInstance(mSelectedChapter.getGplusId());
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return mContext.getText(R.string.news);
                case 1:
                    return mContext.getText(R.string.info);
                case 2:
                    return mContext.getText(R.string.events);
            }
            return "";
        }

        public Chapter getSelectedChapter() {
            return mSelectedChapter;
        }

        public void setSelectedChapter(Chapter chapter) {
            this.mSelectedChapter = chapter;
        }
    }
}

