package project6;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CovidDao {
    private DataSource dataSource;

    public CovidDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Citizen saveCitizen(Citizen c) {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into citizens(c_name,c_zip,c_age,c_email,c_taj) values (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getPostcode());
            stmt.setInt(3, c.getAge());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getTAJnumber());
            stmt.executeUpdate();
            c.setId(saveCitizenStatement(stmt));
            return c;
        } catch (SQLException e) {
            throw new IllegalStateException("insert error", e);
        }
    }
    private long saveCitizenStatement(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new IllegalStateException("id error");
        }
    }

    public void saveCitizenList(List<Citizen> cl) {
        for (Citizen item : cl) {
            saveCitizen(item);
        }
    }

    public void updateCitizen(Citizen c, Vaccination vc) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement("UPDATE citizens SET `num_of_vacc`=?, `last_vacc`=? WHERE  id=?");
                 PreparedStatement stmt2 = conn.prepareStatement("insert into vaccinations(citizen_id,vacc_date,status,note,vacc_type) values (?,?,?,?,?)")) {
                stmt1.setInt(1, c.getVaccNumber());
                stmt1.setString(2, c.getVaccLast().toString());
                stmt1.setLong(3, c.getId());
                stmt1.executeUpdate();
                stmt2.setLong(1, vc.getCitizen_id());
                stmt2.setString(2, vc.getVaccination_date().toString());
                stmt2.setString(3, vc.getStatus());
                stmt2.setString(4, vc.getNote());
                stmt2.setString(5, vc.getVacc_type());
                stmt2.executeUpdate();
                conn.commit();
            }catch (SQLException e){
                conn.rollback();
                throw new IllegalStateException("update error", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("connection error", e);
        }
    }

    public List<Citizen> listGenerate(String post) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(
                             "SELECT * FROM citizens WHERE c_zip = ? AND (num_of_vacc = 0 OR num_of_vacc = 1) ORDER BY c_age DESC LIMIT 16")) {
            stmt.setString(1, post);
            return getCitizenStatement(stmt);
        } catch (SQLException e) {
            throw new IllegalStateException("select* error");
        }
    }

    public Citizen selectCitizenTaj(String taj) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(
                             "SELECT * FROM citizens WHERE c_taj = ?")) {
            stmt.setString(1, taj);
            List<Citizen> x = getCitizenStatement(stmt);
            if (x.size() == 1) {
                return x.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("select* error");
        }
    }
    public List<Citizen> selectCitizenZip(String zip) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(
                             "SELECT * FROM citizens WHERE c_zip = ?")) {
            stmt.setString(1, zip);
            List<Citizen> x = getCitizenStatement(stmt);
            return x;
        } catch (SQLException e) {
            throw new IllegalStateException("select* error");
        }
    }

    public Vaccination selectVaccination(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(
                             "SELECT * FROM vaccinations WHERE citizen_id = ?")) {
            stmt.setLong(1, id);
            List<Vaccination> x = getVaccStatement(stmt);
            if (x.size() == 1) {
                return x.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("select* error");
        }
    }

    private List<Citizen> getCitizenStatement(PreparedStatement stmt) throws SQLException {
        List<Citizen> result = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Citizen a = new Citizen(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getTimestamp(8)
                );
                result.add(a);
            }
        }
        return result;
    }
    private List<Vaccination> getVaccStatement(PreparedStatement stmt) throws SQLException {
        List<Vaccination> result = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vaccination v = new Vaccination(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getTimestamp(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
                result.add(v);
            }
        }
        return result;
    }

    public Map<String, String> mapPostcodes() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement("select * from cities")) {
            return getMapStatement(stmt);
        } catch (SQLException e) {
            throw new IllegalStateException("select map post error");
        }
    }

    private Map<String, String> getMapStatement(PreparedStatement stmt) throws SQLException {
        Map<String, String> result = new HashMap<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
        }
        return result;
    }
}