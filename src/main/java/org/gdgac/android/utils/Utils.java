package org.gdgac.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * GDG Aachen
 * org.gdgac.android.utils
 * <p/>
 * User: maui
 * Date: 21.04.13
 * Time: 22:34
 */
public class Utils {
    public static <T> List<T> createListOfType(Class<T> type) {
        return new ArrayList<T>();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager mConMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (mConMgr.getActiveNetworkInfo() != null
                && mConMgr.getActiveNetworkInfo().isAvailable()
                && mConMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmulator() {
        Log.d("GDG", Build.PRODUCT);
        return Build.PRODUCT.equals("google_sdk");
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        is.close();

        return sb.toString();
    }

    public static ArrayList<View> findViewByType(ViewGroup root, Class clazz) {
        ArrayList<View> views = new ArrayList<View>();
        int count = root.getChildCount();
        for (int i = 0; i <= count; i++) {
            View v = root.getChildAt(i);
            if(v != null) {
                if (v.getClass().equals(clazz)) {
                    views.add(v);
                } else if(v instanceof ViewGroup) {
                    views.addAll(findViewByType((ViewGroup)v, clazz));
                }
            }
        }
        return views;
    }
}
