package model;

public class Seat {
	private int seatID;
    private String seatNumber;
    private int theatreID;
	public Seat(int seatID, String seatNumber, int theatreID) {
		super();
		this.seatID = seatID;
		this.seatNumber = seatNumber;
		this.theatreID = theatreID;
	}
	public Seat(String seatNumber, int theatreID) {
		super();
		this.seatNumber = seatNumber;
		this.theatreID = theatreID;
	}
	public Seat() {
		super();
	}
	public int getSeatID() {
		return seatID;
	}
	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public int getTheatreID() {
		return theatreID;
	}
	public void setTheatreID(int theatreID) {
		this.theatreID = theatreID;
	}
    
    
}
