package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Buyer;
import lk.ijse.model.Employee;
import lk.ijse.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepo {

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES (?,?,?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, order.getOrderId());
        pstm.setObject(2, order.getBuyerId());
        pstm.setObject(3,order.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Order order) throws SQLException {
        String sql = "UPDATE orders SET buyerId = ?, date = ? WHERE orderId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, order.getBuyerId());
        pstm.setObject(2, order.getDate());
        pstm.setObject(3, order.getOrderId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String orderID) throws SQLException {
        String sql = "DELETE FROM orders WHERE orderId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, orderID);

        return pstm.executeUpdate() > 0;
    }

    public static Order searchByOrderId(String orderID) throws SQLException {
        String sql = "SELECT * FROM orders WHERE orderId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, orderID);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String orderId = resultSet.getString(1);
            String buyerId = resultSet.getString(2);
            String date = String.valueOf(resultSet.getDate(3));

            return new Order(orderId,buyerId,date);
        }
        return null;
    }

    public static List<Order> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM orders";

        List<Order> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT orderId FROM orders";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}
