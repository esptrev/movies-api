package dao;

import data.Movie;

import java.sql.SQLException;
import java.util.List;

public interface MoviesDao {

    List<Movie> all() throws SQLException;

    Movie findOne(int id) throws SQLException;

    void insert(Movie movie);

    void insertAll(Movie[] movies) throws SQLException;

    void update(Movie movie) throws SQLException;

    void delete(int id) throws SQLException;

    void cleanUp();
}

