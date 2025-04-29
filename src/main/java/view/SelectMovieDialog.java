package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Movie;

public class SelectMovieDialog extends JDialog {
    private JTextField txtSearch;
    private JTable tblMovies;

    private Movie selectedMovie;

    public SelectMovieDialog(Frame owner, List<Movie> movies) {
        super(owner, "Chọn Phim", true);
        initComponents();
        loadMovies(movies);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Khởi tạo các thành phần của dialog
        txtSearch = new JTextField();
        tblMovies = new JTable(new DefaultTableModel(new Object[]{"ID", "Tên Phim"}, 0));
        JScrollPane scrollPane = new JScrollPane(tblMovies);

        // Set layout, add components
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Tìm kiếm phim: "), BorderLayout.WEST);
        topPanel.add(txtSearch, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Set sự kiện cho bảng
        tblMovies.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblMovies.getSelectedRow();
                if (row != -1) {
                    int movieID = (int) tblMovies.getValueAt(row, 0);
                    String movieName = (String) tblMovies.getValueAt(row, 1);
                    selectedMovie = new Movie(movieID, movieName, "", "", "", "", 0, 0);
                    dispose(); // Đóng dialog khi chọn phim
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void loadMovies(List<Movie> movies) {
        DefaultTableModel model = (DefaultTableModel) tblMovies.getModel();
        for (Movie movie : movies) {
            model.addRow(new Object[]{movie.getMovieID(), movie.getMovieName()});
        }
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }
}
