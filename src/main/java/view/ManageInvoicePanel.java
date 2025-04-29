package view;

import com.toedter.calendar.JDateChooser;
import service.InvoiceService;
import util.InvoiceTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageInvoicePanel extends JPanel {
    private JDateChooser fromDateChooser;
    private JDateChooser toDateChooser;
    private JButton searchButton;
    private JButton viewAllButton;
    private JTable invoiceTable;
    private JLabel totalRevenueLabel;

    private InvoiceService invoiceService;
    private InvoiceTableModel invoiceTableModel;

    public ManageInvoicePanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        invoiceService = new InvoiceService();
        invoiceTableModel = new InvoiceTableModel();

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fromDateChooser = new JDateChooser();
        toDateChooser = new JDateChooser();
        searchButton = new JButton("Search");
        viewAllButton = new JButton("View All");
        searchPanel.add(new JLabel("From:"));
        searchPanel.add(fromDateChooser);
        searchPanel.add(new JLabel("To:"));
        searchPanel.add(toDateChooser);
        searchPanel.add(searchButton);
        searchPanel.add(viewAllButton);

        add(searchPanel, BorderLayout.NORTH);

        invoiceTable = new JTable(invoiceTableModel);
        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        add(scrollPane, BorderLayout.CENTER);

        totalRevenueLabel = new JLabel("Total Revenue: $0.00");
        totalRevenueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(totalRevenueLabel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchInvoices();
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllInvoices();
            }
        });
    }

    private void searchInvoices() {
        Date fromDate = fromDateChooser.getDate();
        Date toDate = toDateChooser.getDate();

        if (fromDate == null || toDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both From and To dates.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp fromTimestamp = new Timestamp(fromDate.getTime());
        Timestamp toTimestamp = new Timestamp(toDate.getTime());

        invoiceTableModel.setInvoices(invoiceService.getInvoicesByDateRange(fromTimestamp, toTimestamp));
        invoiceTableModel.fireTableDataChanged(); // Thông báo cho JTable cập nhật dữ liệu
        updateTotalRevenue();
    }

    private void viewAllInvoices() {
        invoiceTableModel.setInvoices(invoiceService.getAllInvoices());
        invoiceTableModel.fireTableDataChanged(); // Thông báo cho JTable cập nhật dữ liệu
        updateTotalRevenue();
    }

    private void updateTotalRevenue() {
        double totalRevenue = calculateTotalRevenue();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        totalRevenueLabel.setText("Total Revenue: $" + df.format(totalRevenue));
    }

    private double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        for (int i = 0; i < invoiceTableModel.getRowCount(); i++) {
            double invoiceAmount = (double) invoiceTableModel.getValueAt(i, 4);
            totalRevenue += invoiceAmount;
        }
        return totalRevenue;
    }
}
