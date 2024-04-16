package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Buyer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerRepo {
    public static boolean save(Buyer buyer) throws SQLException {
        String sql = "INSERT INTO buyer VALUES(?, ?, ?, ?, ?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, buyer.getBuyerId());
        pstm.setObject(2, buyer.getBuyerName());
        pstm.setObject(3, buyer.getBuyerAddress());
        pstm.setObject(4,buyer.getBuyerContactOffice());
        pstm.setObject(5, buyer.getBuyerContactManager());

        return pstm.executeUpdate() > 0;
    }

    public static List<Buyer> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM buyer";

        List<Buyer> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Buyer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static Buyer searchById(String buyerID) throws SQLException {
        String sql = "SELECT * FROM buyer WHERE buyerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, buyerID);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String buyerId = resultSet.getString(1);
            String buyerName = resultSet.getString(2);
            String buyerAddress = resultSet.getString(3);
            String buyerContactOffice = resultSet.getString(4);
            String buyerContactManager = resultSet.getString(5);

            return new Buyer(buyerID,buyerName,buyerAddress,buyerContactOffice,buyerContactManager);
        }
        return null;
    }

    public static boolean update(Buyer buyer) throws SQLException {
        String sql = "UPDATE buyer SET name = ?, address = ?, contactOffice = ?, contactManager = ? WHERE buyerId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, buyer.getBuyerName());
        pstm.setObject(2, buyer.getBuyerAddress());
        pstm.setObject(3, buyer.getBuyerContactOffice());
        pstm.setObject(4, buyer.getBuyerContactManager());
        pstm.setObject(5, buyer.getBuyerId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String buyerID) throws SQLException {
        String sql = "DELETE FROM buyer WHERE buyerId = ?;";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, buyerID);

        return pstm.executeUpdate() > 0;
    }
}