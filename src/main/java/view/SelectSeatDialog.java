package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Seat;
import model.Ticket;

public class SelectSeatDialog extends JDialog {
    private JPanel seatPanel;
    private Seat selectedSeat; // Thêm thuộc tính cho ghế đã chọn
    private static final int BUTTON_SIZE = 100; // Kích thước của nút ghế

    public SelectSeatDialog(Frame owner, List<Seat> seats, List<Ticket> bookedTickets) {
        super(owner, "Chọn Ghế", true);
        initComponents(seats, bookedTickets);
    }

    private void initComponents(List<Seat> allSeats, List<Ticket> bookedTickets) {
        seatPanel = new JPanel(new GridBagLayout()); // Sử dụng GridBagLayout thay vì GridLayout

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10); // Đặt khoảng cách giữa các thành phần

        // Tạo nút cho mỗi ghế
        for (Seat seat : allSeats) {
            JButton seatButton = new JButton(seat.getSeatNumber()); // Sử dụng seatNumber thay vì SeatID
            seatButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE)); // Đặt kích thước cố định cho nút ghế

            // Xác định màu sắc dựa trên trạng thái của ghế
            if (isSeatBooked(seat, bookedTickets)) {
                seatButton.setBackground(Color.RED);
            } else {
                seatButton.setBackground(UIManager.getColor("Button.background"));
            }

            // Xử lý sự kiện khi người dùng nhấn vào một ghế
            seatButton.addActionListener(e -> {
                if (isSeatBooked(seat, bookedTickets)) {
                    JOptionPane.showMessageDialog(SelectSeatDialog.this, "Ghế đã được đặt.");
                } else {
                    // Trả về thông tin ghế đã chọn
                    dispose();
                    selectedSeat = seat; // Lưu ghế đã chọn
                }
            });

            // Thêm nút vào panel với các ràng buộc đã thiết lập
            seatPanel.add(seatButton, constraints);

            // Cập nhật ràng buộc cho ghế tiếp theo
            constraints.gridx++; // Di chuyển sang cột tiếp theo
            if (constraints.gridx == 4) { // Nếu đã đến cột thứ 4, chuyển sang hàng mới
                constraints.gridx = 0;
                constraints.gridy++;
            }
        }

        JScrollPane scrollPane = new JScrollPane(seatPanel);
        getContentPane().add(scrollPane);
        pack();

        // Cài đặt kích thước cố định cho hộp thoại
        int dialogWidth = 600; // Chiều rộng của hộp thoại
        int dialogHeight = 600; // Chiều cao của hộp thoại
        setSize(dialogWidth, dialogHeight);

        setLocationRelativeTo(null);
    }

    // Kiểm tra xem ghế đã được đặt hay chưa
    private boolean isSeatBooked(Seat seat, List<Ticket> bookedTickets) {
        for (Ticket ticket : bookedTickets) {
            if (ticket.getSeatID() == seat.getSeatID()) {
                return true; // Ghế đã được đặt
            }
        }
        return false; // Ghế chưa được đặt
    }

    // Phương thức để trả về ghế đã chọn
    public Seat getSelectedSeat() {
        return selectedSeat;
    }
}
