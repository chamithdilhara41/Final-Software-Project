package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private PasswordField txtPasswordLogin;

    @FXML
    private TextField txtUsernameLogin;


    @FXML
    void btnLoginOnAction() throws SQLException, IOException {

        String usernameLogin = txtUsernameLogin.getText();
        String passwordLogin = txtPasswordLogin.getText();

        String sql = "SELECT username,password FROM users WHERE username = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,usernameLogin);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String dbPw = resultSet.getString("password");
            if (passwordLogin.equals(dbPw)){
                new Alert(Alert.AlertType.CONFIRMATION,"Login Successful!").show();
                navigateToTheDashboard();
            }else {
                new Alert(Alert.AlertType.ERROR,"sorry! password is incorrect!").show();
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION,"sorry! username can't be find!").show();
        }

    }

    private void navigateToTheDashboard () throws IOException {
        // Load the FXML file
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        // Create a new Scene
        Scene scene = new Scene(rootNode);

        // Get the Stage from the current window
        Stage stage = (Stage) txtPasswordLogin.getScene().getWindow();

        // Set the new scene to the stage
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }


    @FXML
    void hyperOnActionForgetPassword(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/ForgetPasswordOTPForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Password Forget Form");
        stage.show();
    }

    @FXML
    void hyperOnActionRegister(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Register Form");
        stage.show();
    }

    public void txtOnActionLogin(ActionEvent actionEvent) throws SQLException, IOException {
        btnLoginOnAction();
    }
}
