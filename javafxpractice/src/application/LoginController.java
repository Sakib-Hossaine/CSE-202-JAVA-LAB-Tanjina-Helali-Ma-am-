package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class LoginController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("12345")) {

    
            JOptionPane.showMessageDialog(null, "Login successful");
        } else {
            System.out.println("Login failed");
        }
    }

}
