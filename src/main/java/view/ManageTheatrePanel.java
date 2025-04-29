package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import model.Theatre;
import service.TheatreService;

public class ManageTheatrePanel extends JPanel {
    private JTextField theatreIDField;
    private JTextField theatreNameField;
    private JComboBox<String> screenComboBox; // Sử dụng JComboBox thay cho screenField
    private JTextField seatCapacityField;
    private JTable theatreTable;
    private DefaultTableModel tableModel;
    private TheatreService theatreService;

    public ManageTheatrePanel() {
        theatreService = new TheatreService();
        setLayout(new BorderLayout());

        // Tiêu đề
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Quản lý phòng chiếu"));
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel theatrePanel = new JPanel(new BorderLayout());
        theatrePanel.setBorder(BorderFactory.createTitledBorder("Thông tin Phòng chiếu"));

        centerPanel.add(theatrePanel, BorderLayout.NORTH);

        // Các nút thao tác
        JPanel northOfTheatrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addTheatreButton = new JButton("Thêm phòng chiếu");
        JButton editTheatreButton = new JButton("Sửa phòng chiếu");
        JButton deleteTheatreButton = new JButton("Xóa phòng chiếu");

        northOfTheatrePanel.add(addTheatreButton);
        northOfTheatrePanel.add(editTheatreButton);
        northOfTheatrePanel.add(deleteTheatreButton);

        theatrePanel.add(northOfTheatrePanel, BorderLayout.SOUTH);

        // Panel chứa các trường nhập liệu
        JPanel infoTheatrePanel = new JPanel(new GridLayout(4, 2, 20, 30));
        theatrePanel.add(infoTheatrePanel, BorderLayout.CENTER);

        JLabel lblTheatreID = new JLabel("Mã Phòng chiếu:");
        theatreIDField = new JTextField();
        theatreIDField.setEnabled(false);
        theatreIDField.setDisabledTextColor(Color.BLACK);

        JLabel lblTheatreName = new JLabel("Tên Phòng chiếu:");
        theatreNameField = new JTextField();

        JLabel lblScreen = new JLabel("Loại phòng:");
        screenComboBox = new JComboBox<>(new String[] { "Standard", "VIP", "3D", "4DX" });

        JLabel lblSeatCapacity = new JLabel("Sức chứa:");
        seatCapacityField = new JTextField();

        infoTheatrePanel.add(lblTheatreID);
        infoTheatrePanel.add(theatreIDField);
        infoTheatrePanel.add(lblTheatreName);
        infoTheatrePanel.add(theatreNameField);
        infoTheatrePanel.add(lblScreen);
        infoTheatrePanel.add(screenComboBox);
        infoTheatrePanel.add(lblSeatCapacity);
        infoTheatrePanel.add(seatCapacityField);

        // Bảng danh sách phòng chiếu
        JPanel theatreListPanel = new JPanel(new BorderLayout());
        theatreListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Phòng chiếu"));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã Phòng chiếu");
        tableModel.addColumn("Tên Phòng chiếu");
        tableModel.addColumn("Loại phòng");
        tableModel.addColumn("Sức chứa");

        theatreTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(theatreTable);

        theatreListPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(theatreListPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Hiển thị danh sách
        displayTheatres();

        // Sự kiện thêm
        addTheatreButton.addActionListener(e -> addTheatre());

        // Sự kiện sửa
        editTheatreButton.addActionListener(e -> editTheatre());

        // Sự kiện xóa
        deleteTheatreButton.addActionListener(e -> deleteTheatre());

        // Sự kiện chọn dòng bảng
        theatreTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && theatreTable.getSelectedRow() != -1) {
                    int selectedRow = theatreTable.getSelectedRow();
                    int theatreID = (int) theatreTable.getValueAt(selectedRow, 0);
                    String theatreName = (String) theatreTable.getValueAt(selectedRow, 1);
                    String screen = (String) theatreTable.getValueAt(selectedRow, 2);
                    int seatCapacity = (int) theatreTable.getValueAt(selectedRow, 3);

                    theatreIDField.setText(String.valueOf(theatreID));
                    theatreNameField.setText(theatreName);
                    screenComboBox.setSelectedItem(screen);
                    seatCapacityField.setText(String.valueOf(seatCapacity));
                }
            }
        });
    }

    // Hiển thị danh sách
    private void displayTheatres() {
        tableModel.setRowCount(0);
        List<Theatre> theatres = theatreService.getAllTheatres();

        for (Theatre theatre : theatres) {
            Object[] rowData = {
                theatre.getTheatreID(),
                theatre.getTheatreName(),
                theatre.getScreen(),
                theatre.getSeatCapacity()
            };
            tableModel.addRow(rowData);
        }
    }

    // Thêm phòng chiếu
    private void addTheatre() {
        String theatreName = theatreNameField.getText();
        String screen = (String) screenComboBox.getSelectedItem();
        int seatCapacity = Integer.parseInt(seatCapacityField.getText());

        Theatre theatre = new Theatre(theatreName, screen, seatCapacity);

        boolean success = theatreService.addTheatre(theatre);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm phòng chiếu thành công");
            displayTheatres();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phòng chiếu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Sửa phòng chiếu
    private void editTheatre() {
        int selectedRow = theatreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng chiếu để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int theatreID = Integer.parseInt(theatreIDField.getText());
        String theatreName = theatreNameField.getText();
        String screen = (String) screenComboBox.getSelectedItem();
        int seatCapacity = Integer.parseInt(seatCapacityField.getText());

        Theatre theatre = new Theatre(theatreID, theatreName, screen, seatCapacity);

        boolean success = theatreService.updateTheatre(theatre);

        if (success) {
            JOptionPane.showMessageDialog(this, "Sửa thông tin phòng chiếu thành công");
            displayTheatres();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thông tin phòng chiếu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa phòng chiếu
    private void deleteTheatre() {
        int selectedRow = theatreTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng chiếu để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int theatreID = Integer.parseInt(theatreIDField.getText());

        boolean success = theatreService.deleteTheatre(theatreID);

        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa phòng chiếu thành công");
            displayTheatres();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa phòng chiếu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa dữ liệu các trường
    private void clearFields() {
        theatreIDField.setText("");
        theatreNameField.setText("");
        screenComboBox.setSelectedIndex(0);
        seatCapacityField.setText("");
    }
}
