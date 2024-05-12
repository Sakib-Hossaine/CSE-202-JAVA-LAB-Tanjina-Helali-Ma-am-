package controllers;

import uap.Apartment;
import uap.NotAvailableException;
import uap.Property;
import uap.User;
// Suna hera 
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.App;
import application.Utils;
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

public class ApartmentsController implements Initializable { // ApartmentsController class that implements Initializable interface.
    ObservableList<Apartment> apartmentList = FXCollections.observableArrayList(); // ObservableList to hold Apartment objects.

    @FXML
    private TableView<Apartment> apartmentTable; // TableView to display Apartment objects.

    @FXML
    private TableColumn<Apartment, Integer> Bedrooms, Washrooms; // TableColumns for apartment bedrooms and washrooms.

    @FXML
    private TableColumn<Apartment, Double> Rent, FloorSpace; // TableColumns for apartment rent and floor space.

    @FXML
    private TableColumn<Apartment, Boolean> Generators; // TableColumn for apartment generators.

    @FXML
    private TableColumn<Apartment, String> Location, Available; // TableColumns for apartment location and availability.

    @FXML
    private TextField bedroomsTF, washroomsTF, floorSpaceTF, locationTF, leaseForUserTF, leaseDurationTF; // TextFields for apartment bedrooms, washrooms, floor space, location, lease for user, and lease duration.

    @FXML
    private DatePicker leaseStartDatePicker; // DatePicker for lease start date.

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { // Method to initialize the controller.
        // setup table columns
        Location.setCellValueFactory(new PropertyValueFactory<>("location")); // Set the cell value factory for the location column.
        Bedrooms.setCellValueFactory(new PropertyValueFactory<>("noOfBed")); // Set the cell value factory for the bedrooms column.
        Washrooms.setCellValueFactory(new PropertyValueFactory<>("noOfWashRoom")); // Set the cell value factory for the washrooms column.
        Rent.setCellValueFactory(new PropertyValueFactory<>("rent")); // Set the cell value factory for the rent column.
        FloorSpace.setCellValueFactory(new PropertyValueFactory<>("floorSpace")); // Set the cell value factory for the floor space column.
        Generators.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().hasGenerator())); // Set the cell value factory for the generators column.
        apartmentTable.setItems(apartmentList); // Set the items of the apartment table.
        Available.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isAvailable() ? "✔" : "✘")); // Set the cell value factory for the available column.

        // load data
        try {
            apartmentList.setAll(App.propertyManager.getAppartments()); // Get all apartments and set them in the ObservableList.
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage()); // Print the exception message.
        }

        if (!App.isAdmin) {
            leaseForUserTF.setText(App.currentUser.getId()); // Set the text of the lease for user TextField.
            leaseForUserTF.setEditable(false); // Make the lease for user TextField uneditable.
        }
    }

    @FXML
    void searchApartment(ActionEvent event) { // Method to handle searchApartment action.
        int bedrooms = Utils.parseInt(bedroomsTF.getText()); // Parse the bedrooms.
        int washrooms = Utils.parseInt(washroomsTF.getText()); // Parse the washrooms.
        double floorSpace = Utils.parseDouble(floorSpaceTF.getText()); // Parse the floor space.
        String location = locationTF.getText(); // Get the location.

        try {
            ArrayList<Apartment> apartments = App.propertyManager.getAppartments(location, bedrooms, washrooms, floorSpace); // Get the apartments and set them in the ArrayList.
            apartmentList.setAll(apartments); // Set the ArrayList in the ObservableList.
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage()); // Print the exception message.
        }
    }

    @FXML
    void leaseProperty(ActionEvent event) { 
        // Method to handle leaseProperty action.
        try {
            Property apartment = apartmentTable.getSelectionModel().getSelectedItem();
             // Get the selected apartment.
            User leaseFor = App.propertyManager.findUser(leaseForUserTF.getText()); 
            // Find the user for lease.
            int leaseDuration = Utils.parseInt(leaseDurationTF.getText()); 
            // Parse the lease duration.
            String leaseStartDate = leaseStartDatePicker.getValue().toString(); 
            // Get the lease start date.
            App.propertyManager.leaseProperty(apartment, leaseFor, leaseStartDate, leaseDuration); 
            // Lease the property.
            apartmentList.setAll(App.propertyManager.getAppartments()); 
            // Get all apartments and set them in the ObservableList.
        } catch (Exception e) {
            System.out.println(e.getMessage()); // Print the exception message.
        }
    }
}