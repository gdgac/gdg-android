package org.gdgac.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.api.services.plus.model.Person;
import org.gdgac.android.R;
import org.gdgac.android.view.NetworkedCacheableImageView;

/**
 * GDG Aachen
 * org.gdgac.android.adapter
 * <p/>
 * User: maui
 * Date: 26.04.13
 * Time: 08:24
 */
public class OrganizerAdapter extends ArrayAdapter<Person> {

    private LayoutInflater mInflater;

    public OrganizerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = mInflater.inflate(R.layout.list_organizer_item, null);

        Person item = (Person) getItem(position);

        NetworkedCacheableImageView picture = (NetworkedCacheableImageView) convertView.findViewById(R.id.icon);
        picture.loadImage(item.getImage().getUrl(), true);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(item.getDisplayName());

        return convertView;
    }
}
