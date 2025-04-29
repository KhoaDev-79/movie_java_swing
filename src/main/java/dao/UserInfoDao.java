package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import database.SQLServerConnection;
import model.UserInfo;

public class UserInfoDao {
    private Connection connection;

    public UserInfoDao() {
        connection = SQLServerConnection.getConnection();
    }
    
    public UserInfo login(String username, String password) {
        String query = "SELECT * FROM UserInfo WHERE UserName = ? AND Password = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userID = resultSet.getInt("UserID");
                    String userName = resultSet.getString("UserName");
                    String userPassword = resultSet.getString("Password");
                    int roleID = resultSet.getInt("RoleID");
                    String gender = resultSet.getString("Gender");
                    String email = resultSet.getString("Email");
                    String phone = resultSet.getString("Phone");
                    String address = resultSet.getString("Address");
                    Date birthday = resultSet.getDate("Birthday");
                    return new UserInfo(userID, userName, userPassword, roleID, gender, email, phone, address, birthday);
                } else {
                    return null; // Không tìm thấy người dùng với tên người dùng và mật khẩu cung cấp
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Thêm người dùng vào CSDL
    public boolean addUser(UserInfo user) {
        String query = "INSERT INTO UserInfo (UserName, Password, RoleID, Gender, Email, Phone, Address, Birthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRoleID());
            statement.setString(4, user.getGender());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getAddress());
            statement.setDate(8, new java.sql.Date(user.getBirthday().getTime())); // Convert java.util.Date to java.sql.Date
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin người dùng trong CSDL
    public boolean updateUser(UserInfo user) {
        String query = "UPDATE UserInfo SET UserName = ?, Password = ?, RoleID = ?, Gender = ?, Email = ?, Phone = ?, Address = ?, Birthday = ? WHERE UserID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRoleID());
            statement.setString(4, user.getGender());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getAddress());
            statement.setDate(8, new java.sql.Date(user.getBirthday().getTime())); // Convert java.util.Date to java.sql.Date
            statement.setInt(9, user.getUserID());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Xóa người dùng từ CSDL
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM UserInfo WHERE UserID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userID);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách người dùng từ CSDL
    public List<UserInfo> getAllUsers() {
        List<UserInfo> users = new ArrayList<>();
        String query = "SELECT * FROM UserInfo";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int userID = resultSet.getInt("UserID");
                String userName = resultSet.getString("UserName");
                String password = resultSet.getString("Password");
                int roleID = resultSet.getInt("RoleID");
                String gender = resultSet.getString("Gender");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");
                String address = resultSet.getString("Address");
                Date birthday = resultSet.getDate("Birthday");
                users.add(new UserInfo(userID, userName, password, roleID, gender, email, phone, address, birthday));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
    
    public List<UserInfo> getCustomers() {
        List<UserInfo> customers = new ArrayList<>();
        String query = "SELECT * FROM UserInfo WHERE RoleID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, 3); // RoleID = 3 là khách hàng
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int userID = resultSet.getInt("UserID");
                    String userName = resultSet.getString("UserName");
                    String password = resultSet.getString("Password");
                    int roleID = resultSet.getInt("RoleID");
                    String gender = resultSet.getString("Gender");
                    String email = resultSet.getString("Email");
                    String phone = resultSet.getString("Phone");
                    String address = resultSet.getString("Address");
                    Date birthday = resultSet.getDate("Birthday");
                    customers.add(new UserInfo(userID, userName, password, roleID, gender, email, phone, address, birthday));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customers;
    }

}
