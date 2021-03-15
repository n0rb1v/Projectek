package project4;

import java.util.ArrayList;
import java.util.List;

public class Activities {
    private List<Activity> activities;

    public Activities(List<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity a) {
        activities.add(a);
    }

    public List<Report> distancesByTypes() {
        double bikDist = 0;
        double hikDist = 0;
        double runDist = 0;
        double basDist = 0;

        for (Activity item : activities) {
            switch (item.getType()) {
                case BIKING:
                    bikDist += item.getDistance();
                    break;
                case HIKING:
                    hikDist += item.getDistance();
                    break;
                case RUNNING:
                    runDist += item.getDistance();
                    break;
                case BASKETBALL:
                    basDist += item.getDistance();
            }
        }
        List<Report> result = List.of(
                new Report(ActivityType.BIKING, bikDist),
                new Report(ActivityType.HIKING, hikDist),
                new Report(ActivityType.RUNNING, runDist),
                new Report(ActivityType.BASKETBALL, basDist));

        return result;
    }

    public int numberOfTrackActivities() {
        int sum = 0;
        for (Activity item : activities) {
            if (item instanceof ActivityWithTrack) {
                sum++;
            }
        }
        return sum;
    }

    public int numberOfWithoutTrackActivities() {
        int sum = 0;
        for (Activity item : activities) {
            if (item instanceof ActivityWithoutTrack) {
                sum++;
            }
        }
        return sum;
    }
}