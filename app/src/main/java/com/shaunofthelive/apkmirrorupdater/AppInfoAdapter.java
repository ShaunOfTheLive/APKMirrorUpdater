package com.shaunofthelive.apkmirrorupdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shaun on 2014-12-07.
 */
public class AppInfoAdapter extends ArrayAdapter<AppInfo> {
    public AppInfoAdapter(Context context, ArrayList<AppInfo> users) {
        super(context, 0, users);
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
        appVersion.setText(appInfo.getVersion());
        // Return the completed view to render on screen
        return convertView;
    }
}