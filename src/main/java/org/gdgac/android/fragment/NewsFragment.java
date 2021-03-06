package org.gdgac.android.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockListFragment;
import com.google.android.gms.plus.PlusShare;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;
import org.gdgac.android.app.App;
import org.gdgac.android.Const;
import org.gdgac.android.R;
import org.gdgac.android.activity.GdgActivity;
import org.gdgac.android.adapter.NewsAdapter;
import org.gdgac.android.task.Builder;
import org.gdgac.android.task.CommonAsyncTask;

import java.io.IOException;

/**
 * GDG Aachen
 * org.gdgac.android.fragment
 * <p/>
 * User: maui
 * Date: 20.04.13
 * Time: 12:22
 */
public class NewsFragment extends GdgListFragment {

    private static final String LOG_TAG = "GDG-NewsFragment";

    final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    final JsonFactory mJsonFactory = new GsonFactory();

    private Plus mClient;

    private NewsAdapter mAdapter;

    public static NewsFragment newInstance(String plusId) {
        NewsFragment fragment = new NewsFragment();
        Bundle arguments = new Bundle();
        arguments.putString("plus_id", plusId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause()");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "onActivityCreated()");

        mClient = new Plus.Builder(mTransport, mJsonFactory, null).setGoogleClientRequestInitializer(new CommonGoogleJsonClientRequestInitializer(Const.API_KEY)).build();

        mAdapter = new NewsAdapter(getActivity(), ((GdgActivity)getActivity()).getPlusClient());
        setListAdapter(mAdapter);

        registerForContextMenu(getListView());

        if(getListView() instanceof ListView) {
            ListView listView = (ListView) getListView();
            listView.setDivider(null);
            listView.setDividerHeight(0);
        }

        new Builder<String, ActivityFeed>(String.class, ActivityFeed.class)
                .addParameter(getArguments().getString("plus_id"))
                .setOnPreExecuteListener(new CommonAsyncTask.OnPreExecuteListener() {
                    @Override
                    public void onPreExecute() {
                        setIsLoading(true);
                    }
                })
                .setOnBackgroundExecuteListener(new CommonAsyncTask.OnBackgroundExecuteListener<String, ActivityFeed>() {
                    @Override
                    public ActivityFeed doInBackground(String... params) {
                        try {

                            ActivityFeed feed = (ActivityFeed) App.getInstance().getModelCache().get("news_" + params[0]);

                            if (feed == null) {
                                Plus.Activities.List request = mClient.activities().list(params[0], "public");
                                request.setMaxResults(10L);
                                request.setFields("nextPageToken,items(id,url,object/content,verb,object/attachments(fullImage),object(plusoners,replies,resharers))");
                                feed = request.execute();

                                App.getInstance().getModelCache().put("news_" + params[0], feed);
                            }

                            return feed;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .setOnPostExecuteListener(new CommonAsyncTask.OnPostExecuteListener<ActivityFeed>() {
                    @Override
                    public void onPostExecute(ActivityFeed activityFeed) {
                        mAdapter.addAll(activityFeed.getItems());
                        setIsLoading(false);
                    }
                })
                .buildAndExecute();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        getActivity().getMenuInflater().inflate(R.menu.news_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Activity activity = (Activity) mAdapter.getItem(info.position);

        switch(item.getItemId()) {
            case R.id.share_with_googleplus:
                shareWithGooglePlus(activity);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void shareWithGooglePlus(Activity activity) {
        Intent shareIntent = new PlusShare.Builder(getActivity())
                .setType("text/plain")
                .setContentUrl(Uri.parse(activity.getUrl()))
                .getIntent();

        startActivityForResult(shareIntent, 0);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }
}
