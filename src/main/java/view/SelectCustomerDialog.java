package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.UserInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectCustomerDialog extends JDialog {
    private JTextField searchField;
    private JTable customerTable;
    private DefaultTableModel tableModel;

    private List<UserInfo> customers;
    private UserInfo selectedCustomer;

    public SelectCustomerDialog(Frame parent, List<UserInfo> customers) {
        super(parent, "Chọn khách hàng", true);
        this.customers = customers;

        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Tìm");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        contentPanel.add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Tên", "Email", "Điện thoại"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton selectButton = new JButton("Chọn");
        JButton cancelButton = new JButton("Hủy");
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCustomers();
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = customerTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedCustomer = customers.get(selectedRow);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SelectCustomerDialog.this, "Vui lòng chọn một khách hàng.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCustomer = null;
                dispose();
            }
        });

        populateCustomerTable(customers);

        add(contentPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    private void populateCustomerTable(List<UserInfo> customers) {
        tableModel.setRowCount(0);
        for (UserInfo customer : customers) {
            Object[] rowData = {customer.getUserID(), customer.getUserName(), customer.getEmail(), customer.getPhone()};
            tableModel.addRow(rowData);
        }
    }

    private void searchCustomers() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            populateCustomerTable(customers);
        } else {
            DefaultTableModel filteredModel = new DefaultTableModel(new String[]{"ID", "Tên", "Email", "Điện thoại"}, 0);
            for (UserInfo customer : customers) {
                if (customer.getUserName().toLowerCase().contains(searchText)
                        || customer.getEmail().toLowerCase().contains(searchText)
                        || customer.getPhone().toLowerCase().contains(searchText)) {
                    Object[] rowData = {customer.getUserID(), customer.getUserName(), customer.getEmail(), customer.getPhone()};
                    filteredModel.addRow(rowData);
                }
            }
            customerTable.setModel(filteredModel);
        }
    }

    public UserInfo getSelectedCustomer() {
        return selectedCustomer;
    }
}

