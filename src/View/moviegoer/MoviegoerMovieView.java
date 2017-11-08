package View.moviegoer;

import Controller.CineplexManager;
import Model.Movie;
import Model.Seat;
import Model.Showtime;

import java.util.*;

/**
 * Created by LiuMingyu on 6/11/17.
 * This is the interface when user wants to see movie listing.
 */

public class MoviegoerMovieView {
    Scanner sc;

    public MoviegoerMovieView() {
        sc = new Scanner(System.in);
        displayMenu();
    }

    private void displayMenu() {
        int choice = -1;
        while (choice != 5) {
            System.out.println("---Search or list movies---");
            System.out.println("1. Search movies");
            System.out.println("2. List all movies");
            System.out.println("3. List the top 5 movies by ticket sales");
            System.out.println("4. List the top 5 movies by overall ratings");
            System.out.println("5. Go back");
            try {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        displayMovieListing(0);
                        break;
                    case 3:
                        displayMovieListing(1);
                        break;
                    case 4:
                        displayMovieListing(2);
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid selection, try again:");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid selection, try again:");
                sc.nextLine();  // to flush the buffer
            }
        }

    }

    private void displayMovieListing(int option) {
        ArrayList<Movie> movieListing;
        if (option == 0) {  // list all movies
            movieListing = CineplexManager.getMovieListing();

            System.out.println("---Movies---");
            if (movieListing == null) {
                System.out.println("Movie listing is not available");
                return;
            }

            int index = 0;

            for (Movie movie : movieListing) {
                System.out.println(++index + ". " + movie.getTitle() + " (" + movie.getStatus().toString() + ")");
            }
            System.out.println(index + 1 + ". Go back");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            if (choice == index + 1) return;
            else displayMovieDetailMenu(movieListing.get(index - 1));
        }
    }

    private void displayMovieDetailMenu(Movie movie) {
        System.out.println("---Movie details---");
        System.out.println(movie);

        int choice = -1;
        while (choice != 4) {
            System.out.println("1. Display showtime");
            System.out.println("2. Display reviews");
            System.out.println("3. Write reviews");
            System.out.println("4. Go back");
            choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1:
                        // TODO movie.getShowtime()
                        displayShowtimeMenu(movie);
                        break;
                    case 2:
                        // TODO movie.getReview()
                        break;
                    case 3:
                        // TODO writeReview()
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Invalid selection, try again:");
                        break;
                }
            }   catch (InputMismatchException ex) {
                System.out.println("Invalid selection, try again:");
                sc.nextLine();  // to flush the buffer
            }
        }
    }

    private void displayShowtimeMenu(Movie movie) {
        ArrayList<Showtime> showtimeList = CineplexManager.getMovieShowtime(movie);
        Collections.sort(showtimeList, new Comparator<Showtime>() {
            @Override
            public int compare(Showtime o1, Showtime o2) {
                return o1.getCinema().getCineplex().toString().compareTo(o2.getCinema().getCineplex().toString());
            }
        });

        int index = 0;
        for (Showtime s : showtimeList) System.out.println(++index + ": " + s);

        System.out.println("Please choose a showtime (enter 0 to go back):");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice == 0) return;

        Showtime showtime = showtimeList.get(choice - 1);
        displayShowtimeDetailMenu(showtime);

    }

    private void displayShowtimeDetailMenu(Showtime showtime) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 3) {
            System.out.println("---" + showtime + "---");
            System.out.println("1. Check seat availability");
            System.out.println("2. Book seat");
            System.out.println("3. Go back");
            try {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        displaySeat(showtime.getSeats());
                        break;
                    case 2:
                        displayBookSeatMenu(showtime);
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Invalid selection.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid selection.");
                sc.nextLine();
                continue;
            }
        }
    }

    private void displaySeat(Seat[][] seats) {
        System.out.println("                    -------Screen------");
        System.out.println("     1  2  3  4  5  6  7  8     9 10 11 12 13 14 15 16");
        seats[4][3].bookSeat();
        for (int row = 0; row <= 8; row++) {
            System.out.print(row + 1 + "   ");
            for (int col = 0; col <= 16; col++) {
                if (seats[row][col] == null) System.out.print("   ");
                else System.out.print(seats[row][col]);
            }
            System.out.println();
        }
    }

    private void displayBookSeatMenu(Showtime showtime) {
        int row, col;

        System.out.println("Enter the row (1 - 9) of the seat:");
        try {
            row = sc.nextInt();
        }
        catch (InputMismatchException ex) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }
        System.out.println("Enter the column (1 - 16) of the seat: ");
        try {
            col = sc.nextInt();
        }
        catch (InputMismatchException ex) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return;
        }

        if (showtime.getSeatAt(row, col) == null) {
            System.out.println("No such seat. Please choose another one.");
            displayBookSeatMenu(showtime);
            return;
        }
        else if (showtime.getSeatAt(row, col).isBooked()) {
            System.out.println("The seat has been booked. Please choose another one.");
            displayBookSeatMenu(showtime);
            return;
        }
        else {
            // TODO BookingManager
        }
    }
}