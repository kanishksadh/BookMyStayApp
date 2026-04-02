import java.util.*;


// CLASS: Reservation
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void cancel() {
        isActive = false;
    }
}


// CLASS: RoomInventory
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}


// CLASS: BookingHistory
class BookingHistory {

    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }
}


// CLASS: CancellationService
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        Reservation reservation = history.getReservation(reservationId);

        // VALIDATION
        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }

        if (!reservation.isActive()) {
            System.out.println("Cancellation failed: Already cancelled.");
            return;
        }

        // ROLLBACK PROCESS (LIFO)
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.increment(reservation.getRoomType());

        // Mark reservation cancelled
        reservation.cancel();

        System.out.println("Booking cancelled successfully.");
        System.out.println("Released Room ID: " + rollbackStack.pop());
    }
}


// ✅ MAIN CLASS (as required)
public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize system
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("RES101", "Single", "S1");
        Reservation r2 = new Reservation("RES102", "Double", "D1");

        history.addReservation(r1);
        history.addReservation(r2);

        // Reduce inventory (simulate allocation)
        inventory.decrement("Single");
        inventory.decrement("Double");

        System.out.println("Before Cancellation:");
        inventory.displayInventory();

        // Perform cancellation
        System.out.println("\nCancelling RES101...");
        cancellationService.cancelBooking("RES101", history, inventory);

        System.out.println("\nAfter Cancellation:");
        inventory.displayInventory();

        // Try invalid cancellation
        System.out.println("\nCancelling RES999...");
        cancellationService.cancelBooking("RES999", history, inventory);

        // Try duplicate cancellation
        System.out.println("\nCancelling RES101 again...");
        cancellationService.cancelBooking("RES101", history, inventory);
    }
}