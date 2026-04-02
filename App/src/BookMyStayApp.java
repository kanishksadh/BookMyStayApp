import java.util.*;

// CUSTOM EXCEPTION
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}


// ROOM INVENTORY CLASS
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public boolean isAvailable(String roomType) {
        return inventory.get(roomType) > 0;
    }

    public void reserveRoom(String roomType) throws InvalidBookingException {

        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (!isAvailable(roomType)) {
            throw new InvalidBookingException("Room not available.");
        }

        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}


// VALIDATOR CLASS
class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (!inventory.isAvailable(roomType)) {
            throw new InvalidBookingException("Room not available.");
        }
    }
}


// SIMPLE QUEUE CLASS
class BookingRequestQueue {

    private Queue<String> queue = new LinkedList<>();

    public void addRequest(String request) {
        queue.add(request);
    }

    public void processRequest() {
        if (!queue.isEmpty()) {
            System.out.println("Processing booking request: " + queue.poll());
        }
    }
}


// ✅ MAIN CLASS (Corrected Name)
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {

            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // FAIL-FAST VALIDATION
            validator.validate(guestName, roomType, inventory);

            // RESERVE ROOM
            inventory.reserveRoom(roomType);

            // PROCESS REQUEST
            bookingQueue.addRequest(guestName + " - " + roomType);
            bookingQueue.processRequest();

            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}