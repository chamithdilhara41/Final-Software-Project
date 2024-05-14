package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardFormController {

    private int supplierCount;

    private int employeeCount;

    private int buyerCount;




    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblSupplierCount;

    @FXML
    private Label lblBuyerCount;

    public void initialize() {
        try {
            supplierCount = getSupplierCount();
            employeeCount = getEmployeeCount();
            buyerCount = getBuyerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(supplierCount);
        setEmployeeCount(employeeCount);
        setBuyerCount(buyerCount);
    }

    private void setEmployeeCount(int employeeCount) {
        lblEmployeeCount.setText(String.valueOf(employeeCount));
    }

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM employee";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getInt("employee_count");
        }
        return 0;

    }

    private void setSupplierCount(int supplierCount) {
        lblSupplierCount.setText(String.valueOf(supplierCount));
    }

    private int getSupplierCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS supplier_count FROM supplier";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("supplier_count");
        }
        return 0;
    }

    private int getBuyerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS buyer_count FROM buyer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("buyer_count");
        }
        return 0;
    }

    private void setBuyerCount(int buyerCount) {
        lblBuyerCount.setText(String.valueOf(buyerCount));
    }



}
