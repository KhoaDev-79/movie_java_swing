package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import model.UserInfo;
import service.UserInfoService;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

public class ManageCustomerPanel extends JPanel {
    private JTextField customerIDField;
    private JTextField customerNameField;
    private JComboBox<String> genderComboBox;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JDateChooser birthdayChooser;
    private JButton addCustomerButton;
    private JButton editCustomerButton;
    private JButton deleteCustomerButton;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private UserInfoService userInfoService;

    public ManageCustomerPanel() {
        userInfoService = new UserInfoService();
        setLayout(new BorderLayout());

        // Tiêu đề
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Quản lý khách hàng"));
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BorderLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        centerPanel.add(customerPanel, BorderLayout.NORTH);

        JPanel northOfCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addCustomerButton = new JButton("Thêm khách hàng");
        editCustomerButton = new JButton("Sửa khách hàng");
        deleteCustomerButton = new JButton("Xóa khách hàng");

        northOfCustomerPanel.add(addCustomerButton);
        northOfCustomerPanel.add(editCustomerButton);
        northOfCustomerPanel.add(deleteCustomerButton);

        customerPanel.add(northOfCustomerPanel, BorderLayout.SOUTH);

        JPanel infoCustomerPanel = new JPanel();
        infoCustomerPanel.setLayout(new GridLayout(7, 2, 20, 20));
        customerPanel.add(infoCustomerPanel, BorderLayout.CENTER);

        JLabel lblCustomerID = new JLabel("Mã khách hàng:");
        customerIDField = new JTextField();
        customerIDField.setEnabled(false);
        customerIDField.setDisabledTextColor(Color.BLACK);

        JLabel lblCustomerName = new JLabel("Tên khách hàng:");
        customerNameField = new JTextField();

        JLabel lblGender = new JLabel("Giới tính:");
        genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});

        JLabel lblEmail = new JLabel("Email:");
        emailField = new JTextField();

        JLabel lblPhone = new JLabel("Số điện thoại:");
        phoneField = new JTextField();

        JLabel lblAddress = new JLabel("Địa chỉ:");
        addressField = new JTextField();

        JLabel lblBirthday = new JLabel("Ngày sinh:");
        birthdayChooser = new JDateChooser();

        infoCustomerPanel.add(lblCustomerID);
        infoCustomerPanel.add(customerIDField);
        infoCustomerPanel.add(lblCustomerName);
        infoCustomerPanel.add(customerNameField);
        infoCustomerPanel.add(lblGender);
        infoCustomerPanel.add(genderComboBox);
        infoCustomerPanel.add(lblEmail);
        infoCustomerPanel.add(emailField);
        infoCustomerPanel.add(lblPhone);
        infoCustomerPanel.add(phoneField);
        infoCustomerPanel.add(lblAddress);
        infoCustomerPanel.add(addressField);
        infoCustomerPanel.add(lblBirthday);
        infoCustomerPanel.add(birthdayChooser);

        // Bảng danh sách khách hàng
        JPanel customerListPanel = new JPanel(new BorderLayout());
        customerListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã KH");
        tableModel.addColumn("Tên KH");
        tableModel.addColumn("Giới tính");
        tableModel.addColumn("Email");
        tableModel.addColumn("SĐT");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Ngày sinh");

        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);

        customerListPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(customerListPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Hiển thị danh sách khách hàng khi khởi tạo
        displayCustomers();

        // Sự kiện các nút
        addCustomerButton.addActionListener(e -> addCustomer());
        editCustomerButton.addActionListener(e -> editCustomer());
        deleteCustomerButton.addActionListener(e -> deleteCustomer());

        // Sự kiện chọn dòng trong bảng
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && customerTable.getSelectedRow() != -1) {
                    int selectedRow = customerTable.getSelectedRow();
                    int customerID = (int) customerTable.getValueAt(selectedRow, 0);
                    String customerName = (String) customerTable.getValueAt(selectedRow, 1);
                    String gender = (String) customerTable.getValueAt(selectedRow, 2);
                    String email = (String) customerTable.getValueAt(selectedRow, 3);
                    String phone = (String) customerTable.getValueAt(selectedRow, 4);
                    String address = (String) customerTable.getValueAt(selectedRow, 5);
                    Date birthday = (Date) customerTable.getValueAt(selectedRow, 6);

                    customerIDField.setText(String.valueOf(customerID));
                    customerNameField.setText(customerName);
                    genderComboBox.setSelectedItem(gender);
                    emailField.setText(email);
                    phoneField.setText(phone);
                    addressField.setText(address);
                    birthdayChooser.setDate(birthday);
                }
            }
        });
    }

    private void displayCustomers() {
        tableModel.setRowCount(0);
        List<UserInfo> customers = userInfoService.getCustomers();
        for (UserInfo customer : customers) {
            Object[] rowData = {
                customer.getUserID(),
                customer.getUserName(),
                customer.getGender(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getBirthday()
            };
            tableModel.addRow(rowData);
        }
    }

    private void addCustomer() {
        String customerName = customerNameField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        Date birthday = birthdayChooser.getDate();

        if (customerName.isEmpty() || gender.isEmpty() || email.isEmpty()
                || phone.isEmpty() || address.isEmpty() || birthday == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserInfo customer = new UserInfo(customerName, "123", 3, gender, email, phone, address, birthday);
        boolean success = userInfoService.addUser(customer);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
            displayCustomers();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int customerID = Integer.parseInt(customerIDField.getText());
        String customerName = customerNameField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        Date birthday = birthdayChooser.getDate();

        if (customerName.isEmpty() || gender.isEmpty() || email.isEmpty()
                || phone.isEmpty() || address.isEmpty() || birthday == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserInfo customer = new UserInfo(customerID, customerName, "123", 3, gender, email, phone, address, birthday);
        boolean success = userInfoService.updateUser(customer);

        if (success) {
            JOptionPane.showMessageDialog(this, "Sửa thông tin khách hàng thành công");
            displayCustomers();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thông tin khách hàng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int customerID = Integer.parseInt(customerIDField.getText());
        boolean success = userInfoService.deleteUser(customerID);

        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công");
            displayCustomers();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        customerIDField.setText("");
        customerNameField.setText("");
        genderComboBox.setSelectedIndex(0);
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        birthdayChooser.setDate(null);
    }
}
