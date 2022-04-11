package main;

import com.google.gson.Gson;
import dao.MoviesDao;
import data.Movie;
import data.MoviesDaoFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")
public class MovieServlet extends HttpServlet {

    //    ArrayList<Movie> movies = new ArrayList<>();
    private int nextID = 1;

    MoviesDao dao = MoviesDaoFactory.getMoviesDao(MoviesDaoFactory.DAOType.MYSQL);


//    Movie firstMovie = new Movie("The Book of Eli", 5.0, "https://upload.wikimedia.org/wikipedia/en/e/e3/Book_of_eli_poster.jpg", 2010, "Sci_Fi,Action,Adventure", "Allen Hughes", "A post-apocalyptic tale, in which a lone man fights his way across America in order to protect a sacred book that holds the secrets to saving humankind.", "Denzel Washington,Mila Kunis,Ray Stephenson", 1);


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("application/json");
//        try {
//            PrintWriter out = resp.getWriter();
//            String movieString = new Gson().toJson(movies.toArray());
//            out.println(movieString);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    //// REFACTORED SERVLET FOR DAO  LINE WITH MOVIE STRING IS ONLY CHANGE HERE
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Movie movieRequested = new Gson().fromJson(req.getReader(), Movie.class);
        resp.setContentType("application/json");

        String[] uriParts = req.getRequestURI().split("/");
        String targetId = uriParts[uriParts.length - 1];
        resp.setContentType("application/json");
        if(isNumeric(targetId)){
            int numericID = Integer.parseInt(uriParts[uriParts.length - 1]);
            try {
                PrintWriter out = resp.getWriter();
               String oneMovie = new Gson().toJson(dao.findOne(numericID));
                out.println(oneMovie);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            try {
                PrintWriter out = resp.getWriter();
                String movieString = new Gson().toJson(dao.all().toArray());
                out.println(movieString);

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isNumeric(String aString) {
        try {
            double d = Double.parseDouble(aString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("application/json");
//
//        BufferedReader bReader = req.getReader();
//        Movie[] newMovies = new Gson().fromJson(bReader, Movie[].class);
//
//
//        for (Movie movie : newMovies) {
//            movie.setId(nextID++);
//            movies.add(movie);
//        }
//        try {
//            PrintWriter out = resp.getWriter();
//            out.println("Movies added");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        BufferedReader bReader = req.getReader();
        Movie[] newMovies = new Gson().fromJson(bReader, Movie[].class);

        try {
            dao.insertAll(newMovies);
            PrintWriter out = resp.getWriter();
            out.println("Movies added");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader bReader = req.getReader();
        Movie updatedMovie = new Gson().fromJson(bReader, Movie.class);

        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);

        try {
            updatedMovie.setId(targetId);
            dao.update(updatedMovie);
            resp.setContentType("application/json");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        BufferedReader bReader = req.getReader();

        Movie movieToDelete = new Gson().fromJson(bReader, Movie.class);
        resp.setContentType("application/json");

        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);

        PrintWriter out = resp.getWriter();
        out.println("Movie Deleted");

        try {
            dao.delete(targetId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        dao.cleanUp();
        super.destroy();
    }
}////END OF CLASS
