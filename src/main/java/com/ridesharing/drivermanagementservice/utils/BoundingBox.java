package com.ridesharing.drivermanagementservice.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoundingBox {
    private double minLatitude;
    private double minLongitude;
    private double maxLatitude;
    private double maxLongitude;
}
