package controllers;

import uap.LeaseRecord;
import uap.Property;
import application.App;
// md.sakib hossaine
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class LeaseRecordsController implements Initializable {
    ObservableList<LeaseRecord> leaseRecordList = FXCollections.observableArrayList(); 
    // observableArrayList is List of lease records.

    @FXML
    private TextField idTextField;
    // Text field for the ID.

    @FXML
    private HBox searchBox;
    // Search box.

    @FXML
    private TableView<LeaseRecord> leaseRecordTable;
    // Table view for the lease records.

    @FXML
    private TableColumn<LeaseRecord, Integer> duration;// instance
    // Column for the duration.

    @FXML
    private TableColumn<LeaseRecord, LocalDate> leaseStartDate;
    // Column for the lease start date.

    @FXML
    private TableColumn<LeaseRecord, String> propertyID, userID, note;
    // Columns for the property ID, user ID, and note.

    @FXML
    void searchRecord(ActionEvent event) { // Method to handle search record action.
        String id = idTextField.getText(); // Get the ID.

        // If the ID is empty, return.
        if (id.isEmpty())
            return;

        try {
            // If the ID starts with "a-" or "c-", get the records for the ID.
            if (id.startsWith("a-") || id.startsWith("c-")) {
                leaseRecordList.setAll(App.propertyManager.getRecords(id));
            }
            // If the ID starts with "11-" or "22-", get the records for the user.
            else if (id.startsWith("11-") || id.startsWith("22-")) {
                leaseRecordList.setAll(App.propertyManager.getRecordsForUser(id));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void leaseOver(ActionEvent event) {
        // Method to handle lease over action.
        try {
            // Get the selected lease record and the property for the record.
            LeaseRecord leaseRecord = leaseRecordTable.getSelectionModel().getSelectedItem();
            Property property = App.propertyManager.findProperty(leaseRecord.getPropertyId());

            // Lease over the property and load the lease records.
            App.propertyManager.leaseOver(property);
            loadLeaseRecords();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Method to initialize the controller.
        // Bind the managed property of the search box to its visible property.
        searchBox.managedProperty().bind(searchBox.visibleProperty());

        // If the user is not an admin, hide the search box.
        if (!App.isAdmin) {
            searchBox.setVisible(false);
        }

        // Set up the table columns.
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        leaseStartDate.setCellValueFactory(new PropertyValueFactory<>("lease_start_date"));
        propertyID.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        leaseRecordTable.setItems(leaseRecordList);

        // Load the lease records.
        loadLeaseRecords();
    }

    void loadLeaseRecords() { // Method to load the lease records.
        // If the user is an admin, get all the records.
        if (App.isAdmin) {
            leaseRecordList.setAll(App.propertyManager.getRecords());
        }
        // If the user is not an admin, get the records for the user.
        else {
            try {
                leaseRecordList.setAll(App.propertyManager.getRecordsForUser(App.currentUser.getId()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}