import java.util.*;

class Reservation {
    static int pnrCounter = 1000;
    int pnr;
    String name;
    String trainNo;
    String trainName;
    String classType;
    String dateOfJourney;
    String from;
    String to;

    Reservation(String name, String trainNo, String trainName, String classType, String dateOfJourney, String from, String to) {
        this.pnr = pnrCounter++;
        this.name = name;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }
}

public class OnlineReservationSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<Integer, Reservation> reservations = new HashMap<>();
    static Map<String, String> users = new HashMap<>();
    static Map<String, String> trains = new HashMap<>();

    public static void main(String[] args) {
        // Sample users and trains
        users.put("user1", "pass1");
        users.put("admin", "admin123");
        trains.put("12345", "Rajdhani Express");
        trains.put("54321", "Shatabdi Express");
        trains.put("67890", "Duronto Express");

        System.out.println("Welcome to the Online Reservation System");

        if (!login()) {
            System.out.println("Too many failed attempts. Exiting.");
            return;
        }

        while (true) {
            System.out.println("\n1. Reserve Ticket\n2. Cancel Ticket\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    reserveTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                    System.out.println("Thank you for using the system!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static boolean login() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Login ID: ");
            String id = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();
            if (users.containsKey(id) && users.get(id).equals(pass)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid credentials. Try again.");
                attempts++;
            }
        }
        return false;
    }

    static void reserveTicket() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Available Trains:");
        for (Map.Entry<String, String> entry : trains.entrySet()) {
            System.out.println("Train No: " + entry.getKey() + ", Name: " + entry.getValue());
        }
        System.out.print("Enter Train Number: ");
        String trainNo = scanner.nextLine();
        String trainName = trains.getOrDefault(trainNo, "Unknown");

        System.out.print("Enter Class Type (e.g., Sleeper, AC): ");
        String classType = scanner.nextLine();
        System.out.print("Enter Date of Journey (dd-mm-yyyy): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("From: ");
        String from = scanner.nextLine();
        System.out.print("To: ");
        String to = scanner.nextLine();

        Reservation res = new Reservation(name, trainNo, trainName, classType, dateOfJourney, from, to);
        reservations.put(res.pnr, res);

        System.out.println("Reservation successful! Your PNR is: " + res.pnr);
    }

    static void cancelTicket() {
        System.out.print("Enter your PNR number: ");
        int pnr = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (reservations.containsKey(pnr)) {
            Reservation res = reservations.get(pnr);
            System.out.println("Reservation Details:");
            System.out.println("Name: " + res.name);
            System.out.println("Train No: " + res.trainNo + ", Train Name: " + res.trainName);
            System.out.println("Class: " + res.classType);
            System.out.println("Date: " + res.dateOfJourney);
            System.out.println("From: " + res.from + " To: " + res.to);

            System.out.print("Confirm cancellation? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes") || confirm.equals("y")) {
                reservations.remove(pnr);
                System.out.println("Ticket cancelled successfully.");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("PNR not found.");
        }
    }
}
