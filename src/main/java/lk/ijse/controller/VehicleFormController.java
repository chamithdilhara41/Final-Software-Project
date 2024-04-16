package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Vehicle;
import lk.ijse.repository.SupplierRepo;
import lk.ijse.repository.VehicleRepo;

import java.sql.SQLException;

public class VehicleFormController {

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableView<?> tblVehicle;

    @FXML
    private TextField txtVehicleNo;

    @FXML
    private TextField txtVehicleType;

    @FXML
    void OnMouseClicked(MouseEvent event) {

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
        String vehicleNo = txtVehicleNo.getText();
        String vehicleType = txtVehicleType.getText();

        Vehicle vehicle = new Vehicle(vehicleNo, vehicleType);

        try {
            boolean isSaved = VehicleRepo.save(vehicle);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle saved!").show();
                clearFields();
//                getAllSuppliers();
//                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void txtOnActionSearch(ActionEvent event) {

    }

    private void clearFields() {
        txtVehicleNo.setText("");
        txtVehicleType.setText("");
    }
}
