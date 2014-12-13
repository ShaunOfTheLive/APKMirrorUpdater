package com.shaunofthelive.apkmirrorupdater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shaun on 2014-12-07.
 */
public class AppInfoAdapter extends ArrayAdapter<AppInfo> {
    ArrayList<AppInfo> items = null;
    boolean[] hidden = null;

    public AppInfoAdapter(Context context, ArrayList<AppInfo> apps) {
        super(context, 0, apps);

        items = apps;
        hidden = new boolean[apps.size()];
        for (int i = 0; i < apps.size(); i++) {
            hidden[i] = false;
        }
    }

    @Override
    public int getCount() {
        return (items.size() - getHiddenCount());
    }
    private int getHiddenCount() {
        int count = 0;
        for(int i=0;i<items.size();i++)
            if(hidden[i])
                count++;
        return count;
    }

    private int getRealPosition(int position) {
        int hElements = getHiddenCountUpTo(position);
        Log.d("grp", "hElements = " + String.valueOf(hElements));
        int diff = 0;
        for(int i=0;i<hElements;i++) {
            diff++;
            Log.d("grp", "diff = " + String.valueOf(diff));
            if(hidden[position+diff])
                i--;
        }
        return (position + diff);
    }
    private int getHiddenCountUpTo(int location) {
        int count = 0;
        for(int i=0;i<=location;i++) {
            if(hidden[i])
                count++;
        }
        return count;
    }

    public void hide(int position) {
        hidden[position] = true;
        notifyDataSetChanged();
    }
    public void unHide(int position) {
        hidden[position] = false;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        int position = getRealPosition(index);
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