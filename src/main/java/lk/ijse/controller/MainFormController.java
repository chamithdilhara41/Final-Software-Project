package lk.ijse.controller;

import javafx.animation.TranslateTransition;
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
        AnchorPane buyerPane = FXMLLoader.load(getClass().getResource("/view/BuyerForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(buyerPane);
        AnimationUtil.popUpAnimation(mainPane,buyerPane);
    }

    @FXML
    void btnOnActionDashboard(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(dashboardPane);
        AnimationUtil.popUpAnimation(mainPane,dashboardPane);

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
        AnimationUtil.popUpAnimation(mainPane,employeePane);
    }

    @FXML
    void btnOnActionLogout(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(root);

        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.setTitle("Login Form");

        loginStage.show();
    }

    @FXML
    void btnOnActionOrders(ActionEvent event) throws IOException {
        AnchorPane orderPane = FXMLLoader.load(getClass().getResource("/view/OrderForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(orderPane);
        AnimationUtil.popUpAnimation(mainPane,orderPane);
    }

    @FXML
    void btnOnActionPayment(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(getClass().getResource("/view/PaymentForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(paymentPane);
        AnimationUtil.popUpAnimation(mainPane,paymentPane);
    }

    @FXML
    void btnOnActionDelivery(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(getClass().getResource("/view/DeliveryForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(paymentPane);
        AnimationUtil.popUpAnimation(mainPane,paymentPane);
    }

    @FXML
    void btnOnActionSettings(ActionEvent event) {

    }

    @FXML
    void btnOnActionStock(ActionEvent event) throws IOException {
        AnchorPane stockPane = FXMLLoader.load(getClass().getResource("/view/StockForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(stockPane);
        AnimationUtil.popUpAnimation(mainPane,stockPane);
    }

    @FXML
    void btnOnActionSupplier(ActionEvent event) throws IOException {

        AnchorPane supplierPane = FXMLLoader.load(getClass().getResource("/view/SupplierForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(supplierPane);
        AnimationUtil.popUpAnimation(mainPane,supplierPane);
    }

    @FXML
    void btnOnActionTransactions(ActionEvent event) throws IOException {
        AnchorPane transactinPane = FXMLLoader.load(getClass().getResource("/view/TransactionForm.fxml"));

        mainPane.getChildren().clear();
        mainPane.getChildren().add(transactinPane);
        AnimationUtil.popUpAnimation(mainPane, transactinPane);
    }

    @FXML
    void btnOnActionVehicle(ActionEvent event) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(getClass().getResource("/view/VehicleForm.fxml"));
        mainPane.getChildren().clear();
        mainPane.getChildren().add(vehicleForm);
        AnimationUtil.popUpAnimation(mainPane, vehicleForm);
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

    public static class AnimationUtil {

        public static void popUpAnimation(AnchorPane stage, Parent rootNode) {

            // Implement your right back animation logic here
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), rootNode);
            translateTransition.setFromX(+rootNode.getBoundsInLocal().getWidth());
            translateTransition.setToX(0);
            translateTransition.play();
        }
    }
}
