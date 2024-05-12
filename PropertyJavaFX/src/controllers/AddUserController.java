package controllers;

import uap.User;
// Tanisa tarnnum Hridi
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.App;
import application.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddUserController implements Initializable {
    // AddUserController class that implements Initializable interface.
    private ObservableList<User> userList = FXCollections.observableArrayList();
    // ObservableList to hold User objects.

    @FXML
    private TextField nameField, ageField;
    // TextFields for user name and age.

    @FXML
    private CheckBox isAdminCheckbox;
    // CheckBox to indicate if the user is an admin.

    @FXML
    private TableView<User> userTable;
    // TableView to display User objects.

    @FXML
    private TableColumn<User, String> ID, Name, Role;
    // TableColumns for user ID, name, and role.

    @FXML
    private TableColumn<User, Integer> Age;
    // TableColumn for user age.

    @FXML
    void addUser(ActionEvent event) { // Method to handle addUser action.
        String name = nameField.getText(), ageValue = ageField.getText();
        // Get the name and age from the TextFields.

        if (!(Utils.isValid(name) || Utils.isValid(ageValue)))
            // Validate the name and age.
            return;

        int age = Integer.parseInt(ageValue); // Parse the age.
        String userId = App.propertyManager.addUser(name, age, isAdminCheckbox.isSelected());
        // Add the user and get the user ID.
        System.out.println("User with id " + userId + " added.");
        // console e Print the user ID.
        loadUsers(); // Load the users.
        resetUserForm(); // Reset the form.
    }

    void resetUserForm() { // Method to reset the user form.
        nameField.clear(); // Clear the name TextField.
        ageField.clear(); // Clear the age TextField.
        isAdminCheckbox.setSelected(false); // Uncheck the admin checkbox.
    }

    void loadUsers() { // Method to load the users.
        for (User user : App.propertyManager.getUsers()) {
            if (!user.isAdmin()) {
                userList.add(user);
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { // Method to initialize the controller.
        // setup table columns
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        // Set the cell value factory for the ID column.
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        // Set the cell value factory for the name column.
        Age.setCellValueFactory(new PropertyValueFactory<>("age"));
        // Set the cell value factory for the age column.
        Role.setCellValueFactory(cellData -> {
            // Set the cell value factory for the role column.
            if (cellData.getValue().isAdmin())
                // If the user is an admin, return "Admin".
                return new SimpleStringProperty("Admin");
            else // If the user is not an admin, return "User".
                return new SimpleStringProperty("User");
        });

        // setup table data
        userTable.setItems(userList); // Set users data to the user table.
        loadUsers(); // Load the users.
    }
}
