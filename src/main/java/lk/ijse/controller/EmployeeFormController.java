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
import lk.ijse.model.Vehicle;
import lk.ijse.model.tm.EmployeeTm;
import lk.ijse.repository.EmployeeRepo;
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
        int index = tblEmployee.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colEmployeeID.getCellData(index).toString();
        String name = colEmployeeName.getCellData(index).toString();
        String address = colEmployeeAddress.getCellData(index).toString();
        String contact = colEmployeeContact.getCellData(index).toString();
        String salary = colEmployeeSalary.getCellData(index).toString();
        String vehicleNo = colVehicleNo.getCellData(index).toString();


        txtEmployeeID.setText(id);
        txtEmployeeName.setText(name);
        txtEmployeeAddress.setText(address);
        txtEmployeeContact.setText(contact);
        txtEmployeeSalary.setText(salary);
        cmbVehicleNo.setValue(vehicleNo);
    }

    private void clearFields() {
        txtEmployeeID.setText("");
        txtEmployeeName.setText("");
        txtEmployeeContact.setText("");
        txtEmployeeAddress.setText("");
        txtEmployeeSalary.setText("");
        cmbVehicleNo.getSelectionModel().clearSelection();
    }


    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String EmployeeID = txtEmployeeID.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(EmployeeID);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
                clearFields();
                getAllEmployees();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
                clearFields();
                getAllEmployees();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String employeeID = txtEmployeeID.getText();
        String employeeName = txtEmployeeName.getText();
        String employeeAddress = txtEmployeeAddress.getText();
        String employeeContact = txtEmployeeContact.getText();
        Double employeeSalary = Double.valueOf(txtEmployeeSalary.getText());
        String vehicleNo = cmbVehicleNo.getValue();

        Employee employee = new Employee(employeeID,employeeName,employeeAddress,employeeContact,employeeSalary,vehicleNo);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                clearFields();
                getAllEmployees();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String employeeID = txtEmployeeID.getText();

        Employee employee = EmployeeRepo.searchById(employeeID);
        if (employee != null) {
            txtEmployeeID.setText(employee.getEmployeeId());
            txtEmployeeName.setText(employee.getEmployeeName());
            txtEmployeeAddress.setText(employee.getEmployeeAddress());
            txtEmployeeContact.setText(employee.getEmployeeContact());
            txtEmployeeSalary.setText(String.valueOf(employee.getEmployeeSalary()));
            cmbVehicleNo.setValue(employee.getVehicleNo());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
            txtEmployeeName.setText("");
            txtEmployeeContact.setText("");
            txtEmployeeAddress.setText("");
            txtEmployeeSalary.setText("");
            cmbVehicleNo.getSelectionModel().clearSelection();
        }
    }

    public void cmbVehicleNoOnAction(ActionEvent actionEvent) {
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
}
