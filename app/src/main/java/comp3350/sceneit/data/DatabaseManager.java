package comp3350.sceneit.data;

import java.util.ArrayList;

/**
 * The interface for accessing a database.
 *
 * @see PostgresDatabaseManager
 */
public interface DatabaseManager {

    //<editor-fold desc="List Access Methods">

    /**
     * Get the list of movies in the DB
     *
     * @return The list af movies in the DB
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Movie
     */
    ArrayList<Movie> getMovies() throws DatabaseAccessException;

    /**
     * Get the list of theatres in the DB.
     *
     * @return the list of theatres in the DB
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Theatre
     */
    ArrayList<Theatre> getTheatres() throws DatabaseAccessException;

    /**
     * Get the list of airings for a given movie.
     *
     * @param movie_id The ID of the movie whose airings we want.
     * @return The list of airings for a given movie
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Movie
     * @see Airing
     */
    ArrayList<Airing> getAirings(int movie_id) throws DatabaseAccessException;

    /**
     * @param movie The movie to retrieve airings for.
     * @return The airings of the given movie.
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @see Movie
     * @see Airing
     */
    default ArrayList<Airing> getAirings(Movie movie) throws DatabaseAccessException {
        return this.getAirings(movie.getMovieId());
    }
    //</editor-fold desc="List Access Methods">


    //<editor-fold desc="Single Access Methods">

    /**
     * Retrieves a movie based on its ID.
     *
     * @param movie_id The ID of the movie to retrieve.
     * @return The movie with the given the ID.
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @throws MovieNotFoundException  if the movie is not found in the DB.
     * @see Movie
     */
    Movie getMovie(int movie_id) throws DatabaseAccessException, MovieNotFoundException;

    /**
     * @param theatre_id The ID of the theatre to retrieve.
     * @return The theatre with the given ID.
     * @throws DatabaseAccessException  in the case the database fails to be accessed.
     * @throws TheatreNotFoundException in the case the theatre is not found in the DB.
     * @see Theatre
     */
    Theatre getTheatre(int theatre_id) throws DatabaseAccessException, TheatreNotFoundException;

    /**
     * @param airing_id The ID of the airing to retrieve.
     * @return The airing with the given ID
     * @throws DatabaseAccessException in the case the database fails to be accessed.
     * @throws AiringNotFoundException if the airing is not found in the DB.
     * @see Airing
     */
    Airing getAiring(int airing_id) throws DatabaseAccessException, AiringNotFoundException;
    //</editor-fold>

}
