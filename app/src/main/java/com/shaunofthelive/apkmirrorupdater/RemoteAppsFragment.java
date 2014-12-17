package com.shaunofthelive.apkmirrorupdater;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class RemoteAppsFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    private ArrayList<AppInfo> mAppList = null;

    // TODO: Rename and change types of parameters
    public static InstalledAppsFragment newInstance(String param1, String param2) {
        InstalledAppsFragment fragment = new InstalledAppsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RemoteAppsFragment() {
    }

    private class FetchAndParse extends AsyncTask<String, Integer, ArrayList<AppInfo>> {
        private static final String TAG = "MY_TAG";

        @Override
        protected ArrayList<AppInfo> doInBackground(String... s) {
            ArrayList<AppInfo> appList = new ArrayList<AppInfo>();

            try {
                Document doc = Jsoup.connect("http://www.apkmirror.com/").get();

                Elements appNameElements = doc.select(".table-row .visible-xs-block:not(.widget_appmanager_recentpostswidget *)");
                ArrayList<String> appNames = new ArrayList<String>();

                for (Element el : appNameElements) {
                    appNames.add(el.text());
                }

                Elements versionElements = doc.select(".infoSlide:not(.widget_appmanager_recentpostswidget *)");
                ArrayList<String> versionStrings = new ArrayList<String>();

                for (Element el : versionElements) {
                    versionStrings.add(el.text());
                }

                Pattern versionPattern = Pattern.compile(".*Latest version: (.*) \\((.*)\\) for Android (.*\\+) \\(.*API ([0-9]+)\\).*");
                String versionName;
                int versionCode;
                int minimumApi;

                int i = 0;
                for (String versionString : versionStrings) {
                    Matcher m = versionPattern.matcher(versionString); // put element here

                    if (m.matches()) {
                        versionName = m.group(1);
                        versionCode = Integer.parseInt(m.group(2));
                        // no group 3; ignore minimum Android version e.g. 4.0.3+
                        minimumApi = Integer.parseInt(m.group(4));
                    } else {
                        versionName = "ERROR";
                        versionCode = 0;
                        minimumApi = 0;
                    }

                    appList.add(new AppInfo("", appNames.get(i), versionName, versionCode, minimumApi));
                    i++;
                }

            } catch(IOException ie) {
                Log.d(TAG, "EXCEPTION: IOException");
            }

            return appList;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(ArrayList<AppInfo> appList) {
            mAppList = appList;
            if (getView() != null) {
                initView(mAppList);
            }
        }
    }

    private void initView(ArrayList<AppInfo> appList) {
        // Create the adapter to convert the array to views
        mAdapter = new AppInfoAdapter(getActivity(), appList);

        // Set the adapter
        mListView = (AbsListView) getView().findViewById(R.id.list_remote_apps);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        new FetchAndParse().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remote_apps, container, false);
        if (mAppList != null) {
            initView(mAppList);
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(...);
            AppInfo app = (AppInfo)mAdapter.getItem(position);
            Toast.makeText(getActivity(), "Version code of "
                            + app.getApplicationName() + ": "
                            + app.getVersionCode(),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onRemoteAppsFragmentInteraction(String id);
    }

    public interface OnFragmentDataInitialized {
        // TODO: Update argument type and name
        public void onRemoteAppsDataInitialized(ArrayList<AppInfo> appList);
    }

}
