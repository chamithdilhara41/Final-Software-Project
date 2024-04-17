package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class ForgetPasswordOTPFormController {

    @FXML
    private TextField txtForgetOTP1;

    @FXML
    private TextField txtForgetOTP2;

    @FXML
    private TextField txtForgetOTP3;

    @FXML
    private TextField txtForgetOTP4;

    @FXML
    void btnBackLoginOnAction(ActionEvent event) throws IOException   {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {

    }

}
