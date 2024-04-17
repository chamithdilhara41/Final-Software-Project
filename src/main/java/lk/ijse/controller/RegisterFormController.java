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
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private TextField txtNameRegister;

    @FXML
    private PasswordField txtPasswordRegister;

    @FXML
    private TextField txtUsernameRegister;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        String usernameRegister = txtUsernameRegister.getText();
        String name = txtNameRegister.getText();
        String passwordRegister = txtPasswordRegister.getText();

        try {
            boolean isSaved = saveUser(usernameRegister, name, passwordRegister);
            if(isSaved) {
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

    private boolean saveUser(String usernameRegister, String name, String passwordRegister) throws SQLException {
        String sql = "INSERT INTO users VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, usernameRegister);
        pstm.setObject(2, name);
        pstm.setObject(3, passwordRegister);

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

}
