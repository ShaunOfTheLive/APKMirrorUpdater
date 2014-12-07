package com.shaunofthelive.apkmirrorupdater;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MY_TAG";

    /**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param packageInfo
     * @return
     */
    private boolean isSystemPackage(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }

    private ArrayList<AppInfo> getAllAppInfo() {
        final PackageManager pm = getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        ArrayList<AppInfo> apps = new ArrayList<AppInfo>();

        for (PackageInfo packageInfo : packages) {
            ApplicationInfo ai;
            try {
                ai = pm.getApplicationInfo(packageInfo.packageName, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }
            final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");

            if (!isSystemPackage(packageInfo)) {
                apps.add(new AppInfo(packageInfo.packageName, applicationName, packageInfo.versionName));
            }

            Collections.sort(apps, AppInfo.nameComparator);
        }

        return apps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<AppInfo> apps = getAllAppInfo();
        for (AppInfo appInfo: apps) {
            // log the info
            Log.d(TAG, "Application name: " + appInfo.getApplicationName());
            Log.d(TAG, "Version: " + appInfo.getVersion());
        }

/*        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, apps);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);*/

        // Create the adapter to convert the array to views
        AppInfoAdapter adapter = new AppInfoAdapter(this, apps);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvApps);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
