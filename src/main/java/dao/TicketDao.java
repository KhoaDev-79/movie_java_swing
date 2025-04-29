package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.SQLServerConnection;
import model.Ticket;

public class TicketDao {
    // Lấy danh sách vé theo ID suất chiếu
    public List<Ticket> getTicketsByShowtimeID(int showtimeID) {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM Ticket WHERE ShowtimeID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, showtimeID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int ticketID = rs.getInt("TicketID");
                    int seatID = rs.getInt("SeatID");
                    double price = rs.getDouble("Price");
                    // Timestamp bookingTime = rs.getTimestamp("BookingTime");
                    // int invoiceID = rs.getInt("InvoiceID");
                    // Tạo đối tượng Ticket và thêm vào danh sách
                    Ticket ticket = new Ticket(ticketID, showtimeID, seatID, price, null, 0); // BookingTime và InvoiceID bị bỏ qua vì không cần thiết ở đây
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
