package co.mobiwise.indircom.model;

import java.io.Serializable;

public class App implements Serializable {

    private int appId;
    private String appName;
    private String appDescription;
    private String appImageUrl;
    private String appDownloadUrl;
    private int userVote;

    public App() {
    }

    public App(int appId, String appName, String appDescription, String appImageUrl, String appDownloadUrl) {
        this.appId = appId;
        this.appName = appName;
        this.appDescription = appDescription;
        this.appImageUrl = appImageUrl;
        this.appDownloadUrl = appDownloadUrl;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppImageUrl() {
        return appImageUrl;
    }

    public void setAppImageUrl(String appImageUrl) {
        this.appImageUrl = appImageUrl;
    }

    public String getAppDownloadUrl() {
        return appDownloadUrl;
    }

    public void setAppDownloadUrl(String appDownloadUrl) {
        this.appDownloadUrl = appDownloadUrl;
    }

    public int getUserVote() {
        return userVote;
    }

    public void setUserVote(int userVote) {
        this.userVote = userVote;
    }

    @Override
    public String toString() {
        return "App id :" + appId + "\n" +
                "App Name  :" + appName + "\n" +
                "App Description :" + appDescription + "\n" +
                "App Image URL :" + appImageUrl + "\n" +
                "App Download URL:" + appDownloadUrl + "\n";
    }
}