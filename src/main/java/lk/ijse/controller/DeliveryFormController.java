package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Buyer;
import lk.ijse.model.Delivery;
import lk.ijse.model.Vehicle;
import lk.ijse.model.tm.DeliveryTm;
import lk.ijse.repository.BuyerRepo;
import lk.ijse.repository.DeliveryRepo;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.VehicleRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DeliveryFormController {

    @FXML
    private JFXComboBox<String> cmbOrderID;

    @FXML
    private JFXComboBox<String> cmbVehicleNo;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDeliveryID;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private Label lblBuyerName;

    @FXML
    private Label lblVehicleType;

    @FXML
    private TableView<Object> tblDelivery;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDeliveryID;

    public void initialize() throws SQLException {
        txtDate.setText(String.valueOf(LocalDate.now()));
        getVehicleNos();
        getOrderIds();
        getAllDeliveries();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colDeliveryID.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
    }

    private void getAllDeliveries() throws SQLException {
        ObservableList<Object> obList = FXCollections.observableArrayList();
        List<Delivery> deliveryList = DeliveryRepo.getAll();

        for (Delivery delivery : deliveryList) {
            obList.add(new DeliveryTm(
                    delivery.getDeliveryId(),
                    delivery.getDate(),
                    delivery.getOrderId(),
                    delivery.getVehicleNo()
            ));
        }tblDelivery.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblDelivery.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String deliveryID = colDeliveryID.getCellData(index).toString();
        String date = colDate.getCellData(index).toString();
        String orderID = colOrderID.getCellData(index).toString();
        String vehicleNo = colVehicleNo.getCellData(index).toString();

        txtDeliveryID.setText(deliveryID);
        txtDate.setText(date);
        cmbOrderID.setValue(orderID);
        cmbVehicleNo.setValue(vehicleNo);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtDeliveryID.setText("");
        lblBuyerName.setText("");
        cmbOrderID.getSelectionModel().clearSelection();
        cmbVehicleNo.getSelectionModel().clearSelection();
    }
    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String deliveryID = txtDeliveryID.getText();

        if (deliveryID.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Delivery ID", ButtonType.OK).show();
            return;
        }

        try {
            boolean isDeleted = DeliveryRepo.delete(deliveryID);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Delivery deleted!").show();
                clearFields();
                getAllDeliveries();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String deliveryID = txtDeliveryID.getText();
        String date = txtDate.getText();
        String orderID = cmbOrderID.getValue();
        String vehicleNo = cmbVehicleNo.getValue();

        if(deliveryID.isEmpty() || date.isEmpty() || orderID.isEmpty() || vehicleNo.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        Delivery delivery = new Delivery(deliveryID, date, orderID, vehicleNo);

        try {
            boolean isSaved = DeliveryRepo.save(delivery);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Saved").show();
                clearFields();
                getAllDeliveries();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String deliveryID = txtDeliveryID.getText();
        String date = txtDate.getText();
        String orderID = cmbOrderID.getValue();
        String vehicleNo = cmbVehicleNo.getValue();

        if(deliveryID.isEmpty() || date.isEmpty() || orderID.isEmpty() || vehicleNo.isEmpty()) {
            new Alert(Alert.AlertType.CONFIRMATION, "Please fill all the fields", ButtonType.OK).show();
            return;
        }


        Delivery delivery = new Delivery(deliveryID, date, orderID, vehicleNo);

        try {
            boolean isUpdated = DeliveryRepo.update(delivery);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Updated").show();
                clearFields();
                getAllDeliveries();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void cmbOrderIdDOnAction(ActionEvent event) {
        String oId = cmbOrderID.getValue();

        try {
            Buyer buyer = BuyerRepo.searchByOrderIdForTransaction(oId);
            if(buyer != null){
                lblBuyerName.setText(buyer.getBuyerName());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void cmbVehicleNoOnAction(ActionEvent event) {
        String No = cmbVehicleNo.getValue();
        try {
            Vehicle vehicle = VehicleRepo.searchByVehicleNoForEmp(No);

            if (vehicle != null) {
                lblVehicleType.setText(vehicle.getVehicleType());
            } else {
                // Handle case when vehicle is not found
                lblVehicleType.setText("");
            }

        } catch (SQLException e) {
            // Handle any SQLException that might occur during the search
            new Alert(Alert.AlertType.ERROR, "Error occurred while searching for vehicle: " + e.getMessage()).show();
        }

    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String deliveryID = txtDeliveryID.getText();

        Delivery delivery = DeliveryRepo.searchByDeliveryId(deliveryID);
        if(delivery != null){
            new Alert(Alert.AlertType.INFORMATION, "Delivery Searched").show();
            txtDeliveryID.setText(delivery.getDeliveryId());
            txtDate.setText(delivery.getDate());
            cmbOrderID.setValue(delivery.getOrderId());
            cmbVehicleNo.setValue(delivery.getVehicleNo());
        }else {
            new Alert(Alert.AlertType.ERROR, "Delivery Not Found").show();
            lblBuyerName.setText("");
            cmbOrderID.getSelectionModel().clearSelection();
            cmbVehicleNo.getSelectionModel().clearSelection();
        }
    }
    private void getVehicleNos() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = VehicleRepo.getNos();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbVehicleNo.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getOrderIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = OrderRepo.getIds();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbOrderID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
