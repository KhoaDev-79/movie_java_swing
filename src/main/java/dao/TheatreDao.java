package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.SQLServerConnection;
import model.Theatre;

public class TheatreDao {
    // Thêm phòng chiếu mới vào CSDL
    public boolean addTheatre(Theatre theatre) {
        String query = "INSERT INTO Theatre (TheatreName, Screen, SeatCapacity) VALUES (?, ?, ?)";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, theatre.getTheatreName());
            stmt.setString(2, theatre.getScreen());
            stmt.setInt(3, theatre.getSeatCapacity());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin phòng chiếu trong CSDL
    public boolean updateTheatre(Theatre theatre) {
        String query = "UPDATE Theatre SET TheatreName = ?, Screen = ?, SeatCapacity = ? WHERE TheatreID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, theatre.getTheatreName());
            stmt.setString(2, theatre.getScreen());
            stmt.setInt(3, theatre.getSeatCapacity());
            stmt.setInt(4, theatre.getTheatreID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phòng chiếu khỏi CSDL
    public boolean deleteTheatre(int theatreID) {
        String query = "DELETE FROM Theatre WHERE TheatreID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, theatreID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách phòng chiếu từ CSDL
    public List<Theatre> getAllTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        String query = "SELECT * FROM Theatre";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int theatreID = rs.getInt("TheatreID");
                String theatreName = rs.getString("TheatreName");
                String screen = rs.getString("Screen");
                int seatCapacity = rs.getInt("SeatCapacity");
                Theatre theatre = new Theatre(theatreID, theatreName, screen, seatCapacity);
                theatres.add(theatre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theatres;
    }
}
