package view;

import javax.swing.*;

import dao.InvoiceDao;
import model.Invoice;
import model.Movie;
import model.Seat;
import model.Showtime;
import model.Ticket;
import model.UserInfo;
import service.MovieService;
import service.SeatService;
import service.ShowtimeService;
import service.TicketService;
import service.UserInfoService;
import util.TicketTableModel;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SaleTicketPanel extends JPanel {
    private JTextField txtEmployeeID;
    private JTextField txtCustomerID;
    private JTextField txtInvoiceDate;
    private JTextField txtTotalAmount;
    private JButton btnSelectCustomer;
    private JButton btnCreateInvoice;
    private JButton btnCancelInvoice;
    private JTextField txtShowtimeID;
    private JTextField txtSeatID;
    private JTextField txtPrice;
    private JButton btnSelectMovie;
    private JButton btnSelectShowtime;
	private JButton btnAddTicket;
	private JButton btnCancelTicket;
	private JTextField txtMovieID;
	private JButton btnSelectSeat;
	private JTextField txttheatreID;
	
 	private JTable ticketTable; // Bảng danh sách vé
    private TicketTableModel ticketTableModel; // Mô hình cho bảng danh sách vé
    private List<Ticket> ticketList;

    public SaleTicketPanel() {
        setLayout(new GridLayout(1, 2));

        // Panel bên trái: Thông tin hóa đơn
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Đặt padding

        JPanel invoicePanel = new JPanel(new GridLayout(0, 1));
        invoicePanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        JPanel invoiceDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblInvoiceDate = new JLabel("Ngày:");
        txtInvoiceDate = new JTextField();
        txtInvoiceDate.setEditable(false);
        invoiceDatePanel.add(lblInvoiceDate);
        invoiceDatePanel.add(txtInvoiceDate);
        invoicePanel.add(invoiceDatePanel);
        
        long currentInvoiceDate = System.currentTimeMillis();
        Timestamp invoiceDateCurrent = new Timestamp(currentInvoiceDate);
        txtInvoiceDate.setText(invoiceDateCurrent.toString());

        JPanel employeePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblEmployeeID = new JLabel("Nhân viên ID:");
        txtEmployeeID = new JTextField();
        txtEmployeeID.setEditable(false);
        employeePanel.add(lblEmployeeID);
        employeePanel.add(txtEmployeeID);
        invoicePanel.add(employeePanel);
        
        if(LoginView.getLoggedInUser() != null) {
        	txtEmployeeID.setText(LoginView.getLoggedInUser().getUserID() + "");
        }

        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCustomerID = new JLabel("Khách hàng ID:");
        txtCustomerID = new JTextField();
        customerPanel.add(lblCustomerID);
        customerPanel.add(txtCustomerID);
        btnSelectCustomer = new JButton("Chọn");
        customerPanel.add(btnSelectCustomer);
        invoicePanel.add(customerPanel);

        JPanel totalAmountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalAmount = new JLabel("Tổng tiền:");
        txtTotalAmount = new JTextField();
        txtTotalAmount.setEditable(false);
        totalAmountPanel.add(lblTotalAmount);
        totalAmountPanel.add(txtTotalAmount);
        invoicePanel.add(totalAmountPanel);
        
     // Set kích thước cho các input và label
        txtInvoiceDate.setPreferredSize(new Dimension(200, 30));
        txtEmployeeID.setPreferredSize(txtInvoiceDate.getPreferredSize());
        txtCustomerID.setPreferredSize(txtInvoiceDate.getPreferredSize());
        txtTotalAmount.setPreferredSize(txtInvoiceDate.getPreferredSize());

        lblInvoiceDate.setPreferredSize(lblCustomerID.getPreferredSize());
        lblEmployeeID.setPreferredSize(lblCustomerID.getPreferredSize());
        lblInvoiceDate.setPreferredSize(lblCustomerID.getPreferredSize());
        lblTotalAmount.setPreferredSize(lblCustomerID.getPreferredSize());


        JPanel buttonInvoicePanel = new JPanel(new FlowLayout());
        btnCreateInvoice = new JButton("Lưu hóa đơn");
        btnCancelInvoice = new JButton("Hủy hóa đơn");
        buttonInvoicePanel.add(btnCreateInvoice);
        buttonInvoicePanel.add(btnCancelInvoice);
        invoicePanel.add(buttonInvoicePanel);

        leftPanel.add(invoicePanel, BorderLayout.NORTH);

        // Panel bên phải: Tạo vé
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Đặt padding

     // Panel trên: Tạo vé
        JPanel ticketPanel = new JPanel(new GridLayout(0, 1));
        ticketPanel.setBorder(BorderFactory.createTitledBorder("Tạo vé"));

        JPanel moviePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblMovieID = new JLabel("Phim ID:");
        txtMovieID = new JTextField();
        txtMovieID.setEditable(false);
        moviePanel.add(lblMovieID);
        moviePanel.add(txtMovieID);
        btnSelectMovie = new JButton("Chọn");
        moviePanel.add(btnSelectMovie);
        ticketPanel.add(moviePanel);

        JPanel showtimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblShowtimeID = new JLabel("Suất chiếu ID:");
        txtShowtimeID = new JTextField();
        txtShowtimeID.setEditable(false);
        showtimePanel.add(lblShowtimeID);
        showtimePanel.add(txtShowtimeID);
        btnSelectShowtime = new JButton("Chọn");
        showtimePanel.add(btnSelectShowtime);
        ticketPanel.add(showtimePanel);
        
        JPanel theatrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbltheatreID = new JLabel("Phòng ID:");
        txttheatreID = new JTextField();
        txttheatreID.setEditable(false);
        theatrePanel.add(lbltheatreID);
        theatrePanel.add(txttheatreID);
        ticketPanel.add(theatrePanel);

        JPanel seatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSeatID = new JLabel("Ghế ID:");
        txtSeatID = new JTextField();
        txtSeatID.setEditable(false);
        seatPanel.add(lblSeatID);
        seatPanel.add(txtSeatID);
        btnSelectSeat = new JButton("Chọn");
        seatPanel.add(btnSelectSeat);
        ticketPanel.add(seatPanel);

        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblPrice = new JLabel("Giá:");
        txtPrice = new JTextField();
        pricePanel.add(lblPrice);
        pricePanel.add(txtPrice);
        ticketPanel.add(pricePanel);

        // Panel layout chứa nút tạo vé, hủy vé
        JPanel buttonTicketPanel = new JPanel(new FlowLayout());
        btnAddTicket = new JButton("Thêm vé");
        btnCancelTicket = new JButton("Hủy vé");
        buttonTicketPanel.add(btnAddTicket);
        buttonTicketPanel.add(btnCancelTicket);
        ticketPanel.add(buttonTicketPanel);

        // Set kích thước cho các input và label trong phần tạo vé
        lblMovieID.setPreferredSize(lblShowtimeID.getPreferredSize());
        lbltheatreID.setPreferredSize(lblShowtimeID.getPreferredSize());
        lblSeatID.setPreferredSize(lblShowtimeID.getPreferredSize());
        lblPrice.setPreferredSize(lblShowtimeID.getPreferredSize());

        txtMovieID.setPreferredSize(txtInvoiceDate.getPreferredSize());
        txtShowtimeID.setPreferredSize(txtInvoiceDate.getPreferredSize());
        txttheatreID.setPreferredSize(txtInvoiceDate.getPreferredSize());
        txtSeatID.setPreferredSize(txtInvoiceDate.getPreferredSize());
        txtPrice.setPreferredSize(txtInvoiceDate.getPreferredSize());


        rightPanel.add(ticketPanel);

        // Panel dưới: Danh sách chi tiết hóa đơn
        JPanel ticketDetailPanel = new JPanel(new BorderLayout());
        ticketDetailPanel.setBorder(BorderFactory.createTitledBorder("Danh sách vé"));

        JScrollPane ticketScrollPane = new JScrollPane();
        ticketTable = new JTable(); // Bảng danh sách vé
        ticketTableModel = new TicketTableModel(); // Mô hình cho bảng danh sách vé
        ticketTable.setModel(ticketTableModel);
        ticketScrollPane.setViewportView(ticketTable);
        ticketDetailPanel.add(ticketScrollPane, BorderLayout.CENTER);

        rightPanel.add(ticketDetailPanel);

        // Thêm panel bên trái và bên phải vào màn hình bán vé
        add(leftPanel);
        add(rightPanel);
        
        btnSelectMovie.addActionListener(e -> {
            MovieService movieService = new MovieService();
            List<Movie> movies = movieService.getAllMovies();

            SelectMovieDialog selectMovieDialog = new SelectMovieDialog((Frame) SwingUtilities.getWindowAncestor(this), movies);
            selectMovieDialog.setVisible(true);

            // Lấy thông tin phim đã chọn và cập nhật vào input
            Movie selectedMovie = selectMovieDialog.getSelectedMovie();
            if (selectedMovie != null) {
                txtMovieID.setText(String.valueOf(selectedMovie.getMovieID()));
                // Cập nhật các input khác nếu cần
            }
        });
        
        btnSelectShowtime.addActionListener(e -> {
            // Kiểm tra xem input id phim đã có giá trị hay chưa
            if (txtMovieID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phim trước khi chọn suất chiếu.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Nếu chưa chọn phim, không thực hiện các bước tiếp theo
            }

            // Lấy ID phim từ input
            int movieID = Integer.parseInt(txtMovieID.getText());

            // Lấy danh sách suất chiếu cho phim đã chọn từ cơ sở dữ liệu
            ShowtimeService showtimeService = new ShowtimeService();
            List<Showtime> showtimes = showtimeService.getShowtimesByMovie(movieID);

            // Hiển thị hộp thoại chọn suất chiếu
            SelectShowtimeDialog selectShowtimeDialog = new SelectShowtimeDialog((Frame) SwingUtilities.getWindowAncestor(this), showtimes);
            selectShowtimeDialog.setVisible(true);

            // Lấy thông tin suất chiếu đã chọn và cập nhật vào input
            Showtime selectedShowtime = selectShowtimeDialog.getSelectedShowtime();
            if (selectedShowtime != null) {
                txtShowtimeID.setText(String.valueOf(selectedShowtime.getShowtimeID()));
                txttheatreID.setText(String.valueOf(selectedShowtime.getTheatreID()));
            }
        });
        
        btnSelectSeat.addActionListener(e -> {
        	
        	if (txtShowtimeID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Suất chiếu trước khi chọn ghế!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Lấy ID suất chiếu từ input
            int showtimeID = Integer.parseInt(txtShowtimeID.getText());
            int theatreID = Integer.parseInt(txttheatreID.getText());
            
            // Lấy danh sách ghế của phòng từ service
            SeatService seatService = new SeatService();
            List<Seat> allSeatsByTheatre = seatService.getSeatsByTheatreID(theatreID);
            
            // Lấy danh sách vé đã đặt theo showtimeID
            TicketService ticketService = new TicketService();
            List<Ticket> bookedTickets = ticketService.getTicketsByShowtimeID(showtimeID);
            
            // Hiển thị hộp thoại chọn ghế
            SelectSeatDialog selectSeatDialog = new SelectSeatDialog((Frame) SwingUtilities.getWindowAncestor(this), allSeatsByTheatre, bookedTickets);
            selectSeatDialog.setVisible(true);
            
            // Xử lý thông tin ghế đã chọn nếu cần
            Seat selectedSeat = selectSeatDialog.getSelectedSeat();
            if (selectedSeat != null) {
                // Cập nhật thông tin ghế đã chọn vào input hoặc thực hiện các thao tác khác
                txtSeatID.setText(String.valueOf(selectedSeat.getSeatID()));
            }
        });
        
        btnAddTicket.addActionListener(e -> {
            // Kiểm tra xem các input đã được điền đầy đủ thông tin chưa
            if (txtShowtimeID.getText().isEmpty() || txtSeatID.getText().isEmpty() || txtPrice.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin vé.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy thông tin vé từ các input
            int showtimeID = Integer.parseInt(txtShowtimeID.getText());
            int seatID = Integer.parseInt(txtSeatID.getText());
            double price = Double.parseDouble(txtPrice.getText());
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp bookingTime = new Timestamp(currentTimeMillis);

            // Tạo vé mới và thêm vào danh sách vé
            Ticket newTicket = new Ticket();
            newTicket.setShowtimeID(showtimeID);
            newTicket.setSeatID(seatID);
            newTicket.setPrice(price);
            newTicket.setBookingTime(bookingTime);

            if (ticketList == null) {
                ticketList = new ArrayList<>(); // Khởi tạo danh sách vé nếu chưa có
            }
            ticketList.add(newTicket);

            // Cập nhật bảng danh sách vé
            ticketTableModel.setTicketList(ticketList);
            ticketTableModel.fireTableDataChanged();

            // Tính tổng tiền và cập nhật vào txtTotalAmount
            double totalAmount = calculateTotalAmount(ticketList);
            txtTotalAmount.setText(String.valueOf(totalAmount));

            // Xóa dữ liệu trong các input
            txtShowtimeID.setText("");
            txtSeatID.setText("");
            txtPrice.setText("");
        });
        
        btnSelectCustomer.addActionListener(e -> {
            // Gọi service để lấy danh sách khách hàng
            UserInfoService userInfoService = new UserInfoService();
            List<UserInfo> customers = userInfoService.getCustomers(); // Lấy danh sách khách hàng với RoleID = 3
            
            // Hiển thị hộp thoại chọn khách hàng
            SelectCustomerDialog selectCustomerDialog = new SelectCustomerDialog((Frame) SwingUtilities.getWindowAncestor(this), customers);
            selectCustomerDialog.setVisible(true);
            
            // Lấy thông tin khách hàng đã chọn từ hộp thoại
            UserInfo selectedCustomer = selectCustomerDialog.getSelectedCustomer();
            
            // Kiểm tra xem khách hàng đã chọn có tồn tại không
            if (selectedCustomer != null) {
                // Cập nhật thông tin khách hàng trên giao diện bán hàng
                txtCustomerID.setText(String.valueOf(selectedCustomer.getUserID()));
                // Các trường thông tin khác của khách hàng (nếu cần)
            }
        });
        
        btnCreateInvoice.addActionListener(e -> {
            // Kiểm tra xem các trường thông tin của hóa đơn đã được điền đầy đủ hay không
            if (txtEmployeeID.getText().isEmpty() || txtCustomerID.getText().isEmpty() || txtTotalAmount.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin của hóa đơn.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra xem danh sách vé có rỗng hay không
            if (ticketList == null || ticketList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một vé.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lấy thông tin của hóa đơn từ các input
            int employeeID = Integer.parseInt(txtEmployeeID.getText());
            int customerID = Integer.parseInt(txtCustomerID.getText());
            double totalAmount = Double.parseDouble(txtTotalAmount.getText());
            Timestamp invoiceDate = Timestamp.valueOf(txtInvoiceDate.getText());

            // Tạo đối tượng Invoice từ thông tin đã lấy được
            Invoice invoice = new Invoice();
            invoice.setEmployeeID(employeeID);
            invoice.setCustomerID(customerID);
            invoice.setTotalAmount(totalAmount);
            invoice.setInvoiceDate(invoiceDate);

            // Gọi service để lưu hóa đơn và danh sách vé vào cơ sở dữ liệu
            InvoiceDao invoiceDao = new InvoiceDao();
            boolean saveSuccess = invoiceDao.saveInvoice(invoice, ticketList);

            if (saveSuccess) {
                JOptionPane.showMessageDialog(this, "Hóa đơn đã được lưu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình lưu hóa đơn.", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }

            // Sau khi lưu hóa đơn, cần làm sạch các input và danh sách vé để chuẩn bị cho hóa đơn mới
            resetInputs();
        });

    }
    
 // Phương thức để tính tổng tiền từ danh sách vé
    private double calculateTotalAmount(List<Ticket> ticketList) {
        double totalAmount = 0;
        for (Ticket ticket : ticketList) {
            totalAmount += ticket.getPrice();
        }
        return totalAmount;
    }

 // Hàm để làm sạch các input và danh sách vé
    private void resetInputs() {
        txtCustomerID.setText("");
        txtTotalAmount.setText("");
        txtMovieID.setText("");
        txtShowtimeID.setText("");
        txttheatreID.setText("");
        txtSeatID.setText("");
        txtPrice.setText("");
        ticketList.clear();
        ticketTableModel.setTicketList(ticketList);
        ticketTableModel.fireTableDataChanged();
    }

}
