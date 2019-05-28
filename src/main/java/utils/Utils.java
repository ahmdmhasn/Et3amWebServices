package utils;

/**
 *
 * @author Wael M Elmahask
 */
public class Utils {

    private final double el2;
    private final double el1;

    public Utils() {
        this.el2 = 0.0;
        this.el1 = 0.0;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @param el1
     * @param el2
     * @return
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
            double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * This routine calculates the distance between two points (given the
     * latitude/longitude of those points). It is being used to calculate the
     * distance between two locations using GeoDataSource (TM) prodducts
     *
     * Definitions: South latitudes are negative, east longitudes are positive
     *
     * Passed to function: lat1, lon1 = Latitude and Longitude of point 1 (in
     * decimal degrees) lat2, lon2 = Latitude and Longitude of point 2 (in
     * decimal degrees) unit = the unit you desire for results where: 'M' is
     * statute miles (default) 'K' is kilometers 'N' is nautical miles
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @param unit
     * @return
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if ("K".equals(unit)) {
                dist = dist * 1.609344;
            } else if ("N".equals(unit)) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
