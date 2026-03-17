import java.util.HashMap;

/**
 * Book My Stay App
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates:
 * - Abstraction & Inheritance
 * - HashMap for centralized inventory
 * - Encapsulation of inventory logic
 * - Single source of truth
 *
 * All implemented in a single file.
 *
 * @author Developer
 * @version 3.1
 */
public class BookMyStayApp {

    // 🔹 Abstract Room Class
    static abstract class Room {
        protected int numberOfBeds;
        protected int squareFeet;
        protected double pricePerNight;

        public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
            this.numberOfBeds = numberOfBeds;
            this.squareFeet = squareFeet;
            this.pricePerNight = pricePerNight;
        }

        public void displayRoomDetails() {
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + squareFeet + " sqft");
            System.out.println("Price per night: " + pricePerNight);
        }
    }

    // 🔹 Room Types
    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 250, 1500.0);
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 400, 2500.0);
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 750, 5000.0);
        }
    }

    // 🔹 Centralized Inventory Class
    static class RoomInventory {

        private HashMap<String, Integer> availability;

        // Constructor initializes inventory
        public RoomInventory() {
            availability = new HashMap<>();

            availability.put("Single Room", 5);
            availability.put("Double Room", 3);
            availability.put("Suite Room", 2);
        }

        // Get availability
        public int getAvailability(String roomType) {
            return availability.getOrDefault(roomType, 0);
        }

        // Update availability
        public void updateAvailability(String roomType, int count) {
            availability.put(roomType, count);
        }

        // Display inventory
        public void displayInventory(Room room, String roomType) {
            System.out.println(roomType + ":");
            room.displayRoomDetails();
            System.out.println("Available Rooms: " + getAvailability(roomType));
            System.out.println();
        }
    }

    // 🔹 Main Method
    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory Status\n");

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory(singleRoom, "Single Room");
        inventory.displayInventory(doubleRoom, "Double Room");
        inventory.displayInventory(suiteRoom, "Suite Room");
    }
}