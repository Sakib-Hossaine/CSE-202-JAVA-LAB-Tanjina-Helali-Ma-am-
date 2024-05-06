package controllers;

import uap.Property;
import uap.User;
import uap.NotAvailableException;
import uap.CommercialSpace;

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

public class CommercialSpacePageController implements Initializable {
    private ObservableList<CommercialSpace> commercialSpaceList = FXCollections.observableArrayList();

    @FXML
    private TableView<CommercialSpace> commercialSpaceTable;

    @FXML
    private TableColumn<CommercialSpace, Double> Rent, FloorSpace;

    @FXML
    private TableColumn<CommercialSpace, Boolean> FireExit;

    @FXML
    private TableColumn<CommercialSpace, String> Location, Available;

    @FXML
    private TextField locationTF, floorSpaceTF, leaseForUserTF, leaseDurationTF;

    @FXML
    private DatePicker leaseStartDatePicker;

    @FXML
    void searchProperty(ActionEvent event) {
        String location = locationTF.getText();
        double floorSpace = Utils.parseDouble(floorSpaceTF.getText());
        try {
            commercialSpaceList.setAll(App.propertyManager.getCommercialSpaces(location, floorSpace));
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // setup table columns
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        Rent.setCellValueFactory(new PropertyValueFactory<>("rent"));
        FloorSpace.setCellValueFactory(new PropertyValueFactory<>("floorSpace"));
        FireExit.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().hasFireExit()));
        Available.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().isAvailable() ? "✔" : "✘"));
        commercialSpaceTable.setItems(commercialSpaceList);

        // load data
        try {
            commercialSpaceList.setAll(App.propertyManager.getCommercialSpaces());
        } catch (NotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void leaseProperty(ActionEvent event) {
        try {
            Property property = commercialSpaceTable.getSelectionModel().getSelectedItem();
            User leaseFor = App.propertyManager.findUser(leaseForUserTF.getText());
            int leaseDuration = Utils.parseInt(leaseDurationTF.getText());
            String leaseStartDate = leaseStartDatePicker.getValue().toString();
            App.propertyManager.leaseProperty(property, leaseFor, leaseStartDate,
                    leaseDuration);
            commercialSpaceList.setAll(App.propertyManager.getCommercialSpaces());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}