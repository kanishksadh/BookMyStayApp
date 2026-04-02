import java.io.*;
import java.util.*;


// CLASS: Reservation (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    @Override
    public String toString() {
        return reservationId + " - " + guestName + " - " + roomType;
    }
}


// CLASS: RoomInventory (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void bookRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}


// CLASS: BookingHistory (Serializable)
class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void display() {
        System.out.println("Booking History:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}


// CLASS: PersistenceService
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // SAVE STATE
    public void save(RoomInventory inventory, BookingHistory history) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // LOAD STATE
    public Object[] load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();

            System.out.println("System state loaded successfully.");
            return new Object[]{inventory, history};

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh...");
            return null;
        }
    }
}


// ✅ MAIN CLASS
public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        RoomInventory inventory;
        BookingHistory history;

        // LOAD STATE (Recovery)
        Object[] data = persistence.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (BookingHistory) data[1];
        } else {
            inventory = new RoomInventory();
            history = new BookingHistory();
        }

        // Display current state
        System.out.println("\n--- CURRENT STATE ---");
        inventory.display();
        history.display();

        // Simulate new booking
        System.out.println("\nAdding new booking...");

        Reservation r = new Reservation("RES" + new Random().nextInt(1000),
                "Guest", "Single");

        history.addReservation(r);
        inventory.bookRoom("Single");

        // Display updated state
        System.out.println("\n--- UPDATED STATE ---");
        inventory.display();
        history.display();

        // SAVE STATE before shutdown
        persistence.save(inventory, history);

        System.out.println("\nRestart the program to see recovery in action.");
    }
}