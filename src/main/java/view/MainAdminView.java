package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainAdminView extends JFrame {
    private JPanel menuPanel;
    private JPanel displayPanel;
    
 // Biến để lưu trữ item menu được chọn trước đó
    private JLabel previousSelectedMenuItem;

    public MainAdminView() {
        setTitle("Admin Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        menuPanel.setBackground(Color.CYAN);
        
     // Thêm các item vào menu panel dựa vào vai trò của người dùng
        if (LoginView.getLoggedInUser().getRoleID() == 1) { // Admin
            addAdminMenuItems();
        } else if (LoginView.getLoggedInUser().getRoleID() == 2) { // Nhân viên
            addStaffMenuItems();
        }

        // Tạo display panel
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        // Thêm menu panel và display panel vào main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(menuPanel, BorderLayout.WEST);
        getContentPane().add(displayPanel, BorderLayout.CENTER);
    }
    
    private void addAdminMenuItems() {
        addMenuItem("Quản lý Phim", "/icon/movie_icon.png");
        addMenuItem("Phòng chiếu", "/icon/theatre_icon.png");
        addMenuItem("Quản lý ghế", "/icon/seat_icon.png");
        addMenuItem("Quản lý Suất chiếu", "/icon/showtime_icon.png");
        addMenuItem("Nhân viên", "/icon/staff_icon.png");
        addMenuItem("Hóa đơn", "/icon/invoice_icon.png");
        addMenuItem("Đăng xuất", "/icon/logout_icon.png");
    }
    
    private void addStaffMenuItems() {
    	addMenuItem("Khách hàng", "/icon/customer_icon.png");
        addMenuItem("Bán vé", "/icon/cart_icon.png");
        addMenuItem("Hóa đơn", "/icon/invoice_icon.png");
        addMenuItem("Đăng xuất", "/icon/logout_icon.png");
    }

    private void addMenuItem(String text, String iconPath) {
        // Load icon từ đường dẫn
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        
        // Kiểm tra nếu icon được load thành công
        if (icon != null) {
            // Scale icon để có kích thước phù hợp
            Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledIcon);
            
            // Tạo JLabel với icon và văn bản
            JLabel menuItem = new JLabel(text, icon, JLabel.LEFT);
            menuItem.setPreferredSize(new Dimension(menuPanel.getPreferredSize().width, 40));
            menuItem.setIconTextGap(10);
            menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
         // Đặt chiều rộng tối đa của item menu bằng chiều rộng của panel cha
            menuItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, menuItem.getPreferredSize().height));
         // Đặt padding cho JLabel (khoảng cách giữa nội dung và lề trái)
            menuItem.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            
            menuItem.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                	JLabel clickedMenuItem = (JLabel) evt.getSource(); // Lấy ra item menu được nhấn

                    // Đặt màu nền của item menu được nhấn
                    clickedMenuItem.setOpaque(true); // Đặt thuộc tính này thành true để có thể thay đổi màu nền
                    clickedMenuItem.setBackground(Color.WHITE);
                    // Xử lý sự kiện khi nhấn vào item menu
                    handleMenuItemClick(text, clickedMenuItem);
                }
            });
            
            // Thêm JLabel vào menuPanel
            menuPanel.add(menuItem);
            
            // Thêm khoảng cách sau mỗi JMenuItem
            menuPanel.add(Box.createVerticalStrut(20));
        } else {
            // Hiển thị thông báo nếu không thể load icon
            System.out.println("Không thể tải icon từ đường dẫn: " + iconPath);
        }
    }

    private void handleMenuItemClick(String text, JLabel clickedMenuItem) {
    	// Đặt lại màu nền cho item menu được chọn trước đó
        if (previousSelectedMenuItem != null) {
            previousSelectedMenuItem.setBackground(menuPanel.getBackground());
        }

        // Cập nhật lại item menu được chọn trước đó
        previousSelectedMenuItem = clickedMenuItem;
    	
        switch (text) {
            case "Quản lý Phim":
                displayPanel.removeAll();
                displayPanel.add(new ManageMoviePanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Phòng chiếu":
                displayPanel.removeAll();
                displayPanel.add(new ManageTheatrePanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Quản lý ghế":
                displayPanel.removeAll();
                displayPanel.add(new ManageSeatPanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Quản lý Suất chiếu":
                displayPanel.removeAll();
                displayPanel.add(new ManageShowtimePanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Nhân viên":
                displayPanel.removeAll();
                displayPanel.add(new ManageUserPanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Khách hàng":
                displayPanel.removeAll();
                displayPanel.add(new ManageCustomerPanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Bán vé":
                displayPanel.removeAll();
                displayPanel.add(new SaleTicketPanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Hóa đơn":
                displayPanel.removeAll();
                displayPanel.add(new ManageInvoicePanel(), BorderLayout.CENTER);
                displayPanel.revalidate();
                displayPanel.repaint();
                break;
            case "Đăng xuất":
                int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                	LoginView loginView = new LoginView();
                    loginView.setVisible(true);
                    dispose();
                }
                break;
        }
        
     // Cập nhật lại giao diện
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainAdminView adminView = new MainAdminView();
                adminView.setVisible(true);
            }
        });
    }
}

