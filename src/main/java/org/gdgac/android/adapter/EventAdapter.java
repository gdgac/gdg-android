package org.gdgac.android.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusOneButton;
import com.google.api.services.plus.model.Activity;
import org.gdgac.android.R;
import org.gdgac.android.api.model.Event;
import org.gdgac.android.view.NetworkedCacheableImageView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * GDG Aachen
 * org.gdgac.android.adapter
 * <p/>
 * User: maui
 * Date: 23.04.13
 * Time: 03:53
 */
public class EventAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Item> mEvents;

    public EventAdapter(Context ctx) {
        mContext = ctx;
        mInflater = LayoutInflater.from(mContext);
        mEvents = new ArrayList<Item>();
    }

    public void addAll(Collection<Event> items) {
        for(Event a : items) {
            mEvents.add(new Item(a));
        }
        notifyDataSetChanged();
    }

    public void add(Event item) {
        mEvents.add(new Item(item));
        notifyDataSetChanged();
    }

    public void clear() {
        mEvents.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int i) {
        return mEvents.get(i).getEvent();
    }

    private Item getItemInternal(int i) {
        return mEvents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = mInflater.inflate(R.layout.list_event_item, null);

        Item item = (Item) getItemInternal(i);
        Event event = item.getEvent();

        NetworkedCacheableImageView picture = (NetworkedCacheableImageView) view.findViewById(R.id.icon);
        picture.loadImage("https://developers.google.com"+event.getIconUrl(), true);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(event.getTitle());

        TextView line2 = (TextView)view.findViewById(R.id.line2);
        line2.setText(event.getStart().toString());

        // That item will contain a special property that tells if it was freshly retrieved
        if (!item.isConsumed()) {
            item.setConsumed(true);
            // In which case we magically instantiate our effect and launch it directly on the view
            Animation animation = AnimationUtils.makeInChildBottomAnimation(mContext);
            view.startAnimation(animation);
        }

        return view;
    }

    public class Item {
        private Event mEvent;
        private boolean mConsumed = false;

        public Item(Event a) {
            mEvent = a;
            mConsumed = false;
        }

        public boolean isConsumed() {
            return mConsumed;
        }

        public void setConsumed(boolean mConsumed) {
            this.mConsumed = mConsumed;
        }

        public Event getEvent() {
            return mEvent;
        }

        public void setEvent(Event mEvent) {
            this.mEvent = mEvent;
        }
    }
}