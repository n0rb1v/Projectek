package project4;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao {
    private DataSource dataSource;

    public ActivityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Activity saveActivity(Activity a) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement("insert into activitytracker(start_time,activity_desc,activity_type) values (?,?,?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(a.getStartTime()));
            stmt.setString(2, a.getDesc());
            stmt.setString(3, a.getType().toString());
            stmt.executeUpdate();
            insertActTackPoints(a.getTrackPoints(), a.setId(getKey(stmt)));
            return a;
        } catch (SQLException e) {
            throw new IllegalStateException("insert error", e);
        }
    }

    private void insertActTackPoints(List<TrackPoint> t, long actId) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement("insert into trackpoints(act_time,lat,lon,activityId) values (?,?,?,?)")) {
                for (TrackPoint item : t) {
                    if (!isValidLatlon(item)){
                        throw new IllegalArgumentException("invalid lat/lon");
                    }
                    stmt.setDate(1, Date.valueOf(item.getTime()));
                    stmt.setDouble(2,item.getLat());
                    stmt.setDouble(3,item.getLon());
                    stmt.setLong(4,actId);
                    stmt.executeUpdate();
                }
                conn.commit();
            }catch (IllegalArgumentException ae){
                conn.rollback();
            }

        } catch (SQLException e) {
            throw new IllegalStateException("insert hiba", e);
        }

    }

    private boolean isValidLatlon(TrackPoint t) {
        if (t.getLat()>90 || t.getLat()<-90){
            return false;
        }
        if (t.getLon()>180 || t.getLon()<-180){
            return false;
        }
        return true;
    }

    private long getKey(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new IllegalStateException("id error");
        }
    }

    public Activity findActivityById(long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt1 =
                     conn.prepareStatement("select * from activitytracker where id =?");
             PreparedStatement stmt2 =
                     conn.prepareStatement("select * from trackpoints where activityId =?")){
            stmt1.setLong(1, id);
            List<Activity> x = getStatementAct(stmt1);
            if (x.size() == 1) {
                stmt2.setLong(1,x.get(0).getId());
                x.get(0).setTrackPoints(getStatementTrk(stmt2));
                return x.get(0);
            }
            throw new IllegalStateException("not found");
        } catch (SQLException e) {
            throw new IllegalStateException("select error", e);
        }
    }

    public List<Activity> findActivityByType(ActivityType type) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement("select * from activitytracker where activity_type=?")) {
            stmt.setString(1, type.toString());
            List<Activity> x = getStatementAct(stmt);
            return x;
        } catch (SQLException e) {
            throw new IllegalStateException("select error", e);
        }
    }

    public List<Activity> listActivities() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement("select * from activitytracker")) {
            return getStatementAct(stmt);
        } catch (SQLException e) {
            throw new IllegalStateException("select* error");
        }
    }

    public List<Activity> selectActivityBeforeDate(LocalDate date) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement("select * from activitytracker where start_time <?")) {
            stmt.setString(1, date.toString());
            return getStatementAct(stmt);
        } catch (SQLException e) {
            throw new IllegalStateException("select* error");
        }
    }

    private List<Activity> getStatementAct(PreparedStatement stmt) throws SQLException {
        List<Activity> result = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Activity a = new Activity(
                        rs.getLong(1),
                        rs.getTimestamp(2).toLocalDateTime(),
                        rs.getString(3),
                        ActivityType.valueOf(rs.getString(4).toUpperCase()));
                result.add(a);
            }
        }
        return result;
    }

    private List<TrackPoint> getStatementTrk(PreparedStatement stmt) throws SQLException {
        List<TrackPoint> result = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TrackPoint t = new TrackPoint(
                        rs.getLong(1),
                        rs.getDate(2).toLocalDate(),
                        rs.getDouble(3),
                        rs.getDouble(4));
                result.add(t);
            }
        }
        return result;
    }
}