package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class ForgetGetUsernameEmailController {

    @FXML
    private Label lblForgetPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUsername;

    public static int OTP;
    public static String Username;

    @FXML
    void btnSendOtpOnAction(ActionEvent event) throws IOException {

        String email = txtEmail.getText();
        String username = txtUsername.getText();

        if (username!=null) {
            Random random = new Random();
            int otp = 1000 + random.nextInt(9000);

            boolean sendingOTP = JavaMailUtil.sendMail(email, otp);
                if (sendingOTP) {
                    new Alert(Alert.AlertType.INFORMATION, "OTP Sent", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "OTP Failed", ButtonType.OK).show();
                }

            Username = username;
            OTP = otp;
            System.out.println(">>>" + otp);

            Parent root = FXMLLoader.load(getClass().getResource("/view/ForgetPasswordOTPForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("OTP Form");
            stage.show();
        }else {
            new Alert(Alert.AlertType.ERROR,"username type", ButtonType.OK).show();
        }

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

}
