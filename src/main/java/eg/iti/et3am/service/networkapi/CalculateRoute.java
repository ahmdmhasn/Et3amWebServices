package eg.iti.et3am.service.networkapi;

import eg.iti.et3am.dto.RestaurantDTO;
import eg.iti.et3am.dto.Results;
import eg.iti.et3am.utils.HereMapConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public Results calculateRoute(Results list, double lat, double log) throws Exception {

        //casting Results Object to get List of RestaurantDTO
        List<RestaurantDTO> lists = (List<RestaurantDTO>) list.getResults();

        List<RestaurantDTO> listOfActualDistance = new ArrayList<>();

        // Create New Object To Get URL
        HttpHandler handler = new HttpHandler();

        // Making a request to url and getting response
        for (RestaurantDTO restaurants : lists) {
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

        Collections.sort(listOfActualDistance, new Comparator<RestaurantDTO>() {
            @Override
            public int compare(RestaurantDTO u1, RestaurantDTO u2) {
                return new Double(u1.getDistance()).compareTo(u2.getDistance());
            }
        });

        Results results = new Results();
        results.setPage(list.getPage());
        results.setTotalPages(list.getTotalResults());
        System.out.println("TotalPages -> ClacMethod " + results.getTotalPages());
        results.setTotalResults(list.getTotalResults());
        results.setResults(listOfActualDistance);
        return results;
    }

    public List<RestaurantDTO> calculateRoute(List<RestaurantDTO> restaurantList, double lat, double log) throws Exception {
        List<RestaurantDTO> listOfActualDistance = new ArrayList<>();
        HttpHandler handler = new HttpHandler();
        // Making a request to url and getting response
        for (RestaurantDTO restaurants : restaurantList) {
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

        Collections.sort(listOfActualDistance, new Comparator<RestaurantDTO>() {
            @Override
            public int compare(RestaurantDTO u1, RestaurantDTO u2) {
                return new Double(u1.getDistance()).compareTo(u2.getDistance());
            }
        });
        return listOfActualDistance;
    }
}
