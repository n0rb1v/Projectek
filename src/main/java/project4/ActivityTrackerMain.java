package project4;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.*;
import java.time.LocalDateTime;

public class ActivityTrackerMain {
    public static void main(String[] args) {
        Activity act1 = new Activity(1, LocalDateTime.of(2012, 5, 22, 17, 55),
                "probablbla", ActivityType.BIKING);
        Activity act2 = new Activity(2, LocalDateTime.of(2015, 9, 21, 19, 50),
                "probabfdlbla", ActivityType.HIKING);
        Activity act3 = new Activity(3, LocalDateTime.of(2017, 6, 17, 14, 45),
                "probabaaalbla", ActivityType.BASKETBALL);
        MariaDbDataSource dataSource;

        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/activitytracker?useUnicode=true");
            dataSource.setUser("activitytracker");
            dataSource.setPassword("activitytracker");
        } catch (SQLException e) {
            throw new IllegalStateException("sql database error", e);
        }

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        ActivityDao ad = new ActivityDao(dataSource);
        ad.saveActivity(act1);
        ad.saveActivity(act2);
        ad.saveActivity(act3);


    }


}