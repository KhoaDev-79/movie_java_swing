package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

import model.Movie;
import model.Showtime;
import model.Theatre;
import service.MovieService;
import service.ShowtimeService;
import service.TheatreService;

public class ManageShowtimePanel extends JPanel {
    private JComboBox<String> movieComboBox;
    private JComboBox<String> theatreComboBox;
    private JSpinner startTimeSpinner;
    private JButton addShowtimeButton;
    private JButton editShowtimeButton;
    private JButton deleteShowtimeButton;
    private JTable showtimeTable;
    private DefaultTableModel tableModel;
    private ShowtimeService showtimeService;
    private MovieService movieService;
    private TheatreService theatreService;

    public ManageShowtimePanel() {
        showtimeService = new ShowtimeService();
        movieService = new MovieService();
        theatreService = new TheatreService();
        setLayout(new BorderLayout());

        // Tiêu đề ở phía bắc
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Quản lý suất chiếu"));
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblMovie = new JLabel("Phim:");
        movieComboBox = new JComboBox<>();
        JLabel lblTheatre = new JLabel("Phòng chiếu:");
        theatreComboBox = new JComboBox<>();
        JLabel lblStartTime = new JLabel("Thời gian bắt đầu:");
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "yyyy-MM-dd HH:mm:ss");
        startTimeSpinner.setEditor(startTimeEditor);

        formPanel.add(lblMovie);
        formPanel.add(movieComboBox);
        formPanel.add(lblTheatre);
        formPanel.add(theatreComboBox);
        formPanel.add(lblStartTime);
        formPanel.add(startTimeSpinner);

        // Button thêm, sửa, xóa suất chiếu
        addShowtimeButton = new JButton("Thêm suất chiếu");
        editShowtimeButton = new JButton("Sửa suất chiếu");
        deleteShowtimeButton = new JButton("Xóa suất chiếu");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addShowtimeButton);
        buttonPanel.add(editShowtimeButton);
        buttonPanel.add(deleteShowtimeButton);
        formPanel.add(buttonPanel);

        centerPanel.add(formPanel, BorderLayout.NORTH);

        // Bảng danh sách suất chiếu
        JPanel showtimeListPanel = new JPanel(new BorderLayout());
        showtimeListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Suất Chiếu"));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Suất Chiếu");
        tableModel.addColumn("Phim");
        tableModel.addColumn("Phòng Chiếu");
        tableModel.addColumn("Thời Gian Bắt Đầu");

        showtimeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(showtimeTable);

        showtimeListPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(showtimeListPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Hiển thị danh sách phim và phòng chiếu trong combobox
        displayMovieComboBox();
        displayTheatreComboBox();
        // Hiển thị danh sách suất chiếu
        displayShowtimes();

        // Sự kiện thêm suất chiếu
        addShowtimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addShowtime();
            }
        });

        // Sự kiện sửa suất chiếu
        editShowtimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editShowtime();
            }
        });

        // Sự kiện xóa suất chiếu
        deleteShowtimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteShowtime();
            }
        });
        
     // Sự kiện chọn dòng trong bảng suất chiếu
        showtimeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Kiểm tra xem đã chọn một dòng trong bảng chưa
                if (!e.getValueIsAdjusting() && showtimeTable.getSelectedRow() != -1) {
                    // Lấy thông tin từ dòng đã chọn
                    int selectedRow = showtimeTable.getSelectedRow();
                    int showtimeID = (int) showtimeTable.getValueAt(selectedRow, 0);
                    int movieID = (int) showtimeTable.getValueAt(selectedRow, 1);
                    int theatreID = (int) showtimeTable.getValueAt(selectedRow, 2);
                    Timestamp startTime = (Timestamp) showtimeTable.getValueAt(selectedRow, 3);

                    // Đổ thông tin lên các trường nhập liệu
                    movieComboBox.setSelectedItem(getMovieName(movieID));
                    theatreComboBox.setSelectedItem(getTheatreName(theatreID));
                    startTimeSpinner.setValue(new Date(startTime.getTime()));
                }
            }
        });

    }
    
 // Lấy tên của phim từ ID phim
    private String getMovieName(int movieID) {
        List<Movie> movies = movieService.getAllMovies();
        for (Movie movie : movies) {
            if (movie.getMovieID() == movieID) {
                return movie.getMovieName();
            }
        }
        return ""; // Trả về chuỗi rỗng nếu không tìm thấy
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

    // Hiển thị danh sách phim trong combobox
    private void displayMovieComboBox() {
        movieComboBox.removeAllItems();

        // Lấy danh sách phim từ CSDL
        List<Movie> movies = movieService.getAllMovies();

        // Hiển thị danh sách phim trong combobox
        for (Movie movie : movies) {
            movieComboBox.addItem(movie.getMovieName());
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

    // Lấy ID của phim từ tên phim
    private int getMovieID(String movieName) {
        List<Movie> movies = movieService.getAllMovies();
        for (Movie movie : movies) {
            if (movie.getMovieName().equals(movieName)) {
                return movie.getMovieID();
            }
        }
        return -1; // Trả về -1 nếu không tìm thấy
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

    // Thêm suất chiếu mới
    private void addShowtime() {
        // Lấy thông tin từ các trường nhập liệu
        String movieName = (String) movieComboBox.getSelectedItem();
        String theatreName = (String) theatreComboBox.getSelectedItem();
        Timestamp startTime = new Timestamp(((Date) startTimeSpinner.getValue()).getTime());
        int movieID = getMovieID(movieName);
        int theatreID = getTheatreID(theatreName);

        // Kiểm tra xem có trường nào trống không
        if (movieName.isEmpty() || theatreName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng suất chiếu mới
        Showtime showtime = new Showtime(movieID, theatreID, startTime);

        // Thêm suất chiếu vào CSDL
        boolean success = showtimeService.addShowtime(showtime);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm suất chiếu thành công");
            displayShowtimes(); // Hiển thị lại danh sách suất chiếu sau khi thêm thành công
            clearFields(); // Xóa dữ liệu trong các trường nhập liệu
        } else {
            JOptionPane.showMessageDialog(this, "Thêm suất chiếu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hiển thị danh sách suất chiếu
    private void displayShowtimes() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy danh sách suất chiếu từ CSDL
        List<Showtime> showtimes = showtimeService.getAllShowtimes();

        // Hiển thị danh sách suất chiếu trên bảng
        for (Showtime showtime : showtimes) {
            Object[] rowData = {showtime.getShowtimeID(), showtime.getMovieID(), showtime.getTheatreID(), showtime.getStartTime()};
            tableModel.addRow(rowData);
        }
    }

    // Xóa dữ liệu trong các trường nhập liệu
    private void clearFields() {
        movieComboBox.setSelectedIndex(0);
        theatreComboBox.setSelectedIndex(0);
        startTimeSpinner.setValue(new Date());
    }

 // Sự kiện sửa suất chiếu
    private void editShowtime() {
        // Kiểm tra xem đã chọn một suất chiếu từ bảng chưa
        int selectedRow = showtimeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một suất chiếu để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy thông tin từ dòng được chọn
        int showtimeID = (int) showtimeTable.getValueAt(selectedRow, 0);

        // Lấy thông tin từ các trường nhập liệu
        String movieName = (String) movieComboBox.getSelectedItem();
        String theatreName = (String) theatreComboBox.getSelectedItem();
        Timestamp startTime = new Timestamp(((Date) startTimeSpinner.getValue()).getTime());
        int movieID = getMovieID(movieName);
        int theatreID = getTheatreID(theatreName);

        // Kiểm tra xem có trường nào trống không
        if (movieName.isEmpty() || theatreName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hiển thị hộp thoại xác nhận sửa suất chiếu
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa suất chiếu này?", "Xác nhận Sửa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Tạo đối tượng suất chiếu mới
            Showtime showtime = new Showtime(showtimeID, movieID, theatreID, startTime);

            // Sửa thông tin suất chiếu trong CSDL
            boolean success = showtimeService.updateShowtime(showtime);

            if (success) {
                JOptionPane.showMessageDialog(this, "Sửa suất chiếu thành công");
                displayShowtimes(); // Hiển thị lại danh sách suất chiếu sau khi sửa thành công
                clearFields(); // Xóa dữ liệu trong các trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(this, "Sửa suất chiếu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

 // Sự kiện xóa suất chiếu
    private void deleteShowtime() {
        // Kiểm tra xem đã chọn một suất chiếu từ bảng chưa
        int selectedRow = showtimeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một suất chiếu để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xác nhận xóa suất chiếu
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa suất chiếu này?", "Xác nhận Xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Lấy ID của suất chiếu cần xóa
            int showtimeID = (int) showtimeTable.getValueAt(selectedRow, 0);

            // Xóa suất chiếu từ CSDL
            boolean success = showtimeService.deleteShowtime(showtimeID);

            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa suất chiếu thành công");
                displayShowtimes(); // Hiển thị lại danh sách suất chiếu sau khi xóa thành công
            } else {
                JOptionPane.showMessageDialog(this, "Xóa suất chiếu thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
