package data;

public class Movie {
    private String title;
    private double rating;
    private String posterURL;
    private int yearReleased;
    private String genre;
    private String director;
    private String plot;
    private String actors;
    private int id;


    public Movie() {

    }

    public Movie(String title, float rating, String posterURL, int yearReleased, String genre, String director, String plot, String actors, int id) {
        this.title = title;
        this.rating = rating;
        this.posterURL = posterURL;
        this.yearReleased = yearReleased;
        this.genre = genre;
        this.director = director;
        this.plot = plot;
        this.actors = actors;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
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

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }


}////END OF CLASS
