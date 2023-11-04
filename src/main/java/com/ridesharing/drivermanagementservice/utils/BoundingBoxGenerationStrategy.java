package com.ridesharing.drivermanagementservice.utils;

public interface BoundingBoxGenerationStrategy {
    BoundingBox generate(double latitude, double longitude, double radiusInKm);
}
