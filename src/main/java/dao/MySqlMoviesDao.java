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
            newMovie.setPoster(rs.getString("poster"));

            moviesAL.add(newMovie);
        }
        st.close();
        return moviesAL;
    }

    @Override
    public Movie findOne(int idd) throws SQLException {
        String findSQL = "select * from moviesTable where id = ?";

        Movie newMovie = null;

        try {
            PreparedStatement pst = initConnection.prepareStatement(findSQL);
            pst.setInt(1, idd);
            ResultSet rs = pst.executeQuery();

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
                newMovie.setPoster(rs.getString("poster"));

            }
            rs.close();
            pst.close();
            return newMovie;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Movie();
    }

    @Override
    public void insert(Movie movie) {
       String insertSQL = "insert into moviesTable (title,rating,year,genre,director,actors,plot,poster) values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement psInsert = initConnection.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, movie.getTitle());
            psInsert.setDouble(2, movie.getRating());
            psInsert.setInt(3, movie.getYear());
            psInsert.setString(4, movie.getGenre());
            psInsert.setString(5, movie.getDirector());
            psInsert.setString(6, movie.getActors());
            psInsert.setString(7, movie.getPlot());
            psInsert.setString(8, movie.getPoster());
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

    private void updateMovie(Movie movie) throws SQLException{
        String sql = "update moviesTable " +
                "set title = ?, " +
                "rating = ?, " +
                "year = ?, " +
                "genre = ?, " +
                "director= ?, " +
                "actors = ?, " +
                "plot = ?, " +
                "poster= ? " +
                "where id = ?";

        PreparedStatement pstm = initConnection.prepareStatement(sql);
                pstm.setString(1,movie.getTitle());
                pstm.setInt(2,movie.getYear());
                pstm.setDouble(3,movie.getRating());
                pstm.setString(4,movie.getGenre());
                pstm.setString(5,movie.getDirector());
                pstm.setString(6,movie.getActors());
                pstm.setString(7,movie.getPlot());
                pstm.setString(8,movie.getPoster());
                pstm.setInt(9,movie.getId());

                pstm.executeUpdate();
    }

    @Override
    public void update(Movie movie) throws SQLException {
        try {

            ///fetch original from DB
           Movie movieToChange = findOne(movie.getId());
           ////merging changes with original movie
                if (movie.getTitle() != null) {
                    movieToChange.setTitle(movie.getTitle());
                }
                if (movie.getRating() != null) {
                    movieToChange.setRating(movie.getRating());
                }
                if (movie.getPoster() != null) {
                    movieToChange.setPoster(movie.getPoster());
                }
                if (movie.getYear() != null) {
                    movieToChange.setYear(movie.getYear());
                }
                if (movie.getGenre() != null) {
                    movieToChange.setGenre(movie.getGenre());
                }
                if (movie.getDirector() != null) {
                    movieToChange.setDirector(movie.getDirector());
                }
                if (movie.getPlot() != null) {
                    movieToChange.setPlot(movie.getPlot());
                }
                if (movie.getActors() != null) {
                    movieToChange.setActors(movie.getActors());
                }
                updateMovie(movieToChange);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

        @Override
    public void delete(int id) throws SQLException {
        PreparedStatement st = initConnection.prepareStatement("delete from trevor.moviesTable where id = ?");
        st.setInt(1,id);
        st.executeUpdate();
    }

    public void cleanUp(){
        try {
            initConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

