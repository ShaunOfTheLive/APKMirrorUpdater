package com.shaunofthelive.apkmirrorupdater;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.Comparator;

/**
 * Created by Shaun on 2014-12-07.
 */
public class AppInfo {
    public PackageInfo packageInfo;
    public String applicationName;
    public int minimumApi;

    public String getPackageName() {
        return packageInfo.packageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getVersionName() {
        return packageInfo.versionName;
    }

    public int getVersionCode() {
        return packageInfo.versionCode;
    }

    public static Comparator<AppInfo> nameComparator = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {
            return lhs.applicationName.compareToIgnoreCase(rhs.applicationName);
        }
    };

    public AppInfo() {
    }

    public AppInfo(PackageInfo packageInfo, String applicationName) {
        this(packageInfo, applicationName, 0);
    }

    public AppInfo(PackageInfo packageInfo, String applicationName, int minimumApi) {
        this.packageInfo = packageInfo;
        this.applicationName = applicationName;
        this.minimumApi = minimumApi;
    }

    public AppInfo(String packageName, String applicationName, String versionName) {
        this(packageName, applicationName, versionName, 0, 0);
    }

    public AppInfo(String packageName, String applicationName, String versionName, int versionCode) {
        this(packageName, applicationName, versionName, versionCode, 0);
    }

    public AppInfo(String packageName,
                   String applicationName,
                   String versionName,
                      int versionCode,
                      int minimumApi) {
        packageInfo = new PackageInfo();
        packageInfo.packageName = packageName;
        this.applicationName = applicationName;
        packageInfo.versionName = versionName;
        packageInfo.versionCode = versionCode;
        this.minimumApi = minimumApi;
    }

    public String toString() {
        return packageInfo.packageName + System.getProperty("line.separator")
             + applicationName + System.getProperty("line.separator")
             + packageInfo.versionName + System.getProperty("line.separator")
             + packageInfo.versionCode + System.getProperty("line.separator")
             + minimumApi;
    }

    /**
     * Return whether the given PackageInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param packageInfo
     * @return
     */
    public boolean isSystemPackage() {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }

    public boolean isSystemPackageNotUpdated() {
        return (((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                && (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0);
    }
}
