package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MainFormController {

    @FXML
    public Label lblDate;

    @FXML
    public Label lblTime;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane rootNode;

    public void initialize() throws IOException {
        loadDashboardForm();
        setDate();
        setTime();
    }

    @FXML
    void btnOnActionBuyer(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(getClass().getResource("/view/BuyerForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(dashboardPane);
    }

    @FXML
    void btnOnActionDashboard(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(dashboardPane);
    }

    private void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(dashboardPane);
    }

    @FXML
    void btnOnActionEmployee(ActionEvent event) throws IOException {
        AnchorPane employeePane = FXMLLoader.load(getClass().getResource("/view/EmployeeForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(employeePane);
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
    void btnOnActionOrders(ActionEvent event) throws IOException {
        AnchorPane orderPane = FXMLLoader.load(getClass().getResource("/view/OrderForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(orderPane);
    }

    @FXML
    void btnOnActionPayment(ActionEvent event) {

    }

    @FXML
    void btnOnActionReports(ActionEvent event) {

    }

    @FXML
    void btnOnActionSettings(ActionEvent event) {

    }

    @FXML
    void btnOnActionStock(ActionEvent event) throws IOException {
        AnchorPane stockPane = FXMLLoader.load(getClass().getResource("/view/StockForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(stockPane);
    }

    @FXML
    void btnOnActionSupplier(ActionEvent event) throws IOException {

        AnchorPane supplierPane = FXMLLoader.load(getClass().getResource("/view/SupplierForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(supplierPane);
    }

    @FXML
    void btnOnActionTransactions(ActionEvent event) {

    }

    @FXML
    void btnOnActionVehicle(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(getClass().getResource("/view/VehicleForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(dashboardPane);
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            LocalTime currentTime = LocalTime.now();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String formattedTime = currentTime.format(timeFormatter);

            lblTime.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Animation.INDEFINITE);

        clock.play();
    }
}
