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
import lk.ijse.model.Buyer;
import lk.ijse.model.tm.BuyerTm;
import lk.ijse.repository.BuyerRepo;

import java.sql.SQLException;
import java.util.List;

public class BuyerFormController {

    @FXML
    private TableColumn<?, ?> colBuyerAddress;

    @FXML
    private TableColumn<?, ?> colBuyerContactManager;

    @FXML
    private TableColumn<?, ?> colBuyerContactOffice;

    @FXML
    private TableColumn<?, ?> colBuyerID;

    @FXML
    private TableColumn<?, ?> colBuyerName;

    @FXML
    private TableView<BuyerTm> tblBuyer;

    @FXML
    private TextField txtBuyerAddress;

    @FXML
    private TextField txtBuyerContactManager;

    @FXML
    private TextField txtBuyerContactOffice;

    @FXML
    private TextField txtBuyerID;

    @FXML
    private TextField txtBuyerName;

    public void initialize() throws SQLException {
        getAllBuyers();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colBuyerID.setCellValueFactory(new PropertyValueFactory<>("BuyerId"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory<>("BuyerName"));
        colBuyerAddress.setCellValueFactory(new PropertyValueFactory<>("BuyerAddress"));
        colBuyerContactOffice.setCellValueFactory(new PropertyValueFactory<>("BuyerContactOffice"));
        colBuyerContactManager.setCellValueFactory(new PropertyValueFactory<>("BuyerContactManager"));
    }

    private void getAllBuyers() throws SQLException {
        ObservableList<BuyerTm> obList = FXCollections.observableArrayList();
        List<Buyer> buyerList = BuyerRepo.getAll();

        for ( Buyer buyer: buyerList){
            obList.add(new BuyerTm(
                    buyer.getBuyerId(),
                    buyer.getBuyerName(),
                    buyer.getBuyerAddress(),
                    buyer.getBuyerContactOffice(),
                    buyer.getBuyerContactManager()
            ));
        }
        tblBuyer.setItems(obList);
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
        String buyerID = txtBuyerID.getText();
        String buyerName = txtBuyerName.getText();
        String buyerAddress = txtBuyerAddress.getText();
        String buyerContactOffice = txtBuyerContactOffice.getText();
        String buyerContactManager = txtBuyerContactManager.getText();

        Buyer buyer = new Buyer(buyerID, buyerName, buyerAddress, buyerContactOffice, buyerContactManager);

        try {
            boolean isSaved = BuyerRepo.save(buyer);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Buyer saved!").show();
                clearFields();
                getAllBuyers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void clearFields() {
        txtBuyerID.setText("");
        txtBuyerName.setText("");
        txtBuyerAddress.setText("");
        txtBuyerContactOffice.setText("");
        txtBuyerContactManager.setText("");
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void txtOnActionSearch(ActionEvent event) {

    }

}
