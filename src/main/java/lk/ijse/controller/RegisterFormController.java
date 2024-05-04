package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.db.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private Label lblRegister;

    @FXML
    private TextField txtEmailRegister;

    @FXML
    private TextField txtNameRegister;

    @FXML
    private PasswordField txtPasswordRegister;

    @FXML
    private TextField txtUsernameRegister;

    public void initialize() {
        animateLabelTyping();
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        String usernameRegister = txtUsernameRegister.getText();
        String emailRegister = txtEmailRegister.getText();
        String name = txtNameRegister.getText();
        String passwordRegister = txtPasswordRegister.getText();

        if(usernameRegister.isEmpty() || name.isEmpty() || passwordRegister.isEmpty() || emailRegister.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Please Fill All Fields").show();
        }else {
            try {
                boolean isSaved = saveUser(usernameRegister, name, passwordRegister, emailRegister);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();

                    Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Login Form");
                    stage.show();
                }
            } catch (
                    SQLException | IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private boolean saveUser(String usernameRegister, String name, String passwordRegister, String emailRegister) throws SQLException {
        String sql = "INSERT INTO users VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, usernameRegister);
        pstm.setObject(2, name);
        pstm.setObject(3, passwordRegister);
        pstm.setObject(4, emailRegister);

        return pstm.executeUpdate() > 0;
    }


    @FXML
    void hyperOnActionLogin(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
    }

    private void animateLabelTyping() {
        String loginText = lblRegister.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblRegister.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblRegister.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }

}
