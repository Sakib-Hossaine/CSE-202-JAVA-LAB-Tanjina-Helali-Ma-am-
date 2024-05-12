package controllers;

import java.net.URL;
import java.util.ResourceBundle;
// oishi ghorami
import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uap.InvalidUserException;
import uap.User;

public class LoginController implements Initializable {
    private String superPassword = "12345";
   //password for user and admin
    @FXML
    private Label userNotFoundError, passwordError;

    @FXML
    private PasswordField uPasswordField;

    @FXML
    private TextField loginUID, userNameField, userAgeField;
    

    @FXML
    void logIn(ActionEvent event) {
        String uid = loginUID.getText();
        String password = uPasswordField.getText();

        if (uid == null || uid.isBlank() || password == null || password.isBlank())
            return;

        // otherwise check for user credentials
        if (App.propertyManager.isSuperAdmin(uid)) {
            if (!password.equals(superPassword)) {
                passwordError.setVisible(true);
                return;
            }
            App.isAdmin = true;
            navigateToMainView(event);
        } else {
            try {
                User user = App.propertyManager.findUser(uid);
                if (!password.equals(superPassword)) {
                    passwordError.setVisible(true);
                    return;
                }

                App.currentUser = user;
                App.isAdmin = user.isAdmin();
                navigateToMainView(event);
            } catch (InvalidUserException e) {
                userNotFoundError.setVisible(true);
            }
        }
    }

    void navigateToMainView(ActionEvent event) {
        try {
            Parent mainView = FXMLLoader.load(getClass().getResource("../application/MainView.fxml"));
            Scene scene = new Scene(mainView);
            scene.getStylesheets().add(getClass().getResource("/application/App.css").toExternalForm());

            // inject main view to the primary stage
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            //stockoverflow
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUp(ActionEvent event) {
        String name = userNameField.getText();
        String age = userAgeField.getText();
        System.out.println("Name: " + name + " Age: " + age);
        System.out.println("Signing up...");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // hide error initially
        userNotFoundError.managedProperty().bind(userNotFoundError.visibleProperty());
         // remove the area if invisible
        passwordError.managedProperty().bind(passwordError.visibleProperty());
         // remove the area if invisible
        userNotFoundError.setVisible(false);
        passwordError.setVisible(false);
    }

}
