package com.shaunofthelive.apkmirrorupdater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shaun on 2014-12-07.
 */
public class AppInfoAdapter extends ArrayAdapter<AppInfo> {
    private ArrayList<AppInfo> items = null;
    private Filter filter;

    private class AppFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = items;
                results.count = items.size();
            }
            else {
                // We perform filtering operation
                ArrayList<AppInfo> nAppList = new ArrayList<AppInfo>();

                for (AppInfo appInfo : items) {
                    if (constraint.toString().equals("noSystemNotUpdated")) {
                        if (!appInfo.isSystemPackageNotUpdated()) {
                            nAppList.add(appInfo);
                        }
                    }
                }

                results.values = nAppList;
                results.count = nAppList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                items.clear();
                items.addAll((ArrayList<AppInfo>) results.values);
                notifyDataSetChanged();
            }
        }

    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AppFilter();
        return filter;
    }

    public AppInfoAdapter(Context context, ArrayList<AppInfo> apps) {
        super(context, 0, apps);

        items = apps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AppInfo appInfo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_appinfo, parent, false);
        }

        // Lookup view for data population
        TextView appName = (TextView) convertView.findViewById(R.id.appName);
        TextView appVersion = (TextView) convertView.findViewById(R.id.appVersion);
        // Populate the data into the template view using the data object
        appName.setText(appInfo.getApplicationName());
        appVersion.setText(appInfo.getVersionName());
        // Return the completed view to render on screen
        return convertView;
    }
}