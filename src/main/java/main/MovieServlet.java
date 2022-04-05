package main;

import com.google.gson.Gson;
import data.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet(name = "MovieServlet", urlPatterns = "/movies/*")
public class MovieServlet extends HttpServlet {

    ArrayList<Movie> movies = new ArrayList<>();
    int nextID = 1;

    Movie firstMovie = new Movie("The Book of Eli", 5, "https://upload.wikimedia.org/wikipedia/en/e/e3/Book_of_eli_poster.jpg", 2010, "Sci_Fi,Action,Adventure", "Allen Hughes", "A post-apocalyptic tale, in which a lone man fights his way across America in order to protect a sacred book that holds the secrets to saving humankind.", "Denzel Washington,Mila Kunis,Ray Stephenson", 1);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            PrintWriter out = resp.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
            out.println(movieString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        BufferedReader bReader = req.getReader();

        Movie[] newMovies = new Gson().fromJson(bReader, Movie[].class);
        for (Movie movie : newMovies) {
            movie.setId(nextID++);
            movies.add(movie);
        }
        try {
            PrintWriter out = resp.getWriter();
            out.println("Movies added");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader bReader = req.getReader();
        Movie updatedMovie = new Gson().fromJson(bReader, Movie.class);
        resp.setContentType("application/json");
        String[] uriParts = req.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);
        for (Movie movie : movies) {
            if (movie.getId() == targetId) {
                int index = movies.indexOf(movie);
                movies.set(index, updatedMovie);
                PrintWriter out = resp.getWriter();
                out.println("Movie updated");
            }
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
        for (Movie movie : movies) {
            if (movie.getId() == targetId) {
                movies.remove(movieToDelete.getId());
                PrintWriter out = resp.getWriter();
                out.println("Movie Deleted");
            }
        }
    }


}////END OF CLASS
