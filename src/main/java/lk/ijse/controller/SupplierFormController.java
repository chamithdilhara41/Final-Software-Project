package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import lk.ijse.model.Supplier;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colSupplierAddress;

    @FXML
    private TableColumn<?, ?> colSupplierContact;

    @FXML
    private TableColumn<?, ?> colSupplierGender;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TextField txtSupplierAddress;

    @FXML
    private TextField txtSupplierContact;

    @FXML
    private TextField txtSupplierGender;

    @FXML
    private TextField txtSupplierID;

    @FXML
    private TextField txtSupplierName;

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {

    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String supplierID = txtSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContact = txtSupplierContact.getText();
        String supplierGender = txtSupplierGender.getText();

        Supplier supplier = new Supplier(supplierID, supplierName, supplierAddress, supplierContact, supplierGender);


        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }
    private void clearFields() {
        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtSupplierAddress.setText("");
        txtSupplierContact.setText("");
        txtSupplierGender.setText("");
    }

    public void txtOnActionSearch(ActionEvent actionEvent) throws SQLException {
        String supplierID = txtSupplierID.getText();

        Supplier supplier = SupplierRepo.searchById(supplierID);
        if (supplier != null) {
            txtSupplierID.setText(supplier.getSupplierId());
            txtSupplierName.setText(supplier.getSupplierName());
            txtSupplierAddress.setText(supplier.getSupplierAddress());
            txtSupplierContact.setText(supplier.getSupplierContact());
            txtSupplierGender.setText(supplier.getSupplierGender());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
        }
    }
}
