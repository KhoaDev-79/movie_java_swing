package dao;

import database.SQLServerConnection;
import model.Invoice;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao {

    // Phương thức để lưu hóa đơn và danh sách vé vào CSDL
	//SaveInvoice
    public boolean saveInvoice(Invoice invoice, List<Ticket> tickets) {
        Connection connection = null;
        PreparedStatement invoiceStatement = null;
        PreparedStatement ticketStatement = null;
        boolean success = false;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                connection.setAutoCommit(false); // Bắt đầu transaction

                // Thêm hóa đơn vào CSDL
                String insertInvoiceQuery = "INSERT INTO Invoice (EmployeeID, CustomerID, InvoiceDate, TotalAmount) VALUES (?, ?, ?, ?)";
                invoiceStatement = connection.prepareStatement(insertInvoiceQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                invoiceStatement.setInt(1, invoice.getEmployeeID());
                invoiceStatement.setInt(2, invoice.getCustomerID());
                invoiceStatement.setTimestamp(3, invoice.getInvoiceDate());
                invoiceStatement.setDouble(4, invoice.getTotalAmount());

                int rowsInserted = invoiceStatement.executeUpdate();
                if (rowsInserted > 0) {
                    // Lấy ID của hóa đơn mới được thêm vào
                    int invoiceID;
                    try (ResultSet generatedKeys = invoiceStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            invoiceID = generatedKeys.getInt(1);

                            // Thêm danh sách vé vào CSDL
                            String insertTicketQuery = "INSERT INTO Ticket (ShowtimeID, SeatID, Price, BookingTime, InvoiceID) VALUES (?, ?, ?, ?, ?)";
                            ticketStatement = connection.prepareStatement(insertTicketQuery);
                            for (Ticket ticket : tickets) {
                                ticketStatement.setInt(1, ticket.getShowtimeID());
                                ticketStatement.setInt(2, ticket.getSeatID());
                                ticketStatement.setDouble(3, ticket.getPrice());
                                ticketStatement.setTimestamp(4, ticket.getBookingTime());
                                ticketStatement.setInt(5, invoiceID);
                                ticketStatement.addBatch(); // Thêm vào batch để thực hiện nhiều lệnh cùng lúc
                            }
                            ticketStatement.executeBatch(); // Thực hiện các lệnh trong batch
                        } else {
                            throw new SQLException("Creating invoice failed, no ID obtained.");
                        }
                    }

                    // Commit transaction
                    connection.commit();
                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction nếu có lỗi xảy ra
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Đặt lại auto-commit sau khi kết thúc transaction
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (invoiceStatement != null) {
                    invoiceStatement.close();
                }
                if (ticketStatement != null) {
                    ticketStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
 // Phương thức để lấy toàn bộ hóa đơn từ CSDL
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            String query = "SELECT * FROM Invoice";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int invoiceID = resultSet.getInt("InvoiceID");
                int employeeID = resultSet.getInt("EmployeeID");
                int customerID = resultSet.getInt("CustomerID");
                Timestamp invoiceDate = resultSet.getTimestamp("InvoiceDate");
                double totalAmount = resultSet.getDouble("TotalAmount");

                Invoice invoice = new Invoice(invoiceID, employeeID, customerID, invoiceDate, totalAmount);
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng ResultSet
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            // Đóng PreparedStatement
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            // Đóng Connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return invoices;
    }

    // Phương thức để lấy danh sách hóa đơn theo khoảng thời gian giữa hai ngày
    public List<Invoice> getInvoicesByDateRange(Timestamp startDate, Timestamp endDate) {
        List<Invoice> invoices = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            String query = "SELECT * FROM Invoice WHERE InvoiceDate BETWEEN ? AND ?";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, startDate);
            statement.setTimestamp(2, endDate);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int invoiceID = resultSet.getInt("InvoiceID");
                int employeeID = resultSet.getInt("EmployeeID");
                int customerID = resultSet.getInt("CustomerID");
                Timestamp invoiceDate = resultSet.getTimestamp("InvoiceDate");
                double totalAmount = resultSet.getDouble("TotalAmount");

                Invoice invoice = new Invoice(invoiceID, employeeID, customerID, invoiceDate, totalAmount);
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng ResultSet
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            // Đóng PreparedStatement
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            // Đóng Connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return invoices;
    }
}
