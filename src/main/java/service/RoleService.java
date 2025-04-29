package service;

import java.util.List;
import dao.RoleDao;
import model.Role;

public class RoleService {
    private RoleDao roleDao;

    public RoleService() {
        roleDao = new RoleDao();
    }

    // Thêm vai trò
    public boolean addRole(Role role) {
        return roleDao.addRole(role);
    }

    // Sửa thông tin vai trò
    public boolean updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    // Xóa vai trò
    public boolean deleteRole(int roleID) {
        return roleDao.deleteRole(roleID);
    }

    // Lấy danh sách vai trò
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }
}
