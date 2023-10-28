# RideSharingApp - Driver Management Service

This service is responsible for managing all the functionalities related to the Drivers.

It exposes APIs for the following functionalities:

1. [**Profile Management**](api_docs/profile_management.yaml)
   1. *View Profile*
   2. *Update Profile*
   3. ~~*Upload Driving License*~~
   4. ~~*Vehicle Onboarding*~~
   5. ~~*Vehicle Details*~~
   6. ~~*Vehicle Update*~~
   7. ~~*Vehicle Delete*~~
2. [**Driver Status Management**](api_docs/driver_status_management.yaml)
   1. Update Availability
   2. Update Location
   3. Get Location of a Driver
3. [**Ride Assignment**](api_docs/ride_assignment.yaml)
   1. *Ride notify*
   2. *Ride acceptance*
4. [**Ride Status**](api_docs/ride_status_management.yaml)
   1. *View Active Ride Details*
   2. *Start Active Ride*
   3. *End Active Ride*
   4. *Cancel Active Ride*
5. [**Ride History**](api_docs/ride_history.yaml)
   1. *View Ride History*
6. [**Driver Earnings**](api_docs/driver_earnings.yaml)
   1. *View Earnings*
   2. *Update Earnings*

The API documentation is under folder `api_docs` and follows *OpenAPI 3.0 specification*.

## References

OpenAPI Links:
- [OpenAPI Specification - GitHub](https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.0.3.md)
- [OpenAPI - Swagger](https://swagger.io/specification/)