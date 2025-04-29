package model;

public class Movie {
	private int movieID;
    private String movieName;
    private String genre;
    private String director;
    private String cast;
    private String summary;
    private int duration;
    private int year;

    public Movie(int movieID, String movieName, String genre, String director, String cast, String summary, int duration, int year) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.genre = genre;
        this.director = director;
        this.cast = cast;
        this.summary = summary;
        this.duration = duration;
        this.year = year;
    }

	public Movie(String movieName, String genre, String director, String cast, String summary, int duration, int year) {
		super();
		this.movieName = movieName;
		this.genre = genre;
		this.director = director;
		this.cast = cast;
		this.summary = summary;
		this.duration = duration;
		this.year = year;
	}

	public Movie() {
		super();
	}

	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
    
    
}
