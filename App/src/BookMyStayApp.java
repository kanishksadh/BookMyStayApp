import java.util.*;

// CLASS: BookingRequest
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}


// CLASS: RoomInventory (Thread-Safe)
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    // SYNCHRONIZED → Critical Section
    public synchronized boolean bookRoom(String roomType) {

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type: " + roomType);
            return false;
        }

        int available = inventory.get(roomType);

        if (available > 0) {
            System.out.println(Thread.currentThread().getName()
                    + " booked " + roomType);

            inventory.put(roomType, available - 1);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName()
                    + " failed (No " + roomType + " available)");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}


// CLASS: Shared Booking Queue
class BookingQueue {

    private Queue<BookingRequest> queue = new LinkedList<>();

    // Add request (synchronized)
    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    // Get request (synchronized)
    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}


// CLASS: BookingProcessor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory, String name) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {

            BookingRequest request;

            // synchronized fetch
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break;
            }

            // Critical booking section handled inside inventory
            inventory.bookRoom(request.roomType);

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


// ✅ MAIN CLASS
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Simulate multiple guest requests
        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));   // conflict case
        queue.addRequest(new BookingRequest("Charlie", "Double"));
        queue.addRequest(new BookingRequest("David", "Double")); // conflict case

        // Create multiple threads (guests)
        BookingProcessor t1 = new BookingProcessor(queue, inventory, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(queue, inventory, "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory state
        System.out.println();
        inventory.displayInventory();
    }
}