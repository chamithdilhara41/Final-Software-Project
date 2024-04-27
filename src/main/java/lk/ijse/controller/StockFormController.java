package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.model.Stock;
import lk.ijse.model.Supplier;
import lk.ijse.model.SupplierStockDetail;
import lk.ijse.model.Transaction;
import lk.ijse.model.tm.StockTm;
import lk.ijse.model.tm.SupplierStockDetailTm;
import lk.ijse.model.tm.TransactionTm;
import lk.ijse.repository.StockRepo;
import lk.ijse.repository.SupplierRepo;
import lk.ijse.repository.SupplierStockDetailRepo;
import lk.ijse.repository.TransactionRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StockFormController {

    @FXML
    private JFXComboBox<String> cmbSupplierID;

    @FXML
    private TableColumn<?, ?> colStockID;

    @FXML
    private TextField txtSuppliersStockID;

    @FXML
    private TableColumn<?, ?> colSupplierID;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colTotalWeight;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private Label lblSupplierName;

    @FXML
    private TableView<StockTm> tblStock;

    @FXML
    private TableView<SupplierStockDetailTm> tblSupplierStock;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtStockID;

    @FXML
    private TextField txtWeight;

    public void initialize() throws SQLException {
        getAllStocks();
        setCellValueFactoryForStocks();
        getSupplierIds();
        txtDate.setText(String.valueOf(LocalDate.now()));
    }

    private void setCellValueFactoryForStocks() {
        colStockID.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colTotalWeight.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void getAllStocks() throws SQLException {
        ObservableList<StockTm> obList = FXCollections.observableArrayList();
        List<Stock> stocksList = StockRepo.getAll();

        for (Stock t : stocksList) {
            obList.add(new StockTm(
                    t.getStockId(),
                    t.getWeight(),
                    t.getDate()
            ));
        }
        tblStock.setItems(obList);
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
    void txtOnActionSearchSuppliers(ActionEvent event) throws SQLException {
        String stockID = txtSuppliersStockID.getText();
        ObservableList<SupplierStockDetailTm> obList = FXCollections.observableArrayList();

        List<SupplierStockDetailTm> supplierStockDetail = SupplierStockDetailRepo.searchSuppliersWithStockId(stockID);

        for(SupplierStockDetailTm No : supplierStockDetail) {
            obList.add(new SupplierStockDetailTm(
                No.getSupplierId(),
                No.getName(),
                No.getWeight()
            ));
        }
        tblSupplierStock.setItems(obList);
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
    }

    @FXML
    void btnOnActionAddWeight(ActionEvent event) throws SQLException {
        String stockID = txtStockID.getText();
        String supplierID = cmbSupplierID.getValue();
        String weight = String.valueOf(txtWeight.getText());
        Date date = Date.valueOf(txtDate.getText());

        if (stockID.isEmpty() || supplierID.isEmpty() || weight.describeConstable().isEmpty() ) {
            new Alert(Alert.AlertType.INFORMATION, "Please fill all the fields", ButtonType.OK).show();
            return;
        }

        Stock stock = new Stock(stockID, Double.valueOf(weight), date);

        SupplierStockDetail supplierStockDetail = new SupplierStockDetail(stockID, supplierID,  Double.valueOf(weight));

        try {
            boolean issaved1 = StockRepo.save(stock);
            boolean isSaved;
            if (issaved1 ) {
                isSaved = SupplierStockDetailRepo.save(supplierStockDetail);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "weight saved!").show();
                    getAllStocks();
                    setCellValueFactoryForStocks();
                }

            }
            } catch (SQLException e) {

            boolean isUpdateWeight = StockRepo.updateWeight(stockID,supplierID, Double.valueOf(weight));
            if (isUpdateWeight) {

                boolean fuck= SupplierStockDetailRepo.save(supplierStockDetail);
                if (fuck) {
                    new Alert(Alert.AlertType.INFORMATION, " weight updated!").show();
                    getAllStocks();
                    setCellValueFactoryForStocks();
                }

            }
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String stockID = txtStockID.getText();
        //String supplierID = cmbSupplierID.getValue();
        Double weight = Double.valueOf(txtWeight.getText());
        Date date = Date.valueOf(txtDate.getText());

        Stock stock = new Stock(stockID, weight, date);
        try {
            boolean isSaved = StockRepo.save(stock);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {

    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        String supplierID = cmbSupplierID.getValue();

        try {
            Supplier supplier = SupplierRepo.searchBySupplierIdForPayment(supplierID);
            if(supplier!=null){
                lblSupplierName.setText(supplier.getSupplierName());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.OK).show();
        }
    }

    @FXML
    void txtOnActionSearch(ActionEvent event) {

    }

    private void getSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = SupplierRepo.getIds();

            for(String No : NoList) {
                obList.add(No);
            }

            cmbSupplierID.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
