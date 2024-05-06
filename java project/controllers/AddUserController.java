package controllers;

import uap.User;

import java.net.URL;
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
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private TextField nameField, ageField;

    @FXML
    private CheckBox isAdminCheckbox;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> ID, Name, Role;

    @FXML
    private TableColumn<User, Integer> Age;

    @FXML
    void addUser(ActionEvent event) {
        String name = nameField.getText(), ageValue = ageField.getText();

        if (!(Utils.isValid(name) || Utils.isValid(ageValue)))
            return;

        int age = Integer.parseInt(ageValue);
        String userId = App.propertyManager.addUser(name, age, isAdminCheckbox.isSelected());
        System.out.println("User with id " + userId + " added.");
        loadUsers();
        resetUserForm();
    }

    void resetUserForm() {
        nameField.clear();
        ageField.clear();
        isAdminCheckbox.setSelected(false);
    }

    void loadUsers() {
        userList.setAll(App.propertyManager.getUsers());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // setup table columns
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Age.setCellValueFactory(new PropertyValueFactory<>("age"));
        Role.setCellValueFactory(cellData -> {
            if (cellData.getValue().isAdmin())
                return new SimpleStringProperty("Admin");
            else
                return new SimpleStringProperty("User");
        });

        // setup table data
        userTable.setItems(userList);
        loadUsers();
    }
}
