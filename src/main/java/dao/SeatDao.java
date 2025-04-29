package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.SQLServerConnection;
import model.Seat;

public class SeatDao {
    private Connection connection;

    public SeatDao() {
        connection = SQLServerConnection.getConnection();
    }

    // Thêm ghế ngồi vào CSDL
    public boolean addSeat(Seat seat) {
        String query = "INSERT INTO Seat (SeatNumber, TheatreID) VALUES (?, ?)";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, seat.getSeatNumber());
            statement.setInt(2, seat.getTheatreID());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin ghế ngồi trong CSDL
    public boolean updateSeat(Seat seat) {
        String query = "UPDATE Seat SET SeatNumber = ?, TheatreID = ? WHERE SeatID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, seat.getSeatNumber());
            statement.setInt(2, seat.getTheatreID());
            statement.setInt(3, seat.getSeatID());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Xóa ghế ngồi từ CSDL
    public boolean deleteSeat(int seatID) {
        String query = "DELETE FROM Seat WHERE SeatID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, seatID);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách ghế ngồi từ CSDL
    public List<Seat> getAllSeats() {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT * FROM Seat";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int seatID = resultSet.getInt("SeatID");
                String seatNumber = resultSet.getString("SeatNumber");
                int theatreID = resultSet.getInt("TheatreID");
                seats.add(new Seat(seatID, seatNumber, theatreID));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return seats;
    }
    
 // Lấy danh sách ghế theo ID phòng
    public List<Seat> getSeatsByTheatreID(int theatreID) {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT * FROM Seat WHERE TheatreID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, theatreID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int seatID = resultSet.getInt("SeatID");
                    String seatNumber = resultSet.getString("SeatNumber");
                    seats.add(new Seat(seatID, seatNumber, theatreID));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return seats;
    }
}
