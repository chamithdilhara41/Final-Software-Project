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
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Vehicle;
import lk.ijse.model.tm.VehicleTm;
import lk.ijse.repository.VehicleRepo;
import java.sql.SQLException;
import java.util.List;

public class VehicleFormController {

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private TableColumn<?, ?> colVehicleType;

    @FXML
    private TableView<VehicleTm> tblVehicle;

    @FXML
    private TextField txtVehicleNo;

    @FXML
    private TextField txtVehicleType;
    public void initialize() throws SQLException {
        getAllVehicles();
        setCellValueFactory();
    }
    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblVehicle.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String vehicleNo = colVehicleNo.getCellData(index).toString();
        String vehicleType = colVehicleType.getCellData(index).toString();

        txtVehicleNo.setText(vehicleNo);
        txtVehicleType.setText(vehicleType);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String vehicleNo = txtVehicleNo.getText();

        try {
            boolean isDeleted = VehicleRepo.delete(vehicleNo);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION , "Vehicle Deleted").showAndWait();
                clearFields();
                getAllVehicles();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR , e.getMessage()).showAndWait();
        }

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
                getAllVehicles();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
    }

    private void getAllVehicles() throws SQLException {
        ObservableList<VehicleTm> obList = FXCollections.observableArrayList();
        List<Vehicle> vehiclesList = VehicleRepo.getAll();

        for ( Vehicle vehicle: vehiclesList){
            obList.add(new VehicleTm(
                    vehicle.getVehicleNo(),
                    vehicle.getVehicleType()
            ));
        }
        tblVehicle.setItems(obList);
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String vehicleNo = txtVehicleNo.getText();
        String vehicleType = txtVehicleType.getText();

        Vehicle vehicle = new Vehicle(vehicleNo, vehicleType);

        try {
            boolean isUpdated = VehicleRepo.update(vehicle);
            if(isUpdated) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle updated!").show();
                getAllVehicles();setCellValueFactory();
            }
        } catch (SQLException e) {
            new  Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String vehicleNo = txtVehicleNo.getText();

        Vehicle vehicle = VehicleRepo.searchByVehicleNo(vehicleNo);

        if(vehicle != null) {
            txtVehicleNo.setText(vehicle.getVehicleNo());
            txtVehicleType.setText(vehicle.getVehicleType());
        } else {
            new Alert(Alert.AlertType.ERROR, "Vehicle not found!").show();
        }
    }

    private void clearFields() {
        txtVehicleNo.setText("");
        txtVehicleType.setText("");
    }
}
