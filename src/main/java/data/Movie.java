package data;

public class Movie {
    private String title;
    private Double rating;
    private String posterURL;
    private Integer year;
    private String genre;
    private String director;
    private String plot;
    private String actors;
    private int id;


    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", posterURL='" + posterURL + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", plot='" + plot + '\'' +
                ", actors='" + actors + '\'' +
                ", id=" + id +
                '}';
    }

    public Movie() {
    }

    public Movie(int id, String title, Double rating, Integer year, String genre, String director, String actors, String plot, String posterURL ) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.posterURL = posterURL;
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

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
