package co.mobiwise.indircom.model;

/**
 * Created by mertsimsek on 20/03/15.
 */
public class App{

    private int app_id;
    private String app_name;
    private String app_description;
    private String app_image_url;
    private String app_download_url;
    private int user_vote;

    public App() {
    }

    public App(int app_id, String app_name, String app_description, String app_image_url, String app_download_url) {
        this.app_id = app_id;
        this.app_name = app_name;
        this.app_description = app_description;
        this.app_image_url = app_image_url;
        this.app_download_url = app_download_url;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_description() {
        return app_description;
    }

    public void setApp_description(String app_description) {
        this.app_description = app_description;
    }

    public String getApp_image_url() {
        return app_image_url;
    }

    public void setApp_image_url(String app_image_url) {
        this.app_image_url = app_image_url;
    }

    public String getApp_download_url() {
        return app_download_url;
    }

    public void setApp_download_url(String app_download_url) {
        this.app_download_url = app_download_url;
    }

    public int getUser_vote() {
        return user_vote;
    }

    public void setUser_vote(int user_vote) {
        this.user_vote = user_vote;
    }

    @Override
    public String toString() {
        return  "App id :" + app_id + "\n"+
                "App Name  :" + app_name + "\n"+
                "App Description :" + app_description + "\n"+
                "App Image URL :" + app_image_url + "\n"+
                "App Download URL:" + app_download_url + "\n";
    }
}
