import java.util.LinkedList;
import java.util.Queue;

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

    // ---------------- Booking Request Queue ----------------
    static class BookingRequestQueue {
        private Queue<Reservation> requestQueue;

        public BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        // Add booking request (enqueue)
        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }

        // Process next request (dequeue)
        public void processNextRequest() {
            if (!requestQueue.isEmpty()) {
                Reservation res = requestQueue.poll();
                System.out.println("Processing booking for Guest: "
                        + res.getGuestName()
                        + ", Room Type: "
                        + res.getRoomType());
            }
        }

        // Check if queue has pending requests
        public boolean hasPendingRequests() {
            return !requestQueue.isEmpty();
        }
    }

    // ---------------- Main Method ----------------
    public static void main(String[] args) {

        System.out.println("Booking Request Queue\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vannathi", "Suite");

        // Add requests to queue (FIFO order)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process requests in order
        while (bookingQueue.hasPendingRequests()) {
            bookingQueue.processNextRequest();
        }
    }
}