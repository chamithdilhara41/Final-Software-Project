package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane rootNode;

    @FXML
    void btnOnActionBuyer(ActionEvent event) {

    }

    @FXML
    void btnOnActionDashboard(ActionEvent event) {

    }

    @FXML
    void btnOnActionEmployee(ActionEvent event) {

    }

    @FXML
    void btnOnActionLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
    }

    @FXML
    void btnOnActionOrders(ActionEvent event) {

    }

    @FXML
    void btnOnActionPayment(ActionEvent event) {

    }

    @FXML
    void btnOnActionStock(ActionEvent event) {

    }

    @FXML
    void btnOnActionSupplier(ActionEvent event) {

    }

    @FXML
    void btnOnActionTransactions(ActionEvent event) {

    }

    @FXML
    void btnOnActionVehicle(ActionEvent event) {

    }

}
