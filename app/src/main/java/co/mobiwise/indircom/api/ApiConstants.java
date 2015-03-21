package co.mobiwise.indircom.api;

/**
 * Created by mac on 13/03/15.
 */
public class ApiConstants {

    /**
     * Server domain
     */
    public static String BASE_URL = "http://www.akilliyazilim.org/indircom/";

    /**
     * Web app domain
     */
    public static String WEBSERVICE_URL = "indir.com/public/api/";

    /**
     * Api version
     */
    public static String VERSION = "v1";

    /**
     * Api key for access to api methods. My preciouss..
     */
    public static String SECRET_API_KEY = "x2AbXnciUE3lb8aHpMWnwpqT2EIuO8l1og5xsp1MXBhKG03aHK5jDVJxfoBj";

    /**
     * Webservice methods
     */
    public static String METHOD_REGISTER = "/register";
    public static String METHOD_UNRATED = "/unrated"; // eg. v1/{user_auth_id}/unrated.
    public static String METHOD_RATE = "/rate"; // eg. v1/{user_auth_id}/rate/{app_id}.

    /**
     * Like and dislike constants
     */
    public static int LIKE = 1;
    public static int DISLIKE = 0;

    /**
     * Post parameters
     */
    public static String NAME = "name";
    public static String SURNAME = "surname";
    public static String USER_AUTH_ID = "user_auth_id";
    public static String API_KEY = "api_key";
    public static String TOKEN = "token";
    public static String RATE = "rate";

    /**
     * Return parameters
     */
    public static String STATUS = "status";
    public static String ERROR = "error";
    public static String CODE = "code";
    public static String MESSAGE = "message";
    public static String ARRAY_NAME_APPS = "apps";
    public static String JSON_NAME_USER = "user";
    public static String APP_ID = "app_id";
    public static String APP_NAME = "app_name";
    public static String APP_DESCRIPTION = "app_description";
    public static String APP_IMAGE_URL = "app_image_url";
    public static String APP_DOWNLOAD_URL = "app_download_url";

}
