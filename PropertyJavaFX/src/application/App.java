package application;

import java.io.IOException;
//md .sakib hossaine
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uap.DataHandler;
import uap.PropertyManager;
import uap.User;

public class App extends javafx.application.Application {
    // Global state
    public static PropertyManager propertyManager = null;
    // static PropertyManager object to manage properties.
    public static boolean isAdmin = false;
    // static boolean to check admin or not
    public static User currentUser = null;
    // object to hold the current user's information.

    @Override
    public void start(Stage primaryStage) {
        // The main entry point for all JavaFX applications.
        loadPropertyManager(); // Load the property manager.
        try {
            Parent root = new FXMLLoader().load(getClass().getResourceAsStream("../pages/Login.fxml")); // Load the
            // login page.
            Scene scene = new Scene(root); // Create a new scene with the login page as root.
            scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm());
            // CSS stylesheet in the scene
            primaryStage.setScene(scene);
            // scene on the primary stage
            primaryStage.show();
            // primary stage show

            primaryStage.setOnCloseRequest(e -> {
                // an event handler for the close request event
                savePropertyManager(); // Save the property manager.
                System.exit(0); // Exit
            });
        } catch (IOException e) {
            e.printStackTrace(); // Print the IOException.
        }
    }

    public static void loadPropertyManager() {
        // Method to load the property manager.
        try {

            // this is in ma'am's jar file, amra oitar instance create kore app.java te use
            // korsi.
            propertyManager = DataHandler.loadData(); // Load the data from the data handler.
            System.out.println("Data loaded successfully"); // Print a success message.
        } catch (Exception e) {
            propertyManager = new PropertyManager("My Property"); // Create a new property manager if data loading
                                                                  // fails.
            System.out.println("Failed to load data. Created new property"); // Print a failure message.
        }
    }

    public static void savePropertyManager() { // Method to save the property manager.
        try {
            DataHandler.saveData(propertyManager); // Save the data using the data handler.
            System.out.println("Data saved successfully"); // Print a success message.
        } catch (IOException e) {
            System.out.println(e.getMessage()); // Print the exception message if data saving fails.
        }
    }

    public static void main(String[] args) { // The main method that launches the application.
        launch(args); // Launch the application.
    }
}

/**
 * For admin:
 * - add apartment, commercial space, user
 * - search apartment, commercial space, user
 * - rent / lease property
 * - can view record of a customer/property
 */

/**
 * For customer:
 * - search apartment, commercial space, user
 * - rent / lease property
 * - can view record of his rental/leased property
 */

/**
 * Search:
 * Apartment: bedroom, washroom, location,floor space
 * Commercial space: location, floor space
 */

/**
 * Lease:
 * property, leaseFor, startDate, durationInMonths
 */