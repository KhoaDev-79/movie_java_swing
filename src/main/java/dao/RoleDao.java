package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.SQLServerConnection;
import model.Role;

public class RoleDao {

    // Thêm vai trò vào CSDL
    public boolean addRole(Role role) {
        String query = "INSERT INTO Role (RoleName) VALUES (?)";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, role.getRoleName());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Sửa thông tin vai trò trong CSDL
    public boolean updateRole(Role role) {
        String query = "UPDATE Role SET RoleName = ? WHERE RoleID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, role.getRoleName());
            statement.setInt(2, role.getRoleID());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Xóa vai trò từ CSDL
    public boolean deleteRole(int roleID) {
        String query = "DELETE FROM Role WHERE RoleID = ?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, roleID);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách vai trò từ CSDL
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM Role";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int roleID = resultSet.getInt("RoleID");
                String roleName = resultSet.getString("RoleName");
                roles.add(new Role(roleID, roleName));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }
}
