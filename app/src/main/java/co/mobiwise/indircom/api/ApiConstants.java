package co.mobiwise.indircom.api;

/**
 * Created by mac on 13/03/15.
 */
public class ApiConstants {

    /**
     * Server domain
     */
    public static String BASE_URL = "http://mobiwise.co";

    /**
     * Web app domain
     */
    public static String WEBSERVICE_URL = "/indir.com/public/api/";

    /**
     * Api version
     */
    public static String VERSION = "v1";

    /**
     * Api key for access to api methods
     */
    public static String API_KEY = "x2AbXnciUE3lb8aHpMWnwpqT2EIuO8l1og5xsp1MXBhKG03aHK5jDVJxfoBj";

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


}
