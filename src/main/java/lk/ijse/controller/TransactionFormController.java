package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Buyer;
import lk.ijse.model.Transaction;
import lk.ijse.model.tm.TransactionTm;
import lk.ijse.repository.BuyerRepo;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.TransactionRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransactionFormController {

    @FXML
    private JFXComboBox<String> cmbOrderID;

    @FXML
    private JFXComboBox<String> cmbPaymentMethod;

    @FXML
    private TableColumn<?, ?> colAccountNo;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colMethod;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colTransactionID;

    @FXML
    private Label lblBuyerName;

    @FXML
    private TableView<TransactionTm> tblTransaction;

    @FXML
    private TextField txtAccountNo;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtTransactionID;

    public void initialize() throws SQLException {
        getAllTransactions();
        setCellValueFactory();
        getOrderIds();
        getPaymentMethods();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private void getAllTransactions() throws SQLException {
        ObservableList<TransactionTm> obList = FXCollections.observableArrayList();
        List<Transaction> transactinList = TransactionRepo.getAll();

        for (Transaction t : transactinList) {
            obList.add(new TransactionTm(
                    t.getTransactionId(),
                    t.getOrderId(),
                    t.getAccountNo(),
                    t.getDescription(),
                    t.getAmount(),
                    t.getDate(),
                    t.getMethod()
            ));
        }tblTransaction.setItems(obList);
    }

    private void setCellValueFactory() {
        colTransactionID.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colAccountNo.setCellValueFactory(new PropertyValueFactory<>("accountNo"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));

    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblTransaction.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String transactionID = colTransactionID.getCellData(index).toString();
        String orderID = colOrderID.getCellData(index).toString();
        String accountNo = colAccountNo.getCellData(index).toString();
        String description = colDescription.getCellData(index).toString();
        String amount = colAmount.getCellData(index).toString();
        String date = colDate.getCellData(index).toString();
        String method = colMethod.getCellData(index).toString();

        txtTransactionID.setText(transactionID);
        cmbOrderID.setValue(orderID);
        txtAccountNo.setText(accountNo);
        txtDescription.setText(description);
        txtAmount.setText(amount);
        txtDate.setText(date);
        cmbPaymentMethod.setValue(method);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtTransactionID.setText("");
        txtAmount.setText("");
        txtAccountNo.setText("");
        txtDescription.setText("");
        cmbOrderID.getSelectionModel().clearSelection();
        cmbPaymentMethod.getSelectionModel().clearSelection();
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String transactionID = txtTransactionID.getText();

        try {
            boolean isDeleted = TransactionRepo.delete(transactionID);
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Transaction deleted").show();
                clearFields();
                getAllTransactions();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new  Alert(Alert.AlertType.ERROR,"Transaction Not Found").show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String transactionID = txtTransactionID.getText();
        String orderID = cmbOrderID.getValue();
        String accountNo = txtAccountNo.getText();
        String description = txtDescription.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        String date = txtDate.getText();
        String paymentMethod = cmbPaymentMethod.getValue();

        Transaction transaction = new Transaction(transactionID, orderID, accountNo, description, amount, date, paymentMethod);

        try {
            boolean isSaved = TransactionRepo.save(transaction);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Transaction Saved").show();
                clearFields();
                getAllTransactions();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String transactionID = txtTransactionID.getText();
        String orderID = cmbOrderID.getValue();
        String accountNo = txtAccountNo.getText();
        String description = txtDescription.getText();
        Double amount = Double.valueOf(txtAmount.getText());
        String date = txtDate.getText();
        String paymentMethod = cmbPaymentMethod.getValue();

        Transaction transaction = new Transaction(transactionID, orderID, accountNo, description, amount, date, paymentMethod);

        try {
            boolean isUpdated = TransactionRepo.update(transaction);
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Transaction Updated",ButtonType.OK).show();
                clearFields();
                getAllTransactions();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void cmbOrderIdDOnAction(ActionEvent event) {
        String oId = cmbOrderID.getValue();

        try {
            Buyer buyer = BuyerRepo.searchByOrderIdForTransaction(oId);
            if(buyer != null){
                lblBuyerName.setText(buyer.getBuyerName());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) throws SQLException {
        String transactionID = txtTransactionID.getText();

        Transaction transaction = TransactionRepo.searchByTransactionId(transactionID);
        if(transaction != null){
            new Alert(Alert.AlertType.INFORMATION, "Transaction Found").show();
            txtTransactionID.setText(transaction.getTransactionId());
            cmbOrderID.setValue(transaction.getOrderId());
            txtAccountNo.setText(transaction.getAccountNo());
            txtDescription.setText(transaction.getDescription());
            txtAmount.setText(String.valueOf(transaction.getAmount()));
            txtDate.setText(transaction.getDate());
            cmbPaymentMethod.setValue(transaction.getMethod());
        }else {
            new Alert(Alert.AlertType.ERROR, "Transaction Not Found").show();
            txtAmount.setText("");
            txtAccountNo.setText("");
            txtDescription.setText("");
            lblBuyerName.setText("");
            cmbOrderID.getSelectionModel().clearSelection();
            cmbPaymentMethod.getSelectionModel().clearSelection();
        }
    }

    private void getOrderIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = OrderRepo.getIds();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbOrderID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPaymentMethods() {
        ObservableList<String> obList = FXCollections.observableArrayList(
                "Cash",
                "Credit Card",
                "Cheque"
        );

        cmbPaymentMethod.setItems(obList);
    }

}
