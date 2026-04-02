import java.util.*;

// CLASS: Reservation
// Represents a confirmed booking
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights;
    }
}


// CLASS: BookingHistory
// Stores confirmed reservations in insertion order
class BookingHistory {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve all bookings
    public List<Reservation> getAllReservations() {
        return reservations;
    }
}


// CLASS: BookingReportService
// Generates reports from booking history
class BookingReportService {

    public void generateReport(BookingHistory history) {

        List<Reservation> reservations = history.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("===== BOOKING REPORT =====");

        int totalBookings = reservations.size();
        int totalNights = 0;

        for (Reservation r : reservations) {
            System.out.println(r);
            totalNights += r.getNights();
        }

        System.out.println("--------------------------");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);
    }
}


// MAIN CLASS
public class BookMyStayApp {

    public static void main(String[] args) {

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("RES101", "Alice", "Deluxe", 2);
        Reservation r2 = new Reservation("RES102", "Bob", "Suite", 3);
        Reservation r3 = new Reservation("RES103", "Charlie", "Standard", 1);

        // Add to history (in order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin generates report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}