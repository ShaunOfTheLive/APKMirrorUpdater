package com.shaunofthelive.apkmirrorupdater;

import java.util.Comparator;

/**
 * Created by Shaun on 2014-12-07.
 */
public class AppInfo {
    private String packageName;
    private String applicationName;
    private String versionName;
    private String versionCode;

    public String getPackageName() {
        return packageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public static Comparator<AppInfo> nameComparator;

    public AppInfo(String packageName, String applicationName, String versionName) {
        this(packageName, applicationName, versionName, null);
    }

    public AppInfo(String packageName, String applicationName, String versionName, String versionCode) {

        this.packageName = packageName;
        this.applicationName = applicationName;
        this.versionName = versionName;
        this.versionCode = versionCode;

        nameComparator = new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo lhs, AppInfo rhs) {
                return lhs.applicationName.compareToIgnoreCase(rhs.applicationName);
            }
        };
    }

    public String toString() {
        return packageName + System.getProperty("line.separator")
             + applicationName + System.getProperty("line.separator")
             + versionName;
    }
}
