package controllers;

import uap.LeaseRecord;
import uap.Property;
import application.App;

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

    @FXML
    private TextField idTextField;

    @FXML
    private HBox searchBox;

    @FXML
    private TableView<LeaseRecord> leaseRecordTable;

    @FXML
    private TableColumn<LeaseRecord, Integer> duration;

    @FXML
    private TableColumn<LeaseRecord, LocalDate> leaseStartDate;

    @FXML
    private TableColumn<LeaseRecord, String> propertyID, userID, note;

    @FXML
    void searchRecord(ActionEvent event) {
        String id = idTextField.getText();

        if (id.isEmpty())
            return;

        try {
            if (id.startsWith("a-") || id.startsWith("c-")) {
                leaseRecordList.setAll(App.propertyManager.getRecords(id));
            } else if (id.startsWith("11-") || id.startsWith("22-")) {
                leaseRecordList.setAll(App.propertyManager.getRecordsForUser(id));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void leaseOver(ActionEvent event) {
        try {
            LeaseRecord leaseRecord = leaseRecordTable.getSelectionModel().getSelectedItem();
            Property property = App.propertyManager.findProperty(leaseRecord.getPropertyId());
            App.propertyManager.leaseOver(property);
            loadLeaseRecords();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        searchBox.managedProperty().bind(searchBox.visibleProperty());

        if (!App.isAdmin) {
            searchBox.setVisible(false);
        }

        // setup table columns
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        leaseStartDate.setCellValueFactory(new PropertyValueFactory<>("lease_start_date"));
        propertyID.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
        leaseRecordTable.setItems(leaseRecordList);

        loadLeaseRecords();
    }

    void loadLeaseRecords() {
        if (App.isAdmin) {
            leaseRecordList.setAll(App.propertyManager.getRecords());
        } else {
            try {
                leaseRecordList.setAll(App.propertyManager.getRecordsForUser(App.currentUser.getId()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
