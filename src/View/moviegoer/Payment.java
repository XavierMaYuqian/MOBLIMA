package View.moviegoer;

import Controller.CineplexManager;
import Model.BookingHistory;
import Model.Customer;
import Model.Movie;
import Model.Seat;
import View.View;

import static Controller.IOController.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents te payment view.
 *
 * @version 1.0
 */
public class Payment extends View {
    private Seat seat;
    private Customer customer;
    private String TID;
    private double basePrice;
    private double GST;
    private double totalPrice;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void start() {
        displayMenu();
    }

    /**
     * Allocates a {@code Payment} object and initializes it specified with customer, seat and base price.
     * @param customer the customer who booked the seat
     * @param seat the seat to be booked
     * @param basePrice the base price of the seat
     */
    Payment(Customer customer, Seat seat, double basePrice) {
        this.customer = customer;
        this.seat = seat;
        this.basePrice = basePrice;
        generateTID();
        computeTotalPrice();
    }

    /**
     * This method is to generate the TID based on current time.
     */
    private void generateTID() {
        TID = seat.getShowtime().getCinema().getCode() +
                new SimpleDateFormat("YYYYMMddhhmm").format(new Date().getTime())
        ;
    }

    /**
     * This method is to compute the total price.
     */
    private void computeTotalPrice() {
        if (customer.isSeniorCitizen()) basePrice /= 2;
        GST = round((basePrice + 2) * 0.07, 2);
        totalPrice = round(basePrice + 2 + GST, 2);
    }

    /**
     * This method is to display the main menu of payment.
     */
    private void displayMenu() {
        printHeader("Payment");
        printMenu("Transaction ID: " + TID,
                "Ticket price: " + basePrice,
                "Booking fee: 2.0",
                "GST: " + GST,
                "Grand total: " + totalPrice,
                "");
        if (customer.isSeniorCitizen()) {
            printMenu("(50% off for senior citizen)", "");
        }

        printMenu("1. Confirm payment",
                "2. Cancel",
                "");

        int choice = readChoice(1, 2);
        switch (choice) {
            case 1:
                logBooking();
                break;
            case 2:
                destroy();
                break;
        }
    }

    /**
     * This method is to log booking into booking history.
     */
    private void logBooking() {
        try {
            seat.bookSeat();
            Movie movie = seat.getShowtime().getMovie();
            CineplexManager.getMovieListing().get(CineplexManager.getMovieListing().indexOf(movie)).incrementSales();
            BookingHistory record = new BookingHistory(TID, customer, seat);
            CineplexManager.logBooking(record);
            CineplexManager.updateShowtime();
            CineplexManager.updateMovieListing();
            System.out.println("Payment has been made. We wish you a great day!");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Payment failed.");
        }

        destroy();
    }
}
