package com.ridesharing.drivermanagementservice.utils;

public class CoordinatesUtils {

    private static final double EARTH_RADIUS_IN_KM = 6371;
    public static double calculateDistance(
            double srcLatitude, double srcLongitude,
            double destLatitude, double destLongitude) {

        srcLatitude = Math.toRadians(srcLatitude);
        srcLongitude = Math.toRadians(srcLongitude);

        destLatitude = Math.toRadians(destLatitude);
        destLongitude = Math.toRadians(destLongitude);

        // Haversine formula
        double dlon = destLongitude - srcLongitude;
        double dlat = destLatitude - srcLatitude;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(srcLatitude) * Math.cos(destLatitude)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // calculate the result
        return c * EARTH_RADIUS_IN_KM;
    }
}
