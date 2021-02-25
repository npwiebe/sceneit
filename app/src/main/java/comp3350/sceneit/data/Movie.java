package comp3350.sceneit.data;

public class Movie {
    int movieId;
    String title;
    String description;
    int rating;
    String poster_url;

    /**
     * @return The rating of the movie out of 100. Ex. 75
     */
    public int getRating() {
        return rating;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Movie(int movieId, String title, String description, int rating, String poster_url) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.poster_url = poster_url;
    }
}
