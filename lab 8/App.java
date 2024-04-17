package PropertyReserve.App;

import PropertyReserve.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PropertyManager propertyManager = new PropertyManager("My Property Manager");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Property");
            System.out.println("2. Search Property");
            System.out.println("3. View by Category");
            System.out.println("4. Rent a Property");
            System.out.println("5. Lease Over");
            System.out.println("6. Display all");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                             // Add property
                System.out.print("Enter property ID: \n");
                String id1 = scanner.nextLine();
                System.out.print("Enter property description:  \n");
                String description = scanner.nextLine();
                System.out.print("Enter property location: \n");
                String location = scanner.nextLine();
                System.out.print("Enter property category: \n");
                String category = scanner.nextLine();
                System.out.print("Enter property floorspace: \n");
                double floorspace = scanner.nextDouble();
                System.out.print("Enter property rent: \n");
                double rent = scanner.nextDouble();
                System.out.print("Enter number of bedrooms: \n");
                int noOfBedRooms = scanner.nextInt();
                System.out.print("Enter number of washrooms: \n");
                int noOfWashRooms = scanner.nextInt();
                System.out.print("Does the property have a generator? (true/false): \n");
                boolean hasGenerator = scanner.nextBoolean();
                scanner.nextLine(); // consume newline
                ArrayList<String> facilities = new ArrayList<>(); // You may want to ask the user for this
                propertyManager.addProperty(id1, description,location, category, floorspace, rent, noOfBedRooms, noOfWashRooms, hasGenerator);
                break;
                case 2:
                                            // Search property
                    System.out.print("Enter property ID: ");
                    String id2 = scanner.nextLine();
                    Property property = propertyManager.findProperty(id2);
                    System.out.println(property);
                    break;
                    case 3:
                                               // View by category
                        System.out.print("Enter property category: ");
                        String viewCategory = scanner.nextLine();
                        propertyManager.viewByCategory(viewCategory);
                        break;
                case 4:
                                              // Rent a property
                    System.out.print("Enter property ID: ");
                    String id3 = scanner.nextLine();
                    System.out.print("Enter rent: ");
                    double rentAmount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter rented by: ");
                    String rentedBy = scanner.nextLine();
                    propertyManager.rentProperty(id3, rentAmount, rentedBy);
                    break;
                case 5:
                                               // Lease over
                    System.out.print("Enter property ID: ");
                    String id4 = scanner.nextLine();
                    propertyManager.leaseOver(id4);
                    break;
                    case 6:
                    // Display all properties
                    propertyManager.displayAllProperties();
                    break;
                case 7:
                    // Exit
                    System.exit(0);
            }
        }
    }
}