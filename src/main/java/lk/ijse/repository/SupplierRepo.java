package lk.ijse.repository;

import javafx.scene.control.Alert;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierRepo {

    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?);";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, supplier.getSupplierId());
        pstm.setObject(2, supplier.getSupplierName());
        pstm.setObject(3, supplier.getSupplierAddress());
        pstm.setObject(4,supplier.getSupplierContact());
        pstm.setObject(5,supplier.getSupplierGender());

        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchById(String supplierID) throws SQLException {

        String sql = "SELECT * FROM supplier WHERE supplierID = ?";
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplierID);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier Found!").show();
            String supplierId = resultSet.getString(1);
            String supplierName = resultSet.getString(2);
            String supplierAddress = resultSet.getString(3);
            String supplierContact = resultSet.getString(4);
            String supplierGender = resultSet.getString(5);

            return  new Supplier(supplierId, supplierName, supplierAddress, supplierContact, supplierGender);
        }

        return null;
    }
}
