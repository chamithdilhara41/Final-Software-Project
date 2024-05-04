package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import lk.ijse.model.Buyer;
import lk.ijse.model.tm.BuyerTm;
import lk.ijse.repository.BuyerRepo;
import java.sql.SQLException;
import java.util.List;

public class BuyerFormController {

    public Label lblBuyerForm;

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
        animateLabelTyping();
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
        int index = tblBuyer.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colBuyerID.getCellData(index).toString();
        String name = colBuyerName.getCellData(index).toString();
        String address = colBuyerAddress.getCellData(index).toString();
        String contactOffice = colBuyerContactOffice.getCellData(index).toString();
        String contactManager = colBuyerContactManager.getCellData(index).toString();

        txtBuyerID.setText(id);
        txtBuyerName.setText(name);
        txtBuyerAddress.setText(address);
        txtBuyerContactOffice.setText(contactOffice);
        txtBuyerContactManager .setText(contactManager);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String buyerID = txtBuyerID.getText();

        if (buyerID.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please fill buyer Id for delete ").show();
            return;
        }

        try {
            boolean isDeleted = BuyerRepo.delete(buyerID);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Buyer deleted").show();
                getAllBuyers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {

        String buyerID = txtBuyerID.getText();
        String buyerName = txtBuyerName.getText();
        String buyerAddress = txtBuyerAddress.getText();
        String buyerContactOffice = txtBuyerContactOffice.getText();
        String buyerContactManager = txtBuyerContactManager.getText();

        if(buyerID.isEmpty() || buyerName.isEmpty() || buyerAddress.isEmpty() || buyerContactOffice.isEmpty() || buyerContactManager.isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please fill all the fields").show();
            return;
        }

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
        String buyerID = txtBuyerID.getText();
        String buyerName = txtBuyerName.getText();
        String buyerAddress = txtBuyerAddress.getText();
        String buyerContactOffice = txtBuyerContactOffice.getText();
        String buyerContactManager = txtBuyerContactManager.getText();

        if(buyerID.isEmpty() || buyerName.isEmpty() || buyerAddress.isEmpty() || buyerContactOffice.isEmpty() || buyerContactManager.isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please fill all the fields").show();
            return;
        }

        Buyer buyer = new Buyer(buyerID, buyerName, buyerAddress, buyerContactOffice, buyerContactManager);

        try {
            boolean isUpdated = BuyerRepo.update(buyer);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Buyer updated!").show();
                clearFields();
                getAllBuyers();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String buyerID = txtBuyerID.getText();

        Buyer buyer = BuyerRepo.searchById(buyerID);
        if (buyer != null) {
            txtBuyerID.setText(buyer.getBuyerId());
            txtBuyerName.setText(buyer.getBuyerName());
            txtBuyerAddress.setText(buyer.getBuyerAddress());
            txtBuyerContactOffice.setText(buyer.getBuyerContactOffice());
            txtBuyerContactManager.setText(buyer.getBuyerContactManager());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Buyer not found!").show();
            txtBuyerName.setText("");
            txtBuyerAddress.setText("");
            txtBuyerContactOffice.setText("");
            txtBuyerContactManager.setText("");
        }
    }

    private void animateLabelTyping() {
        String loginText = lblBuyerForm.getText(); // Text to be typed
        int animationDuration = 250; // Duration of animation in milliseconds

        // Set initial text of lblLogin to an empty string
        lblBuyerForm.setText("");

        // Create a Timeline for the typing animation
        Timeline typingAnimation = new Timeline();

        // Add KeyFrames to gradually display the characters
        for (int i = 0; i <= loginText.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDuration * i), event -> {
                lblBuyerForm.setText(loginText.substring(0, finalI)); // Update label text with substring
            });
            typingAnimation.getKeyFrames().add(keyFrame);
        }

        // Play the animation
        typingAnimation.play();
    }
}
