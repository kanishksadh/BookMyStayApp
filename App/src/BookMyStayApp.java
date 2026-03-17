import java.util.*;

public class BookMyStayApp {

    // ---------------- Reservation Class ----------------
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // ---------------- Room Inventory Class ----------------
    static class RoomInventory {
        private Map<String, Integer> availability;

        public RoomInventory() {
            availability = new HashMap<>();
        }

        public void addRoom(String type, int count) {
            availability.put(type, count);
        }

        public boolean isAvailable(String type) {
            return availability.getOrDefault(type, 0) > 0;
        }

        public void decrementRoom(String type) {
            if (isAvailable(type)) {
                availability.put(type, availability.get(type) - 1);
            }
        }

        public int getAvailableCount(String type) {
            return availability.getOrDefault(type, 0);
        }
    }

    // ---------------- Booking Request Queue ----------------
    static class BookingRequestQueue {
        private Queue<Reservation> requestQueue;

        public BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }

        public Reservation getNextRequest() {
            return requestQueue.poll();
        }

        public boolean hasPendingRequests() {
            return !requestQueue.isEmpty();
        }
    }

    // ---------------- Room Allocation Service ----------------
    static class RoomAllocationService {
        // Tracks assigned room IDs to prevent double booking
        private Map<String, Set<String>> allocatedRoomIds;

        public RoomAllocationService() {
            allocatedRoomIds = new HashMap<>();
        }

        // Allocate a room if available
        public void allocateRoom(Reservation reservation, RoomInventory inventory) {
            String type = reservation.getRoomType();

            if (!inventory.isAvailable(type)) {
                System.out.println("No available rooms for type: " + type + " for Guest: " + reservation.getGuestName());
                return;
            }

            // Initialize set if not present
            allocatedRoomIds.putIfAbsent(type, new HashSet<>());

            // Generate a unique room ID
            int nextId = allocatedRoomIds.get(type).size() + 1;
            String roomId = type + "-" + nextId;

            // Assign room and update allocated IDs
            allocatedRoomIds.get(type).add(roomId);

            // Decrement inventory
            inventory.decrementRoom(type);

            // Confirm booking
            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
        }
    }

    // ---------------- Main Method ----------------
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing\n");

        // Setup inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 2);
        inventory.addRoom("Suite", 1);

        // Setup booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Room allocation service
        RoomAllocationService allocationService = new RoomAllocationService();

        // Process bookings in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation res = bookingQueue.getNextRequest();
            allocationService.allocateRoom(res, inventory);
        }
    }
}