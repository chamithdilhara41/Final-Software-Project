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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetNewPasswordFormController {
    @FXML
    private Label lblForgetPassword;

    @FXML
    private TextField txtNewPassword;

    public TextField txtReNewPassword;


    @FXML
    void btnChangePasswordOnAction(ActionEvent event) throws IOException {

        String newPassword1 = txtNewPassword.getText();
        String newPassword2 = txtReNewPassword.getText();
        String username = ForgetGetUsernameEmailController.Username;

        if (newPassword1.isEmpty() || newPassword2.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter all fields", ButtonType.OK).show();
            return;
        }

        try {
            if (!isValidPassword()) {
                boolean isChangePassword = false;
                if (newPassword1.equals(newPassword2)) {
                    isChangePassword = changePassword(username,newPassword2);
                }else {
                    new Alert(Alert.AlertType.ERROR, "Passwords does not match", ButtonType.OK).show();
                    return;
                }
                if (isChangePassword) {
                    new Alert(Alert.AlertType.INFORMATION, "Password changed successfully",ButtonType.OK).show();
                    hyperOnActionLogin(event);
                }else {
                    new Alert(Alert.AlertType.ERROR, "Password not changed ",ButtonType.OK).show();

                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Check password fields", ButtonType.OK).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private boolean changePassword(String username, String newPassword2) throws SQLException {
        String sql ="UPDATE `users` SET `password`=? WHERE `username`=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, newPassword2);
        pstm.setString(2, username);

        return pstm.executeUpdate() > 0;
    }

    public boolean isValidPassword(){
        if (Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtNewPassword)) return false;
        if (Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtReNewPassword)) return false;
        return true;
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
    @FXML
    void txtNewPasswordOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtNewPassword);
    }

    @FXML
    void txtReNewPasswordOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtReNewPassword);
    }
}
