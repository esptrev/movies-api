package main;

import com.mysql.cj.jdbc.Driver;
import dao.MoviesDao;
import data.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieJDBC implements MoviesDao {

    static Connection initConn;
    {
        try {
            initConn = DriverManager.getConnection(
                    "jdbc:mysql://" + Config.DB_Host + ":3306/trevor?allowPublicKeyRetrieval=true&useSSL=false",
                    Config.DB_User,
                    Config.DB_PW
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new Driver());

//        viewMoviesTable(initConn);
//        insertMovie(firstMovie);
//        updateMovie();
//        deleteMovie();

//        Statement st = connection.createStatement();
//        ResultSet movieRows = st.executeQuery("select title,rating,posterURL,year,genre,director,plot,actors from movies");
//        /// iterates over rows and prints fields for each
//        while (movieRows.next()) {
//            System.out.printf("%s ", movieRows.getString("title"));
//            System.out.printf("%s ", movieRows.getInt("year"));
//            System.out.println(movieRows.getString("plot"));
//        }

//        movieRows = st.executeQuery("select count(id) from movies");
//        movieRows.next();
//        System.out.println("row count via count function " + movieRows.getInt("count(id)"));



//        String newMovie = "CODA";
//        int newYear = 2021;
//        String newPlot = "As a CODA (Child of Deaf Adults) Ruby is the only hearing person in her deaf family. When the family's fishing business is threatened, Ruby finds herself torn between pursuing her love of music by wanting to go to Berklee College of Music and her fear of abandoning her parents.";
//        String newPoster = "https://upload.wikimedia.org/wikipedia/en/8/89/Coda_poster.jpeg ";
//
                //PARAMATERIZED QUERY === PREPARED STATEMENTS////
//        PreparedStatement ps = initConn.prepareStatement("insert into movies (title,year,plot,posterURL) values(?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
//        ps.setString(1, newMovie);
//        ps.setInt(2,newYear);
//        ps.setString(3,newPlot);
//        ps.setString(4,newPoster);
//        ps.executeUpdate();


//        ResultSet newKeys = ps.getGeneratedKeys();
//        newKeys.next();
//        int newID = newKeys.getInt(1);
//        System.out.println("new record id is " + newID);

        String updatePlot = "";
        String updateDirector = "";
        String updatePoster = "";
        String updateGenre = "Comedy, Drama, Music";
        int updateYear = 2000;
        int updateRating = 4;


//        ps = initConn.prepareStatement("update movies set rating = ?,genre = ? where id = ?");
//        ps.setInt(1,updateYear);
//        ps.setInt(1,updateRating);
//        ps.setString(2,updateGenre);
//        ps.setString(3,updateDirector);
//        ps.setInt(3,newID);
//        ps.executeUpdate();



//        ps.close();
//        newKeys.close();
//        movieRows.close();
//        st.close();
        initConn.close();
    }/////END OF MAIN


//    public static void viewMoviesTable(Connection conn) {
//        String query = "select title,rating,posterURL,year,genre,director,plot,actors from movies";
//        try (Statement st = conn.createStatement()){
//            ResultSet movieRows = st.executeQuery(query);
//            while (movieRows.next()){
//                String movieTitle = movieRows.getString("title");
//                int movieRating = movieRows.getInt("rating");
//                String moviePoster = movieRows.getString("posterURL");
//                int movieYear = movieRows.getInt("year");
//                String movieGenre = movieRows.getString("genre");
//                String movieDirector = movieRows.getString("director");
//                String moviePlot = movieRows.getString("plot");
//                String movieActors = movieRows.getString("actors");
//                System.out.println(movieTitle + movieRating + moviePoster + movieYear + movieGenre + movieDirector + moviePlot + movieActors);
//
//            }
//            movieRows.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }



    public List<Movie> all() throws SQLException {

        Statement statement = initConn.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM moviesTable");

        List<Movie> movies = new ArrayList<>();

        while (rs.next()) {
            movies.add(new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getDouble("rating"),
                    rs.getInt("year"),
                    rs.getString("genre"),
                    rs.getString("director"),
                    rs.getString("actors"),
                    rs.getString("plot"),
                    rs.getString("poster")
            ));
        }
        return movies;
    }

    @Override
    public Movie findOne(int id) {
        return null;
    }

    @Override
    public void insert(Movie movie) {

    }

    @Override
    public void insertAll(Movie[] movies) throws SQLException {

    }

    @Override
    public void update(Movie movie) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void cleanUp() {

    }


    public static void insertMovie(Movie mn){
        String insertQue = "insert into moviesTable (title,rating,year,genre,director,actors,plot,poster) values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = initConn.prepareStatement(insertQue, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,mn.getTitle());
            ps.setDouble(2, mn.getRating());
            ps.setInt(3, mn.getYear());
            ps.setString(4, mn.getGenre());
            ps.setString(5, mn.getDirector());
            ps.setString(6, mn.getActors());
            ps.setString(7, mn.getPlot());
            ps.setString(8, mn.getPoster());
            ps.executeUpdate();

            ResultSet newKeys = ps.getGeneratedKeys();
            newKeys.next();
            int newID = newKeys.getInt(1);
            System.out.println("new record id is " + newID);

            ps.close();
            newKeys.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateMovie(Connection conn){
        String insertQue = "update moviesTable  "
                + "set title = ?,rating = ?,year = ?,genre = ?,director = ?,actors = ?,plot = ?,poster = ?"
                + " where id = 15";
        try (PreparedStatement ps = conn.prepareStatement(insertQue)) {
            ps.setString(1, "Geronimo");
            ps.setInt(2, Integer.parseInt("5"));
            ps.setInt(3, Integer.parseInt("1993"));
            ps.setString(4, "Western, Drama, History");
            ps.setString(5, "director");
            ps.setString(6, "actors");
            ps.setString(7, "plot");
            ps.setString(8, "poster");
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie(Connection conn){
        String deleteQue = "DELETE FROM moviesTable WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteQue)){
           ps.setInt(1,15);
            int movieIdToDel = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }





}/////END OF CLASS
