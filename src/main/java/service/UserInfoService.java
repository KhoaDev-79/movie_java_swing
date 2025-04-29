package service;

import java.util.Date;
import java.util.List;
import dao.UserInfoDao;
import model.UserInfo;

public class UserInfoService {
    private UserInfoDao userInfoDao;

    public UserInfoService() {
        userInfoDao = new UserInfoDao();
    }

    // Thêm người dùng
    public boolean addUser(UserInfo user) {
        return userInfoDao.addUser(user);
    }

    // Sửa thông tin người dùng
    public boolean updateUser(UserInfo user) {
        return userInfoDao.updateUser(user);
    }

    // Xóa người dùng
    public boolean deleteUser(int userID) {
        return userInfoDao.deleteUser(userID);
    }

    // Lấy danh sách người dùng
    public List<UserInfo> getAllUsers() {
        return userInfoDao.getAllUsers();
    }

    // Đăng nhập
    public UserInfo login(String username, String password) {
        return userInfoDao.login(username, password);
    }
    
    // lấy danh sách khách hàng
    public List<UserInfo> getCustomers() {
        return userInfoDao.getCustomers();
    }
}

