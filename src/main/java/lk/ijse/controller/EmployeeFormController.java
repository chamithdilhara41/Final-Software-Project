package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Vehicle;
import lk.ijse.repository.VehicleRepo;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private JFXComboBox<String> cmbVehicleNo;

    @FXML
    private TableColumn<?, ?> colEmployeeAddress;

    @FXML
    private TableColumn<?, ?> colEmployeeContact;

    @FXML
    private TableColumn<?, ?> colEmployeeID;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeContact;

    @FXML
    private TextField txtEmployeeID;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeSalary;

    public void initialize() {
      getVehicleNos();
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void btnOnActionClear(ActionEvent event) {

    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {

    }

    @FXML
    void btnOnActionSave(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void txtOnActionSearch(ActionEvent event) {

    }

    public void cmbVehicleNoOnAction(ActionEvent actionEvent) {

    }

    private void getVehicleNos() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = VehicleRepo.getNos();

            for(String id : idList) {
                obList.add(id);
            }

            cmbVehicleNo.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
