package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order;
import lk.ijse.model.Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepo {
    public static boolean save(Stock stock) throws SQLException {
        String sql = "insert into stock values(?,?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,stock.getStockId());
        pstm.setObject(2,stock.getWeight());
        pstm.setObject(3,stock.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static List<Stock> getAll() throws SQLException {
        String sql = "select * from stock";

        List<Stock> data = new ArrayList<Stock>();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            data.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getDate(3)
            ));
        }
        return data;
    }

    public static boolean updateWeight(String stockID, String supplierID, Double weight) throws SQLException {
        String sql = "update stock set TotalWeight = TotalWeight + ? where stockID = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1,weight);
        pstm.setObject(2,stockID);

        return pstm.executeUpdate() > 0;
    }
}
