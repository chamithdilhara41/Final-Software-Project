package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepo {
    public static boolean save(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock VALUES(?,?,?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,stock.getStockId());
        pstm.setObject(2,stock.getTotalWeight());
        pstm.setObject(3,stock.getQuantity());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Stock stock) throws SQLException {
        String sql = "UPDATE stock SET totalWeight = ?, quantity = ? WHERE stockId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,stock.getTotalWeight());
        pstm.setObject(2,stock.getQuantity());
        pstm.setObject(3,stock.getStockId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String stockID) throws SQLException {

        String sql = "DELETE FROM stock WHERE stockId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,stockID);

        return pstm.executeUpdate() > 0;
    }

    public static Stock searchById(String stockID) throws SQLException {
        String sql = "SELECT * FROM stock WHERE stockId = ?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,stockID);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            String stockId = resultSet.getString(1);
            double totalWeight = resultSet.getDouble(2);
            Integer quantity = resultSet.getInt(3);

            return new Stock(stockId,totalWeight,quantity);
        }
    return null;
    }

    public static List<Stock> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM stock;";
        List<Stock> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getInt(3)
            ));
        }
        return data;

    }
}
