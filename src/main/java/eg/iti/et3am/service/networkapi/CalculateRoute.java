package eg.iti.et3am.service.networkapi;

import eg.iti.et3am.model.Restaurants;
import eg.iti.et3am.utils.HereMapConstants;
import static eg.iti.et3am.utils.HereMapConstants.APP_CODE;
import static eg.iti.et3am.utils.HereMapConstants.APP_ID;
import static eg.iti.et3am.utils.HereMapConstants.MODE;
import static eg.iti.et3am.utils.HereMapConstants.TRAFIC;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wael M Elmahask
 */
@Service
public class CalculateRoute {

    public List<Restaurants> calculateRoute(List<Restaurants> restaurantList, double lat, double log) throws Exception {
        List<Restaurants> listOfActualDistance = new ArrayList<>();
        HttpHandler handler = new HttpHandler();
        // Making a request to url and getting response
        for (Restaurants restaurants : restaurantList) {
            String url = HereMapConstants.url(lat, log, restaurants.getLatitude(), restaurants.getLongitude());
            String jsonStr = handler.getURL(url);
            Logger.getLogger("Response from url: " + jsonStr);
            if (jsonStr != null) {
                JSONObject reader = new JSONObject(jsonStr);
                JSONObject response = reader.getJSONObject("response");
                JSONArray routeArray = response.getJSONArray("route");
                try {
                    for (int i = 0; i < routeArray.length(); i++) {
                        //get the JSON Object 
                        JSONObject obj = routeArray.getJSONObject(i);
                        JSONObject summary = obj.getJSONObject("summary");
                        double distance = summary.getDouble("distance");
                        double travelTime = summary.getDouble("travelTime");

                        double km = Math.round((distance / 1000) * 100.0) / 100.0;
                        restaurants.setDistance(km);

                        double min = Math.round((travelTime / 60));
                        restaurants.setTravelTime(min);

                        listOfActualDistance.add(restaurants);
                        System.out.println(km + " ,KM");
                        System.out.println(min + " , Min");
                    }
                } catch (final JSONException e) {
                    Logger.getLogger("Json parsing error: " + e.getMessage());
                }
            } else {
                System.out.println("Get URL Is Failed");
            }
        }
        return listOfActualDistance;
    }
}
