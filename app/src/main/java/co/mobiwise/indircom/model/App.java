package co.mobiwise.indircom.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class App implements Serializable,Parcelable {

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

    public App(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        this.appId = Integer.parseInt(data[0]);
        this.appName = data[1];
        this.appDescription = data[2];
        this.appImageUrl = data[3];
        this.appDownloadUrl = data[4];
        this.userVote = Integer.parseInt(data[5]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(this.appId),
                                            this.appName,
                                            this.appDescription,
                                            this.appImageUrl,
                                            this.appDownloadUrl,
                                            String.valueOf(this.userVote)});
    }

    public static final Parcelable.Creator CREATOR = new ClassLoaderCreator() {
        @Override
        public Object createFromParcel(Parcel source, ClassLoader loader) {
            return new App(source);
        }

        @Override
        public Object createFromParcel(Parcel source) {
            return new App(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new  App[size];
        }
    };
}