package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.SQLServerConnection;
import model.Movie;

public class MovieDao {

    public boolean addMovie(Movie movie) {
        String query = "INSERT INTO Movie (MovieName, Genre, Director, Cast, Summary, Duration, Year) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getGenre());
            statement.setString(3, movie.getDirector());
            statement.setString(4, movie.getCast());
            statement.setString(5, movie.getSummary());
            statement.setInt(6, movie.getDuration());
            statement.setInt(7, movie.getYear());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateMovie(Movie movie) {
        String query = "UPDATE Movie SET MovieName=?, Genre=?, Director=?, Cast=?, Summary=?, Duration=?, Year=? WHERE MovieID=?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getGenre());
            statement.setString(3, movie.getDirector());
            statement.setString(4, movie.getCast());
            statement.setString(5, movie.getSummary());
            statement.setInt(6, movie.getDuration());
            statement.setInt(7, movie.getYear());
            statement.setInt(8, movie.getMovieID());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteMovie(int movieID) {
        String query = "DELETE FROM Movie WHERE MovieID=?";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, movieID);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM Movie";
        try (Connection conn = SQLServerConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int movieID = resultSet.getInt("MovieID");
                String movieName = resultSet.getString("MovieName");
                String genre = resultSet.getString("Genre");
                String director = resultSet.getString("Director");
                String cast = resultSet.getString("Cast");
                String summary = resultSet.getString("Summary");
                int duration = resultSet.getInt("Duration");
                int year = resultSet.getInt("Year");

                Movie movie = new Movie(movieID, movieName, genre, director, cast, summary, duration, year);
                movies.add(movie);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return movies;
    }
}
