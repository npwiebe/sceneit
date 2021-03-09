package comp3350.sceneit.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import comp3350.sceneit.data.exceptions.AiringNotFoundException;
import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.data.exceptions.MovieNotFoundException;
import comp3350.sceneit.data.exceptions.TheatreNotFoundException;

public class PostgresDatabaseManager implements DatabaseManager {

    // NOTE: These are RO credentials. When we implement users later, this will likely change to
    // allow authorised create/update/delete.
    private final String driver = "org.postgresql.Driver";
    private final String user = "rouser";
    private final String pass = "AGQLzuqwKFbDWh9i";
    private final String connection_string = "jdbc:postgresql://db.sceneit.linney.dev:5433/sceneit";
    private Connection connection;
    private boolean status;

    public PostgresDatabaseManager() {
        connect();
    }

    private void connect() {
        Thread thread = new Thread(() -> {
            try {
                Class.forName(this.driver);
                this.connection = DriverManager.getConnection(connection_string, user, pass);
            } catch (Exception e) {
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Airing getAiringFromResultSet(ResultSet rs) throws SQLException {
        return new Airing(
                rs.getInt("airings_id"),
                rs.getInt("movie_id"),
                rs.getInt("theatre_id"),
                rs.getDate("air_time"),
                rs.getInt("total_seats"),
                rs.getInt("available_seats"));
    }

    private Movie getMovieFromResultSet(ResultSet rs) throws SQLException {
        return new Movie(
                rs.getInt("movie_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("rating"),
                rs.getString("poster_url"));
    }

    private Theatre getTheatreFromResultSet(ResultSet rs) throws SQLException {
        return new Theatre(
                rs.getInt("theatre_id"),
                rs.getString("name"),
                rs.getString("location"));
    }

    @Override
    public ArrayList<Movie> getMovies() throws DatabaseAccessException {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from movies;");
            while (rs.next()) {
                movies.add(getMovieFromResultSet(rs));
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseAccessException();
        }

        return movies;
    }

    @Override
    public ArrayList<Theatre> getTheatres() throws DatabaseAccessException {
        ArrayList<Theatre> theatres = new ArrayList<>();

        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from theatres;");
            while (rs.next()) {
                theatres.add(getTheatreFromResultSet(rs));
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseAccessException();
        }

        return theatres;
    }

    @Override
    public ArrayList<Airing> getAirings(int movie_id) throws DatabaseAccessException {
        ArrayList<Airing> airings = new ArrayList<>();

        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    String.format(
                            "select * from airings where movie_id = %s;",
                            movie_id
                    )
            );

            while (rs.next()) {
                airings.add(getAiringFromResultSet(rs));
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseAccessException();
        }

        return airings;
    }

    @Override
    public Movie getMovie(int movie_id) throws DatabaseAccessException, MovieNotFoundException {
        Movie result;
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    String.format(
                            "select * from movies where movie_id = %d;",
                            movie_id
                    )
            );
            if (rs.next()) {
                result = getMovieFromResultSet(rs);
            } else {
                throw new MovieNotFoundException();
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseAccessException();
        }

        return result;
    }

    @Override
    public Theatre getTheatre(int theatre_id) throws DatabaseAccessException, TheatreNotFoundException {
        Theatre result;
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    String.format(
                            "select * from theatres where theatre_id = %d;",
                            theatre_id
                    )
            );
            if (rs.next()) {
                result = getTheatreFromResultSet(rs);
            } else {
                throw new TheatreNotFoundException();
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseAccessException();
        }

        return result;
    }

    @Override
    public Airing getAiring(int airing_id) throws DatabaseAccessException, AiringNotFoundException {
        Airing result;
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    String.format(
                            "select * from airings where airings_id = %d;",
                            airing_id
                    )
            );
            if (rs.next()) {
                result = getAiringFromResultSet(rs);
            } else {
                throw new AiringNotFoundException();
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseAccessException();
        }

        return result;
    }
}