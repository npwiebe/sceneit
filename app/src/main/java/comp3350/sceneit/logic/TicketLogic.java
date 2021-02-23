package comp3350.sceneit.logic;

import comp3350.sceneit.data.Movie;

public final class TicketLogic {
    private TicketLogic(){};


    /**
     * Calculates cost of all selected movies in OrderActivity
     * @param numOfTickets
     * @param movie
     * @return cost of all selected movie tickets
     */
    public static int totalOrderPrice(int numOfTickets, Movie movie){
        int price = calculateTicketPrice(movie);
        return numOfTickets * price;
    }

    /**
     * Calculates ticket price given a specific movie
     * @param movie
     * @return $10...... for this iteration all movies cost $10.
     */
    public static int calculateTicketPrice(Movie movie){
        return 10;
    }
}
