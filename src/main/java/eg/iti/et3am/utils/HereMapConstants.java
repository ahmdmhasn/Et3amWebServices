/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.iti.et3am.utils;

/**
 *
 * @author Wael M Elmahask
 */
public class HereMapConstants {

    /*
        https://route.api.here.com/routing/7.2/calculateroute.json?waypoint0=30.6210017,32.2688362
        &waypoint1=30.6175238,32.2658221&mode=fastest;car&traffic=enabled
        &app_id=yKkygBHyQ46ZJcEmOwf7&app_code=RoY6RYkL_tNcAMEAnRKkZQ
     */
    public static final String BASE_URL = "https://route.api.here.com/routing/7.2/calculateroute.json?";
    public static final String PONIT_ZERO = "waypoint0=";
    public static final String PONIT_ONE = "&waypoint1=";
    public static final String MODE = "&mode=fastest;car";
    public static final String TRAFIC = "&traffic=enabled";
    public static final String APP_ID = "&app_id=yKkygBHyQ46ZJcEmOwf7";
    public static final String APP_CODE = "&app_code=RoY6RYkL_tNcAMEAnRKkZQ";

    public static String url(double lat0, double log0, double lat1, double log1) {
        return BASE_URL
                + PONIT_ZERO + lat0 + "," + log0
                + PONIT_ONE + lat1 + "," + log1
                + MODE + TRAFIC + APP_ID + APP_CODE;
    }

}
