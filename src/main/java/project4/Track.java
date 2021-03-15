package project4;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private List<TrackPoint> trackPoints = new ArrayList<>();

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoints.add(trackPoint);
    }

    public Coordinate findMaximumCoordinate() {
        double maxLat = 0;
        double maxLong = 0;
        for (TrackPoint item : trackPoints) {
            if (maxLat < item.getCoordinate().getLatitude()) {
                maxLat = item.getCoordinate().getLatitude();
            }
        }
        for (TrackPoint item : trackPoints) {
            if (maxLong < item.getCoordinate().getLongitude()) {
                maxLong = item.getCoordinate().getLongitude();
            }
        }
        return new Coordinate(maxLat, maxLong);
    }

    public Coordinate findMinimumCoordinate() {
        double minLat = 0;
        double minLong = 0;
        for (TrackPoint item : trackPoints) {
            if (minLat > item.getCoordinate().getLatitude()) {
                minLat = item.getCoordinate().getLatitude();
            }
        }
        for (TrackPoint item : trackPoints) {
            if (minLong > item.getCoordinate().getLongitude()) {
                minLong = item.getCoordinate().getLongitude();
            }
        }
        return new Coordinate(minLat, minLong);
    }

    public double getDistance() {
        double result = 0;
        for (int i = 1; i < trackPoints.size(); i++) {
            result += trackPoints.get(i - 1).getDistanceFrom(trackPoints.get(i));
        }
        return result;
    }

    public double getFullDecrease() {
        double result = 0;
        for (int i = 1; i < trackPoints.size(); i++) {
            if (trackPoints.get(i).getElevation() - trackPoints.get(i - 1).getElevation() < 0) {
                result += trackPoints.get(i - 1).getElevation() - trackPoints.get(i).getElevation();
            }
        }
        return result;
    }

    public double getFullElevation() {
        double result = 0;
        for (int i = 1; i < trackPoints.size(); i++) {
            if (trackPoints.get(i).getElevation() - trackPoints.get(i - 1).getElevation() > 0) {
                result += trackPoints.get(i).getElevation() - trackPoints.get(i - 1).getElevation();
            }
        }
        return result;
    }

    public double getRectangleArea() {
        double lat = 0;
        double lng = 0;
        lat = findMaximumCoordinate().getLatitude() - findMinimumCoordinate().getLatitude();
        lng = findMaximumCoordinate().getLongitude() - findMinimumCoordinate().getLongitude();
        return lat * lng;
    }


    public List<TrackPoint> getTrackPoints() {
        return trackPoints;
    }
}