package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierFormController {

    public TableView<SupplierTm> tblSupplier;

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

    private ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        getAllSuppliers();
        setCellValueFactory();
    }

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
                getAllSuppliers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

        String supplierID = txtSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContact = txtSupplierContact.getText();
        String supplierGender = txtSupplierGender.getText();

        Supplier supplier = new Supplier(supplierID, supplierName, supplierAddress, supplierContact, supplierGender);

        try {
            boolean isUpdated = SupplierRepo.update(supplier);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
                getAllSuppliers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

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
    void getAllSuppliers() throws SQLException {
        obList = FXCollections.observableArrayList();
        List<Supplier> supplierList = SupplierRepo.getAll();

        for ( Supplier supplier: supplierList){
            obList.add(new SupplierTm(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getSupplierAddress(),
                    supplier.getSupplierContact(),
                    supplier.getSupplierGender()
            ));
        }
        tblSupplier.setItems(obList);
    }

    void setCellValueFactory(){
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colSupplierContact.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
        colSupplierGender.setCellValueFactory(new PropertyValueFactory<>("supplierGender"));

    }
}
