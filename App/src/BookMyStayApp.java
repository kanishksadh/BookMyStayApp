import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    // ---------------- Room Class ----------------
    static class Room {
        private String type;
        private int beds;
        private int size;
        private double price;

        public Room(String type, int beds, int size, double price) {
            this.type = type;
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void displayDetails(int availability) {
            System.out.println(type + " Room:");
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sqft");
            System.out.println("Price per night: " + price);
            System.out.println("Available: " + availability);
            System.out.println();
        }
    }

    // ---------------- Inventory Class ----------------
    static class RoomInventory {
        private Map<String, Integer> availability = new HashMap<>();

        public void addRoom(String type, int count) {
            availability.put(type, count);
        }

        // Read-only access
        public Map<String, Integer> getRoomAvailability() {
            return availability;
        }
    }

    // ---------------- Search Service ----------------
    static class RoomSearchService {

        public void searchAvailableRooms(
                RoomInventory inventory,
                Room singleRoom,
                Room doubleRoom,
                Room suiteRoom) {

            Map<String, Integer> availability = inventory.getRoomAvailability();

            System.out.println("Room Search Results:\n");

            // Single Room
            if (availability.getOrDefault("Single", 0) > 0) {
                singleRoom.displayDetails(availability.get("Single"));
            }

            // Double Room
            if (availability.getOrDefault("Double", 0) > 0) {
                doubleRoom.displayDetails(availability.get("Double"));
            }

            // Suite Room
            if (availability.getOrDefault("Suite", 0) > 0) {
                suiteRoom.displayDetails(availability.get("Suite"));
            }
        }
    }

    // ---------------- Main Method ----------------
    public static void main(String[] args) {

        // Create Room Objects
        Room singleRoom = new Room("Single", 1, 250, 1300.0);
        Room doubleRoom = new Room("Double", 2, 400, 2500.0);
        Room suiteRoom = new Room("Suite", 3, 750, 5000.0);

        // Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 3);
        inventory.addRoom("Suite", 2);

        // Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Perform Search (Read-Only)
        searchService.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );
    }
}