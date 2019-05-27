package eg.iti.et3am.service.networkapi;

import eg.iti.et3am.model.Restaurants;
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
            String url = "https://route.api.here.com/routing/7.2/calculateroute.json?waypoint0="
                    + lat + "," + log + "&waypoint1=" + restaurants.getLatitude() + ","
                    + restaurants.getLongitude() + "&mode=fastest%3Bcar%3Btraffic%3Aenabled&app_id=yKkygBHyQ46ZJcEmOwf7&app_code=RoY6RYkL_tNcAMEAnRKkZQ&departure=now";
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
                        restaurants.setDistance(distance);
                        listOfActualDistance.add(restaurants);
                        System.out.println(distance);
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
