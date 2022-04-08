package dao;

import data.Movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMoviesDao implements MoviesDao {

    private ArrayList<Movie> movies = new ArrayList<>();
    private int nextID = 1;

    @Override
    public List<Movie> all() throws SQLException {
        return movies;
    }

    @Override
    public Movie findOne(int id) {
        return movies != null ? movies.get(id) : null;
    }



    @Override
    public void insertAll(Movie[] moviesN) throws SQLException {
        for (Movie movie : moviesN) {
            movie.setId(nextID++);
            movies.add(movie);
        }
    }

    @Override
    public void update(Movie udMovie) throws SQLException {
        for (Movie movie : movies) {
            if (movie.getId() == udMovie.getId()) {
                if (udMovie.getTitle() != null) {
                    movie.setTitle(udMovie.getTitle());
                }
                if (udMovie.getRating() != null) {
                    movie.setRating(udMovie.getRating());
                }
                if (udMovie.getPosterURL() != null) {
                    movie.setPosterURL(udMovie.getPosterURL());
                }
                if (udMovie.getYear() != null) {
                    movie.setYear(udMovie.getYear());
                }
                if (udMovie.getGenre() != null) {
                    movie.setGenre(udMovie.getGenre());
                }
                if (udMovie.getDirector() != null) {
                    movie.setDirector(udMovie.getDirector());
                }
                if (udMovie.getPlot() != null) {
                    movie.setPlot(udMovie.getPlot());
                }
                if (udMovie.getActors() != null) {
                    movie.setActors(udMovie.getActors());
                }
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Movie foundMovie = null;
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                foundMovie = movie;
                break;
            }
        }
        if (foundMovie != null) {
            movies.remove(foundMovie);
        }
    }

    @Override
    public void insert(Movie movie) {
        movie.setId(nextID++);
        int newId = movie.getId();
        movies.add(newId, movie);
    }
//
//    private HashMap<Integer, Movie> getMoviesMap() {
//        try {
//            Reader reader = Files.newBufferedReader(Paths.get("/Users/vegetasrevenge/IdeaProjects/movies-backend/src/main/resources/movies.json"));
//            Type type = TypeToken.getParameterized(ArrayList.class, Movie.class).getType();
//            return getMoviesMap(new Gson().fromJson(reader, type));
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    private HashMap<Integer, Movie> getMoviesMap(List<Movie> movies) {
//        HashMap<Integer, Movie> movieHashMap = new HashMap<>();
//        int counter = 1;
//        for (Movie movie : movies) {
//            movieHashMap.put(counter, movie);
//            movie.setId(counter);
//            counter++;
//        }
//        return movieHashMap;
//    }

}

