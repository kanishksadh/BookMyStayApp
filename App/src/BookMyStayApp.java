
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

                // 🔹 Single Room
                static class SingleRoom extends Room {
                    public SingleRoom() {
                        super(1, 250, 1500.0);
                    }
                }

                // 🔹 Double Room
                static class DoubleRoom extends Room {
                    public DoubleRoom() {
                        super(2, 400, 2500.0);
                    }
                }

                // 🔹 Suite Room
                static class SuiteRoom extends Room {
                    public SuiteRoom() {
                        super(3, 750, 5000.0);
                    }
                }

                // 🔹 Main Method (Entry Point)
                public static void main(String[] args) {
                        // Welcome message
                        System.out.println("Welcome to the Hotel Booking Management System");

                        // Application name and version
                        System.out.println("Application: Book My Stay");
                        System.out.println("Version: 1.0");

                    System.out.println("Hotel Room Initialization\n");

                    // Create room objects (Polymorphism)
                    Room singleRoom = new SingleRoom();
                    Room doubleRoom = new DoubleRoom();
                    Room suiteRoom = new SuiteRoom();

                    // Static availability
                    int singleAvailable = 5;
                    int doubleAvailable = 3;
                    int suiteAvailable = 2;

                    // Display details
                    System.out.println("Single Room:");
                    singleRoom.displayRoomDetails();
                    System.out.println("Available: " + singleAvailable + "\n");

                    System.out.println("Double Room:");
                    doubleRoom.displayRoomDetails();
                    System.out.println("Available: " + doubleAvailable + "\n");

                    System.out.println("Suite Room:");
                    suiteRoom.displayRoomDetails();
                    System.out.println("Available: " + suiteAvailable);
                }
            }

