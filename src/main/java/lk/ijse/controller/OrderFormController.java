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
import lk.ijse.model.Order;
import lk.ijse.model.tm.OrderTm;
import lk.ijse.repository.BuyerRepo;
import lk.ijse.repository.OrderRepo;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderFormController {

    @FXML
    private JFXComboBox<String> cmbBuyerId;

    @FXML
    private TableColumn<?, ?> colBuyerID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private Label lblBuyerName;

    @FXML
    private TableView<OrderTm> tblOrder;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrderID;

    public void initialize() throws SQLException {
        getAllOrders();
        setCellValueFactory();
        getBuyerIds();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setCellValueFactory() {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colBuyerID.setCellValueFactory(new PropertyValueFactory<>("buyerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAllOrders() throws SQLException {
        ObservableList<OrderTm> obList = FXCollections.observableArrayList();
        List<Order> ordersList = OrderRepo.getAll();

        for ( Order order: ordersList){
            obList.add(new OrderTm(
                    order.getOrderId(),
                    order.getBuyerId(),
                    order.getDate()
            ));
        }
        tblOrder.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblOrder.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String orderId = colOrderID.getCellData(index).toString();
        String buyerId = colBuyerID.getCellData(index).toString();
        String date = colDate.getCellData(index).toString();

        txtOrderID.setText(orderId);
        txtDate.setText(date);
        cmbBuyerId.setValue(buyerId);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtOrderID.setText("");
        //txtDate.setText("");
        cmbBuyerId.getSelectionModel().clearSelection();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String orderID = txtOrderID.getText();

        try {
            boolean isDeleted = OrderRepo.delete(orderID);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Order Deleted").show();
                clearFields();
                getAllOrders();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String buyerId = cmbBuyerId.getValue();
        String date = txtDate.getText();

        Order order = new Order(orderID, buyerId, date);
        try {
            boolean isSaved = OrderRepo.save(order);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Order Saved", ButtonType.OK).show();
                clearFields();
                getAllOrders();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String orderID = txtOrderID.getText();
        String buyerId = cmbBuyerId.getValue();
        String date = txtDate.getText();

        Order order = new Order(orderID, buyerId, date);
        try {
            boolean isUpdated = OrderRepo.update(order);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Order Updated", ButtonType.OK).show();
                clearFields();
                getAllOrders();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbBuyerIdOnAction(ActionEvent event) {
        String No = cmbBuyerId.getValue();
        try {
            Buyer buyer = BuyerRepo.searchByBuyerIdForOrder(No);

            if (buyer != null) {
                lblBuyerName.setText(buyer.getBuyerName());
            } else {
                // Handle case when vehicle is not found
                lblBuyerName.setText("");
            }

        } catch (SQLException e) {
            // Handle any SQLException that might occur during the search
            new Alert(Alert.AlertType.ERROR, "Error occurred while searching for buyer: " + e.getMessage()).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String orderID = txtOrderID.getText();

        Order order = OrderRepo.searchByOrderId(orderID);
        if (order != null) {
            txtOrderID.setText(order.getOrderId());
            cmbBuyerId.setValue(order.getBuyerId());
            txtDate.setText(order.getDate());
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Order not found!").show();
            cmbBuyerId.getSelectionModel().clearSelection();
        }
    }

    private void getBuyerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = BuyerRepo.getIds();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbBuyerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
