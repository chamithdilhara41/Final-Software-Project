package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class SupplierFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private RadioButton btnRadioFemale;

    @FXML
    private RadioButton btnRadioMale;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

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
    private TextField txtSupplierID;

    @FXML
    private TextField txtSupplierName;

}
