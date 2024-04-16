package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Supplier;
import lk.ijse.model.Vehicle;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleRepo {

    public static boolean save(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicle VALUES(?, ?);";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, vehicle.getVehicleNo());
        pstm.setObject(2, vehicle.getVehicleType());

        return pstm.executeUpdate() > 0;
    }
}
