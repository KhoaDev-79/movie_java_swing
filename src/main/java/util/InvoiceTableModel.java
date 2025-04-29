package util;

import model.Invoice;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class InvoiceTableModel extends AbstractTableModel {

    private List<Invoice> invoices;
    private final String[] columnNames = {"ID", "Employee ID", "Customer ID", "Invoice Date", "Total Amount"};

    public InvoiceTableModel() {
        invoices = new ArrayList<>();
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice invoice = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return invoice.getInvoiceID();
            case 1:
                return invoice.getEmployeeID();
            case 2:
                return invoice.getCustomerID();
            case 3:
                return invoice.getInvoiceDate();
            case 4:
                return invoice.getTotalAmount();
            default:
                return null;
        }
    }
}
