package controllers;

import uap.Property;
import uap.User;
import uap.NotAvailableException;
import uap.CommercialSpace;
// tabiur rahman
import application.App;
import application.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CommercialSpacePageController implements Initializable { // CommercialSpacePageController class that implements Initializable interface.
    private ObservableList<CommercialSpace> commercialSpaceList = FXCollections.observableArrayList(); // ObservableList to hold CommercialSpace objects.

    @FXML
    private TableView<CommercialSpace> commercialSpaceTable; // TableView to display CommercialSpace objects.

    @FXML
    private TableColumn<CommercialSpace, Double> Rent, FloorSpace; // TableColumns for commercial space rent and floor space.

    @FXML
    private TableColumn<CommercialSpace, Boolean> FireExit; // TableColumn for commercial space fire exit.

    @FXML
    private TableColumn<CommercialSpace, String> Location, Available; // TableColumns for commercial space location and availability.

    @FXML
    private TextField locationTF, floorSpaceTF, leaseForUserTF, leaseDurationTF; // TextFields for commercial space location, floor space, lease for user, and lease duration.

    @FXML
    private DatePicker leaseStartDatePicker; // DatePicker for lease start date.

    @FXML
    void searchProperty(ActionEvent event) { // Method to handle searchProperty action.
        String location = locationTF.getText(); // Get the location.
        double floorSpace = Utils.parseDouble(floorSpaceTF.getText()); // Parse the floor space.
        try {
            commercialSpaceList.setAll(App.propertyManager.getCommercialSpaces(location, floorSpace)); // Get the commercial spaces and set them in the ObservableList.
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage()); // Print the exception message.
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { // Method to initialize the controller.
        // setup table columns
        Location.setCellValueFactory(new PropertyValueFactory<>("location")); // Set the cell value factory for the location column.
        Rent.setCellValueFactory(new PropertyValueFactory<>("rent")); // Set the cell value factory for the rent column.
        FloorSpace.setCellValueFactory(new PropertyValueFactory<>("floorSpace")); // Set the cell value factory for the floor space column.
        FireExit.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().hasFireExit())); // Set the cell value factory for the fire exit column.
        Available.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAvailable() ? "✔" : "✘")); // Set the cell value factory for the available column.
        commercialSpaceTable.setItems(commercialSpaceList); // Set the items of the commercial space table.

        // load data
        try {
            commercialSpaceList.setAll(App.propertyManager.getCommercialSpaces()); // Get all commercial spaces and set them in the ObservableList.
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage()); // Print the exception message.
        }
    }

    @FXML
    void leaseProperty(ActionEvent event) { // Method to handle leaseProperty action.
        try {
            Property property = commercialSpaceTable.getSelectionModel().getSelectedItem(); // Get the selected property.
            User leaseFor = App.propertyManager.findUser(leaseForUserTF.getText()); // Find the user for lease.
            int leaseDuration = Utils.parseInt(leaseDurationTF.getText()); // Parse the lease duration.
            String leaseStartDate = leaseStartDatePicker.getValue().toString(); // Get the lease start date.
            App.propertyManager.leaseProperty(property, leaseFor, leaseStartDate, leaseDuration); // Lease the property.
            commercialSpaceList.setAll(App.propertyManager.getCommercialSpaces()); // Get all commercial spaces and set them in the ObservableList.
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Print the exception message.
        }
    }
}