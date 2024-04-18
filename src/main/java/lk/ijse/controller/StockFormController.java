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
import lk.ijse.model.Stock;
import lk.ijse.model.tm.StockTm;
import lk.ijse.repository.StockRepo;
import java.sql.SQLException;
import java.util.List;

public class StockFormController {

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colStockID;

    @FXML
    private TableColumn<?, ?> colTotalWeight;

    @FXML
    private TableView<StockTm> tblStock;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtStockID;

    @FXML
    private TextField txtTotalWeight;

    public void initialize() throws SQLException {
        getAllStocks();
        setCellValueFactory();
    }

    private void getAllStocks() throws SQLException {
        ObservableList<StockTm> obList = FXCollections.observableArrayList();
        List<Stock> stockList = StockRepo.getAll();

        for ( Stock stock: stockList){
            obList.add(new StockTm(
                    stock.getStockId(),
                    stock.getTotalWeight(),
                    stock.getQuantity()
            ));
        }
        tblStock.setItems(obList);
    }

    private void setCellValueFactory() {
        colStockID.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colTotalWeight.setCellValueFactory(new PropertyValueFactory<>("totalWeight"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    @FXML
    void OnMouseClicked(MouseEvent event) {
        int index = tblStock.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
        String id = colStockID.getCellData(index).toString();
        String weight = colTotalWeight.getCellData(index).toString();
        String qty = colQuantity.getCellData(index).toString();

        txtStockID.setText(id);
        txtTotalWeight.setText(weight);
        txtQuantity.setText(qty);
    }

    @FXML
    void btnOnActionClear(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtStockID.setText("");
        txtTotalWeight.setText("");
        txtQuantity.setText("");
    }

    @FXML
    void btnOnActionDelete(ActionEvent event) {
        String stockID = txtStockID.getText();

        try {
            boolean isDeleted = StockRepo.delete(stockID);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Stock deleted").show();
                clearFields();
                getAllStocks();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionSave(ActionEvent event) {
        String stockID = txtStockID.getText();
        Double totalWeight = Double.valueOf(txtTotalWeight.getText());
        Integer quantity = Integer.valueOf(txtQuantity.getText());

        Stock stock = new Stock(stockID, totalWeight, quantity);

        try {
            boolean isSaved = StockRepo.save(stock);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Stock Saved").show();
                clearFields();
                getAllStocks();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnOnActionUpdate(ActionEvent event) {
        String stockID = txtStockID.getText();
        Double totalWeight = Double.valueOf(txtTotalWeight.getText());
        Integer quantity = Integer.valueOf(txtQuantity.getText());

        Stock stock = new Stock(stockID, totalWeight, quantity);

        try {
            boolean isUpdated = StockRepo.update(stock);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Stock Updated").show();
                clearFields();
                getAllStocks();
                setCellValueFactory();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void txtOnActionSearch(ActionEvent actionEvent) throws SQLException {
        String stockID = txtStockID.getText();

        Stock stock = StockRepo.searchById(stockID);
            if (stock != null) {
                txtStockID.setText(stock.getStockId());
                txtTotalWeight.setText(Double.toString(stock.getTotalWeight()));
                txtQuantity.setText(Integer.toString(stock.getQuantity()));
            }
            else {
                new Alert(Alert.AlertType.INFORMATION, "Stock not found!").show();
            }
    }

}
