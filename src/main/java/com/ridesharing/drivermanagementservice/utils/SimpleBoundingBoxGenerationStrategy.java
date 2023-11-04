package com.ridesharing.drivermanagementservice.utils;

import org.springframework.stereotype.Component;

@Component
public class SimpleBoundingBoxGenerationStrategy implements BoundingBoxGenerationStrategy {

    public static final double BOUNDING_BOX = 0.009;

    @Override
    public BoundingBox generate(double latitude, double longitude, double radiusInKm) {
        BoundingBox boundingBox = new BoundingBox();
        boundingBox.setMinLatitude(latitude - BOUNDING_BOX * radiusInKm);
        boundingBox.setMaxLatitude(latitude + BOUNDING_BOX * radiusInKm);
        boundingBox.setMinLongitude(longitude - BOUNDING_BOX * radiusInKm);
        boundingBox.setMaxLongitude(longitude + BOUNDING_BOX * radiusInKm);

        return boundingBox;
    }
}
