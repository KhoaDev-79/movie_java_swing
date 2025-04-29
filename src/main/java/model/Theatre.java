package model;

public class Theatre {
	private int theatreID;
    private String theatreName;
    private String screen;
    private int seatCapacity;
	public Theatre(int theatreID, String theatreName, String screen, int seatCapacity) {
		super();
		this.theatreID = theatreID;
		this.theatreName = theatreName;
		this.screen = screen;
		this.seatCapacity = seatCapacity;
	}
	public Theatre(String theatreName, String screen, int seatCapacity) {
		super();
		this.theatreName = theatreName;
		this.screen = screen;
		this.seatCapacity = seatCapacity;
	}
	public Theatre() {
		super();
	}
	public int getTheatreID() {
		return theatreID;
	}
	public void setTheatreID(int theatreID) {
		this.theatreID = theatreID;
	}
	public String getTheatreName() {
		return theatreName;
	}
	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	public String getScreen() {
		return screen;
	}
	public void setScreen(String screen) {
		this.screen = screen;
	}
	public int getSeatCapacity() {
		return seatCapacity;
	}
	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}
	@Override
	public String toString() {
		return this.theatreName;
	}

    
}
