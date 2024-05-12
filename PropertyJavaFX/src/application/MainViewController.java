package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
// md sakib hossaine
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController implements Initializable { // MainViewController class implementing Initializable interface.

    @FXML
    private VBox pageContainer, adminControls; // Two VBox objects for page container and admin controls.

    @FXML
    private Label pageTitle; // Label for the page title.

    @FXML
    void addProperty(ActionEvent event) { // Method to handle addProperty action.
        pageTitle.setText("Add Property"); // Set the page title.
        showPage("../pages/AddProperty.fxml"); // Show the AddProperty page.
    }

    @FXML
    void addUser(ActionEvent event) { // Method to handle addUser action.
        pageTitle.setText("Add User"); // Set the page title.
        showPage("../pages/AddUser.fxml"); // Show the AddUser page.
    }

    @FXML
    void logOut(ActionEvent event) { // Method to handle logOut action.
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("../pages/Login.fxml")); // Load the login page.
            Scene scene = new Scene(loginPage); // Create a new scene with the login page.
            scene.getStylesheets().add(getClass().getResource("App.css").toExternalForm()); // Add the CSS stylesheet to the scene.

            // inject login page to the primary stage
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the primary stage.
            primaryStage.setScene(scene); // Set the scene on the primary stage.
            primaryStage.show(); // Show the primary stage.
            App.isAdmin = false; // Set isAdmin to false.
            App.currentUser = null; // Set currentUser to null.
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for the IOException.
        }
    }

    @FXML
    void showApartments(ActionEvent event) { // Method to handle showApartments action.
        pageTitle.setText("Apartments"); // Set the page title.
        showPage("../pages/Apartments.fxml"); // Show the Apartments page.
    }

    @FXML
    void showCommercialSpaces(ActionEvent event) { // Method to handle showCommercialSpaces action.
        pageTitle.setText("Commercial Spaces"); // Set the page title.
        showPage("../pages/CommercialSpaces.fxml"); // Show the CommercialSpaces page.
    }

    @FXML
    void showLeaseRecord(ActionEvent event) { // Method to handle showLeaseRecord action.
        pageTitle.setText("Lease Record"); // Set the page title.
        showPage("../pages/LeaseRecord.fxml"); // Show the LeaseRecord page.
    }

    void showPage(String pageName) { // Method to show a page.
        try {
            Parent page = new FXMLLoader().load(getClass().getResourceAsStream(pageName)); // Load the page.
            pageContainer.getChildren().clear(); // Clear the page container.
            pageContainer.getChildren().add(page); // Add the page to the page container.
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for the IOException.
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { // Method to initialize the controller.
        pageTitle.setText("Apartments"); // Set the page title.
        showPage("../pages/Apartments.fxml"); // Show the Apartments page.

        adminControls.managedProperty().bind(adminControls.visibleProperty()); // Bind the managed property to the visible property.
        adminControls.setVisible(App.isAdmin); // Set the visibility of the admin controls.
    }

}