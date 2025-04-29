package model;

import java.sql.Timestamp;

public class Invoice {
    private int invoiceID;
    private int employeeID;
    private int customerID;
    private Timestamp invoiceDate;
    private double totalAmount;

    public Invoice() {
    }

    public Invoice(int invoiceID, int employeeID, int customerID, Timestamp invoiceDate, double totalAmount) {
        this.invoiceID = invoiceID;
        this.employeeID = employeeID;
        this.customerID = customerID;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
    }
    

    public Invoice(int employeeID, int customerID, Timestamp invoiceDate, double totalAmount) {
		super();
		this.employeeID = employeeID;
		this.customerID = customerID;
		this.invoiceDate = invoiceDate;
		this.totalAmount = totalAmount;
	}

	public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

