package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import model.UserInfo;
import service.UserInfoService;
import service.RoleService; // Import RoleService
import java.util.Date;
import java.util.List;
import model.Role; // Import Role model

public class ManageUserPanel extends JPanel {
    private JTextField userIDField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JComboBox<Role> roleComboBox; // Change JComboBox<String> to JComboBox<Role>
    private JComboBox<String> genderComboBox; // Change to JComboBox<String> for gender
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JDateChooser birthdayChooser;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserInfoService userService;
    private RoleService roleService; // Add RoleService reference

    public ManageUserPanel() {
        userService = new UserInfoService();
        roleService = new RoleService(); // Initialize RoleService
        setLayout(new BorderLayout());

        // Tiêu đề
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Quản lý người dùng"));
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.setBorder(BorderFactory.createTitledBorder("Thông tin người dùng"));

        centerPanel.add(userPanel, BorderLayout.NORTH);

        JPanel northOfUserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addUserButton = new JButton("Thêm người dùng");
        editUserButton = new JButton("Sửa người dùng");
        deleteUserButton = new JButton("Xóa người dùng");

        northOfUserPanel.add(addUserButton);
        northOfUserPanel.add(editUserButton);
        northOfUserPanel.add(deleteUserButton);

        userPanel.add(northOfUserPanel, BorderLayout.SOUTH);

        JPanel infoUserPanel = new JPanel();
        infoUserPanel.setLayout(new GridLayout(3, 3, 20, 30));
        userPanel.add(infoUserPanel, BorderLayout.CENTER);

        JLabel lblUserID = new JLabel("Mã người dùng:");
        userIDField = new JTextField();
        userIDField.setEnabled(false);
        userIDField.setDisabledTextColor(Color.BLACK);

        JLabel lblUserName = new JLabel("Tên người dùng:");
        userNameField = new JTextField();

        JLabel lblPassword = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField();

        JLabel lblRole = new JLabel("Vai trò:");
        roleComboBox = new JComboBox<>(); // Change to JComboBox<Role>

        JLabel lblGender = new JLabel("Giới tính:");
        genderComboBox = new JComboBox<>(new String[] {"Nam", "Nữ", "Khác"}); // Gender options

        JLabel lblEmail = new JLabel("Email:");
        emailField = new JTextField();

        JLabel lblPhone = new JLabel("Số điện thoại:");
        phoneField = new JTextField();

        JLabel lblAddress = new JLabel("Địa chỉ:");
        addressField = new JTextField();

        JLabel lblBirthday = new JLabel("Ngày sinh:");
        birthdayChooser = new JDateChooser();

        infoUserPanel.add(lblUserID);
        infoUserPanel.add(userIDField);
        infoUserPanel.add(lblUserName);
        infoUserPanel.add(userNameField);
        infoUserPanel.add(lblPassword);
        infoUserPanel.add(passwordField);
        infoUserPanel.add(lblRole);
        infoUserPanel.add(roleComboBox); // Change to JComboBox<Role>
        infoUserPanel.add(lblGender);
        infoUserPanel.add(genderComboBox); // Add gender JComboBox
        infoUserPanel.add(lblEmail);
        infoUserPanel.add(emailField);
        infoUserPanel.add(lblPhone);
        infoUserPanel.add(phoneField);
        infoUserPanel.add(lblAddress);
        infoUserPanel.add(addressField);
        infoUserPanel.add(lblBirthday);
        infoUserPanel.add(birthdayChooser);

        // Bảng danh sách người dùng
        JPanel userListPanel = new JPanel(new BorderLayout());
        userListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách người dùng"));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã người dùng");
        tableModel.addColumn("Tên người dùng");
        tableModel.addColumn("Vai trò");
        tableModel.addColumn("Giới tính");
        tableModel.addColumn("Email");
        tableModel.addColumn("SĐT");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Ngày sinh");

        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        userListPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(userListPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Hiển thị danh sách người dùng khi khởi tạo
        displayUsers();

        // Load vai trò vào ComboBox
        loadRoles();

        // Sự kiện các nút
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editUser();
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        // Sự kiện chọn dòng trong bảng người dùng
        userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                    int selectedRow = userTable.getSelectedRow();
                    int userID = (int) userTable.getValueAt(selectedRow, 0);
                    String userName = (String) userTable.getValueAt(selectedRow, 1);
                    int roleID = (int) userTable.getValueAt(selectedRow, 2);
                    String gender = (String) userTable.getValueAt(selectedRow, 3);
                    String email = (String) userTable.getValueAt(selectedRow, 4);
                    String phone = (String) userTable.getValueAt(selectedRow, 5);
                    String address = (String) userTable.getValueAt(selectedRow, 6);
                    Date birthday = (Date) userTable.getValueAt(selectedRow, 7);

                    userIDField.setText(String.valueOf(userID));
                    userNameField.setText(userName);
                    // Set the selected role in the combobox
                    selectRole(roleID);
                    // Set the gender from the table to JComboBox
                    genderComboBox.setSelectedItem(gender);
                    emailField.setText(email);
                    phoneField.setText(phone);
                    addressField.setText(address);
                    birthdayChooser.setDate(birthday);
                }
            }
        });
    }

    private void selectRole(int roleID) {
        for (int i = 0; i < roleComboBox.getItemCount(); i++) {
            Role role = (Role) roleComboBox.getItemAt(i);
            if (role.getRoleID() == roleID) {
                roleComboBox.setSelectedIndex(i);
                return;
            }
        }
        // If role not found, select the first item in the combobox
        if (roleComboBox.getItemCount() > 0) {
            roleComboBox.setSelectedIndex(0);
        }
    }

    private void displayUsers() {
        tableModel.setRowCount(0);

        List<UserInfo> users = userService.getAllUsers();

        for (UserInfo user : users) {
            Object[] rowData = {user.getUserID(), user.getUserName(), user.getRoleID(), user.getGender(),
                    user.getEmail(), user.getPhone(), user.getAddress(), user.getBirthday()};
            tableModel.addRow(rowData);
        }
    }

    private void addUser() {
        // Get data from input fields
        String userName = userNameField.getText();
        String password = new String(passwordField.getPassword());
        Role role = (Role) roleComboBox.getSelectedItem(); // Get selected role from combobox
        String gender = (String) genderComboBox.getSelectedItem(); // Get gender from JComboBox
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        Date birthday = birthdayChooser.getDate();

        // Validate input
        if (userName.isEmpty() || password.isEmpty() || role == null || gender.isEmpty()
                || email.isEmpty() || phone.isEmpty() || address.isEmpty() || birthday == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new user object
        UserInfo user = new UserInfo(userName, password, role.getRoleID(), gender, email, phone, address, birthday);

        // Add user to database
        boolean success = userService.addUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm người dùng thành công");
            displayUsers();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm người dùng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userID = Integer.parseInt(userIDField.getText());
        String userName = userNameField.getText();
        String password = new String(passwordField.getPassword());
        Role role = (Role) roleComboBox.getSelectedItem(); // Get selected role from combobox
        String gender = (String) genderComboBox.getSelectedItem(); // Get gender from JComboBox
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        Date birthday = birthdayChooser.getDate();

        // Validate input
        if (userName.isEmpty() || role == null || gender.isEmpty()
                || email.isEmpty() || phone.isEmpty() || address.isEmpty() || birthday == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a user object
        UserInfo user = new UserInfo(userID, userName, password, role.getRoleID(), gender, email, phone, address, birthday);

        // Update user in database
        boolean success = userService.updateUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật người dùng thành công");
            displayUsers();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật người dùng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userID = Integer.parseInt(userIDField.getText());

        // Delete user from database
        boolean success = userService.deleteUser(userID);

        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa người dùng thành công");
            displayUsers();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa người dùng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        userIDField.setText("");
        userNameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
        genderComboBox.setSelectedIndex(0); // Clear gender selection
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        birthdayChooser.setDate(null);
    }

    // Load roles into the combobox
    private void loadRoles() {
        List<Role> roles = roleService.getAllRoles();
        for (Role role : roles) {
            roleComboBox.addItem(role);
        }
    }
}
