package service;

import dao.InvoiceDao;
import model.Invoice;
import model.Ticket;

import java.sql.Timestamp;
import java.util.List;

public class InvoiceService {

    private final InvoiceDao invoiceDao;

    public InvoiceService() {
        this.invoiceDao = new InvoiceDao();
    }

    public boolean saveInvoice(Invoice invoice, List<Ticket> tickets) {
        return invoiceDao.saveInvoice(invoice, tickets);
    }
    
    // Phương thức để lấy toàn bộ hóa đơn từ CSDL
    public List<Invoice> getAllInvoices() {
        return invoiceDao.getAllInvoices();
    }

    // Phương thức để lấy danh sách hóa đơn theo khoảng thời gian giữa hai ngày
    public List<Invoice> getInvoicesByDateRange(Timestamp startDate, Timestamp endDate) {
        return invoiceDao.getInvoicesByDateRange(startDate, endDate);
    }
}
