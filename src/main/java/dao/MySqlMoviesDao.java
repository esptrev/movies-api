package dao;

import com.mysql.cj.jdbc.Driver;
import data.Movie;
import main.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao {

    private final Connection initConnection;

////CONNECTION TO DB HAS TO BE MADE IN THE DAO CONSTRUCTOR////

    public MySqlMoviesDao() {
        try {
            DriverManager.registerDriver(new Driver());

            initConnection = DriverManager.getConnection(
                    "jdbc:mysql://" + Config.DB_Host + ":3306/trevor?allowPublicKeyRetrieval=true&useSSL=false",
                    Config.DB_User, // <-- WHO IS ACCESSING?
                    Config.DB_PW // <-- WHAT IS THEIR PASSWORD?
            );

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!", e);
        }

    }


    @Override
    public List<Movie> all() throws SQLException {
        ArrayList<Movie> moviesAL = new ArrayList<>();
        Statement st = initConnection.createStatement();

        ResultSet rs = st.executeQuery("select * from moviesTable");
        while (rs.next()){
            Movie newMovie = new Movie();
            newMovie.setId(rs.getInt("id"));
            newMovie.setTitle(rs.getString("title"));
            newMovie.setRating(rs.getDouble("rating"));
            newMovie.setYear(rs.getInt("year"));
            newMovie.setGenre(rs.getString("genre"));
            newMovie.setDirector(rs.getString("director"));
            newMovie.setActors(rs.getString("actors"));
            newMovie.setPlot(rs.getString("plot"));
            newMovie.setPosterURL(rs.getString("posterURL"));

            moviesAL.add(newMovie);
        }
        st.close();
        return moviesAL;
    }

    @Override
    public Movie findOne(int id) throws SQLException {
        Statement st = initConnection.createStatement();

        ResultSet rs = st.executeQuery("select * from moviesTable where id = ?");
        Movie newMovie = null;
        while (rs.next()) {
            newMovie = new Movie();
            newMovie.setId(rs.getInt("id"));
            newMovie.setTitle(rs.getString("title"));
            newMovie.setRating(rs.getDouble("rating"));
            newMovie.setYear(rs.getInt("year"));
            newMovie.setGenre(rs.getString("genre"));
            newMovie.setDirector(rs.getString("director"));
            newMovie.setActors(rs.getString("actors"));
            newMovie.setPlot(rs.getString("plot"));
            newMovie.setPosterURL(rs.getString("posterURL"));

        }
        st.close();
        System.out.println(newMovie);
        return newMovie;

    }

    @Override
    public void insert(Movie movie) {
       String insertSQL = "insert into moviesTable (title,rating,year,genre,director,actors,plot,posterURL) values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement psInsert = initConnection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, movie.getTitle());
            psInsert.setDouble(2, movie.getRating());
            psInsert.setInt(3, movie.getYear());
            psInsert.setString(4, movie.getGenre());
            psInsert.setString(5, movie.getDirector());
            psInsert.setString(6, movie.getActors());
            psInsert.setString(7, movie.getPlot());
            psInsert.setString(8, movie.getPosterURL());
            psInsert.executeUpdate();

//            ResultSet keys = psInsert.getGeneratedKeys();
//            keys.next();
//            int newID = keys.getInt(0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAll(Movie[] movies) throws SQLException {
        for (Movie m: movies) {
            insert(m);
        }
    }

    @Override
    public void update(Movie movie) throws SQLException {
        //TODO: Update a movie here!
    }

    @Override
    public void delete(int id) throws SQLException {
        //TODO: Annihilate a movie
    }

    public void cleanUp(){
        try {
            initConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

