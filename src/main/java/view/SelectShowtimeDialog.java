package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Showtime;

public class SelectShowtimeDialog extends JDialog {
    private JTable tblShowtimes;
    private Showtime selectedShowtime;

    public SelectShowtimeDialog(Frame owner, List<Showtime> showtimes) {
        super(owner, "Chọn Suất Chiếu", true);
        initComponents();
        loadShowtimes(showtimes);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Khởi tạo các thành phần của dialog
        tblShowtimes = new JTable(new DefaultTableModel(new Object[]{"ID", "Thời gian bắt đầu", "ID Phòng"}, 0));
        JScrollPane scrollPane = new JScrollPane(tblShowtimes);

        // Set layout, add components
        add(scrollPane, BorderLayout.CENTER);

        // Set sự kiện cho bảng
        tblShowtimes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblShowtimes.getSelectedRow();
                if (row != -1) {
                    int showtimeID = (int) tblShowtimes.getValueAt(row, 0);
                    int theatreID = (int) tblShowtimes.getValueAt(row, 2);
                    selectedShowtime = new Showtime(showtimeID, 0, theatreID, null); // Tạo Showtime với cả ShowtimeID và TheatreID
                    dispose(); // Đóng dialog khi chọn suất chiếu
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void loadShowtimes(List<Showtime> showtimes) {
        DefaultTableModel model = (DefaultTableModel) tblShowtimes.getModel();
        for (Showtime showtime : showtimes) {
            model.addRow(new Object[]{showtime.getShowtimeID(), showtime.getStartTime(), showtime.getTheatreID()});
        }
    }

    public Showtime getSelectedShowtime() {
        return selectedShowtime;
    }
}
