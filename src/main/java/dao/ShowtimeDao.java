package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import database.SQLServerConnection;
import model.Showtime;

public class ShowtimeDao {
    // Thêm xuất chiếu mới vào CSDL
    public boolean addShowtime(Showtime showtime) {
        String query = "INSERT INTO Showtime (MovieID, TheatreID, StartTime) VALUES (?, ?, ?)";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, showtime.getMovieID());
            stmt.setInt(2, showtime.getTheatreID());
            stmt.setTimestamp(3, showtime.getStartTime());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin xuất chiếu trong CSDL
    public boolean updateShowtime(Showtime showtime) {
        String query = "UPDATE Showtime SET MovieID = ?, TheatreID = ?, StartTime = ? WHERE ShowtimeID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, showtime.getMovieID());
            stmt.setInt(2, showtime.getTheatreID());
            stmt.setTimestamp(3, showtime.getStartTime());
            stmt.setInt(4, showtime.getShowtimeID());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa xuất chiếu khỏi CSDL
    public boolean deleteShowtime(int showtimeID) {
        String query = "DELETE FROM Showtime WHERE ShowtimeID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, showtimeID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách xuất chiếu từ CSDL
    public List<Showtime> getAllShowtimes() {
        List<Showtime> showtimes = new ArrayList<>();
        String query = "SELECT * FROM Showtime";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int showtimeID = rs.getInt("ShowtimeID");
                int movieID = rs.getInt("MovieID");
                int theatreID = rs.getInt("TheatreID");
                Timestamp startTime = rs.getTimestamp("StartTime");
                Showtime showtime = new Showtime(showtimeID, movieID, theatreID, startTime);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }
    
 // Lấy danh sách suất chiếu theo phim từ CSDL
    public List<Showtime> getShowtimesByMovie(int movieID) {
        List<Showtime> showtimes = new ArrayList<>();
        String query = "SELECT * FROM Showtime WHERE MovieID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, movieID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int showtimeID = rs.getInt("ShowtimeID");
                    int theatreID = rs.getInt("TheatreID");
                    Timestamp startTime = rs.getTimestamp("StartTime");
                    Showtime showtime = new Showtime(showtimeID, movieID, theatreID, startTime);
                    showtimes.add(showtime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }
}
