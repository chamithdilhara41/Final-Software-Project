package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Employee;
import lk.ijse.model.Supplier;
import lk.ijse.model.Vehicle;
import lk.ijse.model.tm.EmployeeTm;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.EmployeeRepo;
import lk.ijse.repository.SupplierRepo;
import lk.ijse.repository.VehicleRepo;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    public Label lblVehicleType;

    @FXML
    private JFXComboBox<String> cmbVehicleNo;

    @FXML
    private TableColumn<?, ?> colEmployeeAddress;

    @FXML
    private TableColumn<?, ?> colEmployeeContact;

    @FXML
    private TableColumn<?, ?> colEmployeeID;

    @FXML
    private TableColumn<?, ?> colEmployeeSalary;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

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

    public void initialize() throws SQLException {
      getVehicleNos();
      getAllEmployees();
      setCellValueFactory();
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
        String employeeID = txtEmployeeID.getText();
        String employeeName = txtEmployeeName.getText();
        String employeeAddress = txtEmployeeAddress.getText();
        String employeeContact = txtEmployeeContact.getText();
        Double employeeSalary = Double.valueOf(txtEmployeeSalary.getText());
        String vehicleNo = cmbVehicleNo.getValue();

        Employee employee = new Employee(employeeID,employeeName,employeeAddress,employeeContact,employeeSalary,vehicleNo);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved").show();
                getAllEmployees();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void getAllEmployees() throws SQLException {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
        List<Employee> employeesList = EmployeeRepo.getAll();

        for ( Employee employee: employeesList){
            obList.add(new EmployeeTm(
                    employee.getEmployeeId(),
                    employee.getEmployeeName(),
                    employee.getEmployeeAddress(),
                    employee.getEmployeeContact(),
                    employee.getEmployeeSalary(),
                    employee.getVehicleNo()
            ));
        }
        tblEmployee.setItems(obList);
    }

    private void setCellValueFactory() {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
        colEmployeeSalary.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void txtOnActionSearch(ActionEvent event) {

    }

    public void cmbVehicleNoOnAction(ActionEvent actionEvent) {
        String No = cmbVehicleNo.getValue();
        try {
            Vehicle vehicle = VehicleRepo.searchByVehicleNo(No);

            assert vehicle != null;
            lblVehicleType.setText(vehicle.getVehicleType());

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
