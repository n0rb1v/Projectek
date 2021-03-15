package project4;

public class Coordinate {
    private final double latitude; // -90,90
    private final double longitude; // -180,180

    public Coordinate(double latitude, double longitude) {
        if (latitude > 90 || latitude < -90) {
            throw new IllegalArgumentException("latitude error");
        }
        if (longitude > 180 || longitude < -180) {
            throw new IllegalArgumentException("longitude error");
        }
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}