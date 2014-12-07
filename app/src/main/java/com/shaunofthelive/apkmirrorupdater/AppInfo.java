package com.shaunofthelive.apkmirrorupdater;

/**
 * Created by Shaun on 2014-12-07.
 */
public class AppInfo {
    private String packageName;
    private String applicationName;
    private String version;

    public String getPackageName() {
        return packageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getVersion() {
        return version;
    }

    public AppInfo(String packageName, String applicationName, String version) {

        this.packageName = packageName;
        this.applicationName = applicationName;
        this.version = version;
    }

    public String toString() {
        return packageName + System.getProperty("line.separator")
             + applicationName + System.getProperty("line.separator")
             + version;
    }
}
