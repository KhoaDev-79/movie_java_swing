package model;

import java.sql.Timestamp;

public class Ticket {
    private int ticketID;
    private int showtimeID;
    private int seatID;
    private double price;
    private Timestamp bookingTime;
    private int invoiceID; // Thêm trường để liên kết với hóa đơn

    public Ticket() {
    }

    public Ticket(int ticketID, int showtimeID, int seatID, double price, Timestamp bookingTime, int invoiceID) {
        this.ticketID = ticketID;
        this.showtimeID = showtimeID;
        this.seatID = seatID;
        this.price = price;
        this.bookingTime = bookingTime;
        this.invoiceID = invoiceID;
    }
    

    public Ticket(int showtimeID, int seatID, double price, Timestamp bookingTime) {
		super();
		this.showtimeID = showtimeID;
		this.seatID = seatID;
		this.price = price;
		this.bookingTime = bookingTime;
	}

	public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getShowtimeID() {
        return showtimeID;
    }

    public void setShowtimeID(int showtimeID) {
        this.showtimeID = showtimeID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Timestamp bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }
}

