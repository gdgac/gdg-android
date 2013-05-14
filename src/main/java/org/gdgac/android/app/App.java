package org.gdgac.android.app;

import android.app.Application;
import com.github.ignition.support.cache.AbstractCache;
import org.gdgac.android.cache.ModelCache;
import uk.co.senab.bitmapcache.BitmapLruCache;

/**
 * Created with IntelliJ IDEA.
 * User: maui
 * Date: 20.04.13
 * Time: 12:09
 */
public class App extends Application {

    private static App mInstance = null;

    public static App getInstance() {
        return mInstance;
    }

    private BitmapLruCache mBitmapCache;
    private ModelCache mModelCache;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public ModelCache getModelCache() {
        if(mModelCache == null) {
            mModelCache = new ModelCache(256, 15, 2);
            mModelCache.enableDiskCache(getApplicationContext(), AbstractCache.DISK_CACHE_SDCARD);
        }
        return mModelCache;
    }

    public BitmapLruCache getBitmapCache() {
        if(mBitmapCache == null)
            mBitmapCache = new BitmapLruCache.Builder(getApplicationContext())
                    .setDiskCacheEnabled(true)
                    .setMemoryCacheEnabled(true)
                    .build();

        return mBitmapCache;
    }
}
