package service;

import java.util.List;
import dao.MovieDao;
import model.Movie;

public class MovieService {
    private MovieDao movieDao;

    public MovieService() {
        this.movieDao = new MovieDao();
    }

    public boolean addMovie(Movie movie) {
        return movieDao.addMovie(movie);
    }

    public boolean updateMovie(Movie movie) {
        return movieDao.updateMovie(movie);
    }

    public boolean deleteMovie(int movieID) {
        return movieDao.deleteMovie(movieID);
    }

    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }
}
