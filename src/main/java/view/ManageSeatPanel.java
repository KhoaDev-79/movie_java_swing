package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import model.Seat;
import model.Theatre;
import service.SeatService;
import service.TheatreService;

public class ManageSeatPanel extends JPanel {
    private JTextField seatIDField;
    private JTextField seatNumberField;
    private JComboBox<String> theatreComboBox;
    private JTable seatTable;
    private DefaultTableModel tableModel;
    private SeatService seatService;
    private TheatreService theatreService;

    public ManageSeatPanel() {
        seatService = new SeatService();
        theatreService = new TheatreService();
        setLayout(new BorderLayout());

        // Tiêu đề ở phía bắc
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Quản lý ghế"));
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new BorderLayout());
        seatPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Ghế"));

        centerPanel.add(seatPanel, BorderLayout.NORTH);

        JPanel northOfSeatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addSeatButton = new JButton("Thêm ghế");
        JButton editSeatButton = new JButton("Sửa ghế");
        JButton deleteSeatButton = new JButton("Xóa ghế");

        northOfSeatPanel.add(addSeatButton);
        northOfSeatPanel.add(editSeatButton);
        northOfSeatPanel.add(deleteSeatButton);

        seatPanel.add(northOfSeatPanel, BorderLayout.SOUTH);

        JPanel infoSeatPanel = new JPanel();
        infoSeatPanel.setLayout(new GridLayout(3, 2, 20, 30));
        seatPanel.add(infoSeatPanel, BorderLayout.CENTER);

        JLabel lblSeatID = new JLabel("ID Ghế:");
        seatIDField = new JTextField();
        seatIDField.setEnabled(false);
        seatIDField.setDisabledTextColor(Color.BLACK);

        JLabel lblSeatNumber = new JLabel("Số Ghế:");
        seatNumberField = new JTextField();

        JLabel lblTheatre = new JLabel("Mã Phòng chiếu:");
        theatreComboBox = new JComboBox<>();

        infoSeatPanel.add(lblSeatID);
        infoSeatPanel.add(seatIDField);
        infoSeatPanel.add(lblSeatNumber);
        infoSeatPanel.add(seatNumberField);
        infoSeatPanel.add(lblTheatre);
        infoSeatPanel.add(theatreComboBox);

        // Bảng danh sách ghế
        JPanel seatListPanel = new JPanel(new BorderLayout());
        seatListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Ghế"));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Ghế");
        tableModel.addColumn("Số Ghế");
        tableModel.addColumn("Mã Phòng chiếu");

        seatTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(seatTable);

        seatListPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(seatListPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Hiển thị danh sách ghế khi khởi tạo
        displaySeats();

        // Hiển thị danh sách phòng chiếu trong combobox
        displayTheatreComboBox();

        // Sự kiện thêm ghế
        addSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSeat();
            }
        });

        // Sự kiện sửa ghế
        editSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSeat();
            }
        });

        // Sự kiện xóa ghế
        deleteSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSeat();
            }
        });

        // Sự kiện chọn dòng trong bảng ghế
        seatTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Kiểm tra xem đã chọn một dòng trong bảng chưa
                if (!e.getValueIsAdjusting() && seatTable.getSelectedRow() != -1) {
                    // Lấy thông tin từ dòng đã chọn
                    int selectedRow = seatTable.getSelectedRow();
                    int seatID = (int) seatTable.getValueAt(selectedRow, 0);
                    String seatNumber = (String) seatTable.getValueAt(selectedRow, 1);
                    int theatreID = (int) seatTable.getValueAt(selectedRow, 2);

                    // Đổ thông tin lên các trường nhập liệu
                    seatIDField.setText(String.valueOf(seatID));
                    seatNumberField.setText(seatNumber);
                    theatreComboBox.setSelectedItem(getTheatreName(theatreID));
                }
            }
        });
    }

    // Hiển thị danh sách ghế
    private void displaySeats() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy danh sách ghế từ CSDL
        List<Seat> seats = seatService.getAllSeats();

        // Hiển thị danh sách ghế trên bảng
        for (Seat seat : seats) {
            Object[] rowData = {seat.getSeatID(), seat.getSeatNumber(), seat.getTheatreID()};
            tableModel.addRow(rowData);
        }
    }

    // Hiển thị danh sách phòng chiếu trong combobox
    private void displayTheatreComboBox() {
        theatreComboBox.removeAllItems();

        // Lấy danh sách phòng chiếu từ CSDL
        List<Theatre> theatres = theatreService.getAllTheatres();

        // Hiển thị danh sách phòng chiếu trong combobox
        for (Theatre theatre : theatres) {
            theatreComboBox.addItem(theatre.getTheatreName());
        }
    }

    // Lấy ID của phòng chiếu từ tên phòng chiếu
    private int getTheatreID(String theatreName) {
        List<Theatre> theatres = theatreService.getAllTheatres();
        for (Theatre theatre : theatres) {
            if (theatre.getTheatreName().equals(theatreName)) {
                return theatre.getTheatreID();
            }
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    // Lấy tên của phòng chiếu từ ID phòng chiếu
    private String getTheatreName(int theatreID) {
        List<Theatre> theatres = theatreService.getAllTheatres();
        for (Theatre theatre : theatres) {
            if (theatre.getTheatreID() == theatreID) {
                return theatre.getTheatreName();
            }
        }
        return ""; // Trả về chuỗi rỗng nếu không tìm thấy
    }

    // Thêm ghế mới
    private void addSeat() {
        // Lấy thông tin từ các trường nhập liệu
        String seatNumber = seatNumberField.getText();
        String theatreName = (String) theatreComboBox.getSelectedItem();
        int theatreID = getTheatreID(theatreName);

        // Kiểm tra xem có trường nào trống không
        if (seatNumber.isEmpty() || theatreName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng ghế mới
        Seat seat = new Seat(seatNumber, theatreID);

        // Thêm ghế vào CSDL
        boolean success = seatService.addSeat(seat);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm ghế thành công");
            displaySeats(); // Hiển thị lại danh sách ghế sau khi thêm thành công
            clearFields(); // Xóa dữ liệu trong các trường nhập liệu
        } else {
            JOptionPane.showMessageDialog(this, "Thêm ghế thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Sửa thông tin ghế
    private void editSeat() {
        // Kiểm tra xem đã chọn một ghế từ bảng chưa
        int selectedRow = seatTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ghế để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy thông tin từ các trường nhập liệu
        int seatID = Integer.parseInt(seatIDField.getText());
        String seatNumber = seatNumberField.getText();
        String theatreName = (String) theatreComboBox.getSelectedItem();
        int theatreID = getTheatreID(theatreName);

        // Kiểm tra xem có trường nào trống không
        if (seatNumber.isEmpty() || theatreName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng ghế cần sửa
        Seat seat = new Seat(seatID, seatNumber, theatreID);

        // Sửa thông tin ghế trong CSDL
        boolean success = seatService.updateSeat(seat);

        if (success) {
            JOptionPane.showMessageDialog(this, "Sửa thông tin ghế thành công");
            displaySeats(); // Hiển thị lại danh sách ghế sau khi sửa thành công
            clearFields(); // Xóa dữ liệu trong các trường nhập liệu
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thông tin ghế thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa ghế
    private void deleteSeat() {
        // Kiểm tra xem đã chọn một ghế từ bảng chưa
        int selectedRow = seatTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ghế để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy ID ghế từ dòng đã chọn
        int seatID = Integer.parseInt(seatIDField.getText());

        // Xóa ghế trong CSDL
        boolean success = seatService.deleteSeat(seatID);

        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa ghế thành công");
            displaySeats(); // Hiển thị lại danh sách ghế sau khi xóa thành công
            clearFields(); // Xóa dữ liệu trong các trường nhập liệu
        } else {
            JOptionPane.showMessageDialog(this, "Xóa ghế thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xóa dữ liệu trong các trường nhập liệu
    private void clearFields() {
        seatIDField.setText("");
        seatNumberField.setText("");
        theatreComboBox.setSelectedIndex(0);
    }
}
