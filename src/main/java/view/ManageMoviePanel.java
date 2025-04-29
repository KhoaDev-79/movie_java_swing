package view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Movie;
import service.MovieService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageMoviePanel extends JPanel {

    private JTextField movieIDField;
    private JTextField movieNameField;
    private JTextField genreField;
    private JTextField directorField;
    private JTextField castField;
    private JTextField summaryField;
    private JTextField durationField;
    private JTextField yearField;
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private MovieService movieService;

    public ManageMoviePanel() {
        movieService = new MovieService();
        setLayout(new BorderLayout());

        // Tiêu đề ở phía bắc
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Quản lý phim"));
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new BorderLayout());
        moviePanel.setBorder(BorderFactory.createTitledBorder("Thông tin Phim"));

        centerPanel.add(moviePanel, BorderLayout.NORTH);

        JPanel northOfMoviePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addMovieButton = new JButton("Thêm phim");
        JButton editMovieButton = new JButton("Sửa phim");
        JButton deleteMovieButton = new JButton("Xóa phim");

        northOfMoviePanel.add(addMovieButton);
        northOfMoviePanel.add(editMovieButton);
        northOfMoviePanel.add(deleteMovieButton);

        moviePanel.add(northOfMoviePanel, BorderLayout.SOUTH);

        JPanel infoMoviePanel = new JPanel();
        infoMoviePanel.setLayout(new GridLayout(4, 2, 20, 30));
        moviePanel.add(infoMoviePanel, BorderLayout.CENTER);

        JLabel lblMovieID = new JLabel("Mã Phim:");
        movieIDField = new JTextField();
        movieIDField.setEnabled(false);
        movieIDField.setDisabledTextColor(Color.BLACK);

        JLabel lblMovieName = new JLabel("Tên Phim:");
        movieNameField = new JTextField();

        JLabel lblGenre = new JLabel("Thể loại:");
        genreField = new JTextField();

        JLabel lblDirector = new JLabel("Đạo diễn:");
        directorField = new JTextField();

        JLabel lblCast = new JLabel("Diễn viên:");
        castField = new JTextField();

        JLabel lblSummary = new JLabel("Tóm tắt:");
        summaryField = new JTextField();

        JLabel lblDuration = new JLabel("Thời lượng:");
        durationField = new JTextField();

        JLabel lblYear = new JLabel("Năm sản xuất:");
        yearField = new JTextField();

        infoMoviePanel.add(lblMovieID);
        infoMoviePanel.add(movieIDField);
        infoMoviePanel.add(lblMovieName);
        infoMoviePanel.add(movieNameField);
        infoMoviePanel.add(lblGenre);
        infoMoviePanel.add(genreField);
        infoMoviePanel.add(lblDirector);
        infoMoviePanel.add(directorField);
        infoMoviePanel.add(lblCast);
        infoMoviePanel.add(castField);
        infoMoviePanel.add(lblSummary);
        infoMoviePanel.add(summaryField);
        infoMoviePanel.add(lblDuration);
        infoMoviePanel.add(durationField);
        infoMoviePanel.add(lblYear);
        infoMoviePanel.add(yearField);

        // Bảng danh sách phim
        JPanel movieListPanel = new JPanel(new BorderLayout());
        movieListPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Phim"));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã Phim");
        tableModel.addColumn("Tên Phim");
        tableModel.addColumn("Thể loại");
        tableModel.addColumn("Đạo diễn");
        tableModel.addColumn("Diễn viên");
        tableModel.addColumn("Tóm tắt");
        tableModel.addColumn("Thời lượng");
        tableModel.addColumn("Năm sản xuất");

        movieTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(movieTable);

        movieListPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(movieListPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Hiển thị danh sách phim khi khởi tạo
        displayMovies();

        // Sự kiện thêm phim
        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });

        // Sự kiện sửa phim
        editMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMovie();
            }
        });

        // Sự kiện xóa phim
        deleteMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMovie();
            }
        });

        // Sự kiện chọn dòng trong bảng phim
        movieTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Kiểm tra xem đã chọn một dòng trong bảng chưa
                if (!e.getValueIsAdjusting() && movieTable.getSelectedRow() != -1) {
                    // Lấy thông tin từ dòng đã chọn
                    int selectedRow = movieTable.getSelectedRow();
                    int movieID = (int) movieTable.getValueAt(selectedRow, 0);
                    String movieName = (String) movieTable.getValueAt(selectedRow, 1);
                    String genre = (String) movieTable.getValueAt(selectedRow, 2);
                    String director = (String) movieTable.getValueAt(selectedRow, 3);
                    String cast = (String) movieTable.getValueAt(selectedRow, 4);
                    String summary = (String) movieTable.getValueAt(selectedRow, 5);
                    int duration = (int) movieTable.getValueAt(selectedRow, 6);
                    int year = (int) movieTable.getValueAt(selectedRow, 7);

                    // Đổ thông tin lên các trường nhập liệu
                    movieIDField.setText(String.valueOf(movieID));
                    movieNameField.setText(movieName);
                    genreField.setText(genre);
                    directorField.setText(director);
                    castField.setText(cast);
                    summaryField.setText(summary);
                    durationField.setText(String.valueOf(duration));
                    yearField.setText(String.valueOf(year));
                }
            }
        });
    }

    // Hiển thị danh sách phim
    private void displayMovies() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy danh sách phim từ CSDL
        List<Movie> movies = movieService.getAllMovies();

        // Hiển thị danh sách phim trên bảng
        for (Movie movie : movies) {
            Object[] rowData = {movie.getMovieID(), movie.getMovieName(), movie.getGenre(), movie.getDirector(),
                    movie.getCast(), movie.getSummary(), movie.getDuration(), movie.getYear()};
            tableModel.addRow(rowData);
        }
    }

    // Thêm phim mới
    private void addMovie() {
        // Lấy thông tin từ các trường nhập liệu
        String movieName = movieNameField.getText();
        String genre = genreField.getText();
        String director = directorField.getText();
        String cast = castField.getText();
        String summary = summaryField.getText();
        int duration = Integer.parseInt(durationField.getText());
        int year = Integer.parseInt(yearField.getText());

        // Tạo đối tượng phim mới
        Movie movie = new Movie(movieName, genre, director, cast, summary, duration, year);

        // Thêm phim vào CSDL
        boolean success = movieService.addMovie(movie);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm phim thành công");
            displayMovies(); // Hiển thị lại danh sách phim sau khi thêm thành công
            clearFields(); // Xóa dữ liệu trong các trường nhập liệu
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phim thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Sửa thông tin phim
    private void editMovie() {
        // Kiểm tra xem đã chọn một phim từ bảng chưa
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy thông tin từ các trường nhập liệu
        int movieID = Integer.parseInt(movieIDField.getText());
        String movieName = movieNameField.getText();
        String genre = genreField.getText();
        String director = directorField.getText();
        String cast = castField.getText();
        String summary = summaryField.getText();
        int duration = Integer.parseInt(durationField.getText());
        int year = Integer.parseInt(yearField.getText());

        // Tạo đối tượng phim cần sửa
        Movie movie = new Movie(movieID, movieName, genre, director, cast, summary, duration, year);

        // Hiển thị hộp thoại xác nhận sửa phim
        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa thông tin phim này?", "Xác nhận sửa phim", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Sửa thông tin phim trong CSDL
            boolean success = movieService.updateMovie(movie);

            if (success) {
                JOptionPane.showMessageDialog(this, "Sửa thông tin phim thành công");
                displayMovies(); // Hiển thị lại danh sách phim sau khi sửa thành công
                clearFields(); // Xóa dữ liệu trong các trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thông tin phim thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Xóa phim
    private void deleteMovie() {
        // Kiểm tra xem đã chọn một phim từ bảng chưa
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy mã phim từ dòng đã chọn
        int movieID = Integer.parseInt(movieIDField.getText());

        // Hiển thị hộp thoại xác nhận xóa phim
        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phim này?", "Xác nhận xóa phim", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Xóa phim trong CSDL
            boolean success = movieService.deleteMovie(movieID);

            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa phim thành công");
                displayMovies(); // Hiển thị lại danh sách phim sau khi xóa thành công
                clearFields(); // Xóa dữ liệu trong các trường nhập liệu
            } else {
                JOptionPane.showMessageDialog(this, "Xóa phim thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Xóa dữ liệu trong các trường nhập liệu
    private void clearFields() {
        movieIDField.setText("");
        movieNameField.setText("");
        genreField.setText("");
        directorField.setText("");
        castField.setText("");
        summaryField.setText("");
        durationField.setText("");
        yearField.setText("");
    }
}
