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
            Collections.sort(appNames);
            for (String appName : appNames) {
                Log.d(TAG, appName);
            }

            Elements versionElements = doc.select(".infoSlide:not(.widget_appmanager_recentpostswidget *)");
            ArrayList<String> versionStrings = new ArrayList<String>();

            for (Element el : versionElements) {
                //Log.d(TAG, "el: " + el);
                versionStrings.add(el.text());
            }
            for (String versionString : versionStrings) {
                Log.d(TAG, versionString);
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
