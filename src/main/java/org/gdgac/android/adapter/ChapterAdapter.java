package org.gdgac.android.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import org.gdgac.android.api.model.Chapter;

import java.util.ArrayList;
import java.util.List;

/**
 * GDG Aachen
 * org.gdgac.android.adapter
 * <p/>
 * User: maui
 * Date: 24.04.13
 * Time: 00:39
 */
public class ChapterAdapter extends ArrayAdapter<Chapter> {

    public ChapterAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ChapterAdapter(Context context, int textViewResourceId, List<Chapter> objects) {
        super(context, textViewResourceId, objects);
    }

    public ArrayList<Chapter> getAll() {
        ArrayList<Chapter> chapters = new ArrayList<Chapter>();
        for(int i = 0; i < getCount(); i++) {
            chapters.add(getItem(i));
        }
        return chapters;
    }
}
