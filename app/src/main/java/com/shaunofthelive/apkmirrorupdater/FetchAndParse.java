package com.shaunofthelive.apkmirrorupdater;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Shaun on 2014-12-08.
 */
public class FetchAndParse extends AsyncTask<String, Void, String> {
    private static final String TAG = "MY_TAG";

    protected String doInBackground(String... urls) {
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
                //Log.d(TAG, "el: " + el);
                versionStrings.add(el.text());
            }

            Pattern versionPattern = Pattern.compile(".*Latest version: (.*) \\((.*)\\) for Android (.*\\+) \\(.*API ([0-9]+)\\).*");
            String versionName;
            int versionCode;
            int minimumApi;

            ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();

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

                appInfos.add(new AppInfo("", appNames.get(i), versionName, versionCode, minimumApi));
                i++;
            }

            for (AppInfo appInfo : appInfos) {
                Log.d(TAG, appInfo.toString());
            }

        } catch(IOException ie) {
            Log.d(TAG, "EXCEPTION: IOException");
        }

        return "test";
    }

    protected void onProgressUpdate(Integer... progress) {
        //
    }

    protected void onPreExecute() {
        Log.d(TAG, "preExecute");
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, "postExecute");
    }
}
