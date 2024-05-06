package controllers;

import uap.Apartment;
import uap.NotAvailableException;
import uap.Property;
import uap.User;

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

public class ApartmentsController implements Initializable {
    ObservableList<Apartment> apartmentList = FXCollections.observableArrayList();

    @FXML
    private TableView<Apartment> apartmentTable;

    @FXML
    private TableColumn<Apartment, Integer> Bedrooms, Washrooms;

    @FXML
    private TableColumn<Apartment, Double> Rent, FloorSpace;

    @FXML
    private TableColumn<Apartment, Boolean> Generators;

    @FXML
    private TableColumn<Apartment, String> Location, Available;

    @FXML
    private TextField bedroomsTF, washroomsTF, floorSpaceTF, locationTF, leaseForUserTF, leaseDurationTF;

    @FXML
    private DatePicker leaseStartDatePicker;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // setup table columns
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        Bedrooms.setCellValueFactory(new PropertyValueFactory<>("noOfBed"));
        Washrooms.setCellValueFactory(new PropertyValueFactory<>("noOfWashRoom"));
        Rent.setCellValueFactory(new PropertyValueFactory<>("rent"));
        FloorSpace.setCellValueFactory(new PropertyValueFactory<>("floorSpace"));
        Generators.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().hasGenerator()));
        apartmentTable.setItems(apartmentList);
        Available.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().isAvailable() ? "✔" : "✘"));

        // load data
        try {
            apartmentList.setAll(App.propertyManager.getAppartments());
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage());
        }

        if (!App.isAdmin) {
            leaseForUserTF.setText(App.currentUser.getId());
            leaseForUserTF.setEditable(false);
        }
    }

    @FXML
    void searchApartment(ActionEvent event) {
        int bedrooms = Utils.parseInt(bedroomsTF.getText());
        int washrooms = Utils.parseInt(washroomsTF.getText());
        double floorSpace = Utils.parseDouble(floorSpaceTF.getText());
        String location = locationTF.getText();

        try {
            ArrayList<Apartment> apartments = App.propertyManager.getAppartments(location, bedrooms, washrooms,
                    floorSpace);
            apartmentList.setAll(apartments);
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void leaseProperty(ActionEvent event) {
        try {
            Property apartment = apartmentTable.getSelectionModel().getSelectedItem();
            User leaseFor = App.propertyManager.findUser(leaseForUserTF.getText());
            int leaseDuration = Utils.parseInt(leaseDurationTF.getText());
            String leaseStartDate = leaseStartDatePicker.getValue().toString();
            App.propertyManager.leaseProperty(apartment, leaseFor, leaseStartDate,
                    leaseDuration);
            apartmentList.setAll(App.propertyManager.getAppartments());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
