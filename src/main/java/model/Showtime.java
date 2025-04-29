package model;

import java.sql.Timestamp;

public class Showtime {
	private int showtimeID;
    private int movieID;
    private int theatreID;
    private Timestamp startTime;
	public Showtime(int showtimeID, int movieID, int theatreID, Timestamp startTime) {
		super();
		this.showtimeID = showtimeID;
		this.movieID = movieID;
		this.theatreID = theatreID;
		this.startTime = startTime;
	}
	public Showtime(int movieID, int theatreID, Timestamp startTime) {
		super();
		this.movieID = movieID;
		this.theatreID = theatreID;
		this.startTime = startTime;
	}
	public Showtime() {
		super();
	}
	public int getShowtimeID() {
		return showtimeID;
	}
	public void setShowtimeID(int showtimeID) {
		this.showtimeID = showtimeID;
	}
	public int getMovieID() {
		return movieID;
	}
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	public int getTheatreID() {
		return theatreID;
	}
	public void setTheatreID(int theatreID) {
		this.theatreID = theatreID;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
    
}
