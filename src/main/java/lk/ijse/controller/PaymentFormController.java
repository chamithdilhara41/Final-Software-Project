package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Payment;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.PaymentTm;
import lk.ijse.repository.PaymentRepo;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPaymentID;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPaymentID;

    @FXML
    private TextField txtSupplierID;

    public void initialize() throws SQLException {
        txtDate.setText(LocalDate.now().toString());
        getAllPayments();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
    }

    private void getAllPayments() throws SQLException {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
        List<Payment> paymentList = PaymentRepo.getAll();

        for (Payment payment : paymentList) {
            obList.add(new PaymentTm(
                    payment.getPaymentId(),
                    payment.getDescription(),
                    payment.getAmount(),
                    payment.getDate(),
                    payment.getSupplierId()
            ));
        }tblPayment.setItems(obList);
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblPayment.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String paymentId = colPaymentID.getCellData(index).toString();
        String description = colDescription.getCellData(index).toString();
        String amount = colAmount.getCellData(index).toString();
        String date = colDate.getCellData(index).toString();
        String supplierId = colSupplierID.getCellData(index).toString();

        txtPaymentID.setText(paymentId);
        txtDescription.setText(description);
        txtAmount.setText(amount);
        txtDate.setText(date);
        txtSupplierID.setText(supplierId);

        try {
            txtOnActionSearchSupplier(new ActionEvent());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtPaymentID.setText("");
        txtSupplierID.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        lblSupplierName.setText("");
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String paymentID = txtPaymentID.getText();

        try {
            boolean isDeleted = PaymentRepo.delete(paymentID);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted").show();
                clearFields();
                getAllPayments();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String paymentID = txtPaymentID.getText();
        String description = txtDescription.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        String date = txtDate.getText();
        String supplierID = txtSupplierID.getText();

        Payment payment = new Payment(paymentID, description, amount, date, supplierID);

        try {
            boolean isSaved = PaymentRepo.save(payment);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved").show();
                clearFields();
                getAllPayments();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String paymentID = txtPaymentID.getText();
        String description = txtDescription.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        String date = txtDate.getText();
        String supplierID = txtSupplierID.getText();

        Payment payment = new Payment(paymentID, description, amount, date, supplierID);

        try {
            boolean isUpdated = PaymentRepo.update(payment);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Updated").show();
                clearFields();
                getAllPayments();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) {
        String paymentID = txtPaymentID.getText();

        try {
            Payment payment = PaymentRepo.searchByPaymentId(paymentID);
            if (payment != null) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Found").show();
                txtPaymentID.setText(payment.getPaymentId());
                txtDescription.setText(payment.getDescription());
                txtAmount.setText(payment.getAmount().toString());
                txtDate.setText(payment.getDate());
                txtSupplierID.setText(payment.getSupplierId());
                txtOnActionSearchSupplier(event);
            }else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Found").show();
                txtSupplierID.setText("");
                txtDescription.setText("");
                txtAmount.setText("");
                lblSupplierName.setText("");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearchSupplier(ActionEvent event) throws SQLException {
        String supplierID = txtSupplierID.getText();

        Supplier supplier = SupplierRepo.searchBySupplierIdForPayment(supplierID);
        if(supplier != null){
            lblSupplierName.setText(supplier.getSupplierName());

        }else {
            new Alert(Alert.AlertType.ERROR,"Supplier not found").show();
            lblSupplierName.setText("");

        }
    }

}
