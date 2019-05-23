package eg.iti.et3am.service.networkapi;

import eg.iti.et3am.dao.implementions.RestaurantDaoImpl;
import eg.iti.et3am.dao.interfaces.RestaurantDao;

/**
 *
 * @author Wael M Elmahask
 */
public class CalculateRoute {

    //List<Restaurants> restaurantDao;
    RestaurantDao rd;
    RestaurantDao rds = new RestaurantDaoImpl();
//    public static void main(String[] args) throws java.lang.Exception {
//        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
//        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
//        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");
//    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1))
                    * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2))
                    * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    public CalculateRoute() throws Exception {
        this.rd = new RestaurantDaoImpl();
        rd.getRestaurantsList();
        //this.restaurantDao = new RestaurantDaoImpl().getRestaurantsList();
    }
}
