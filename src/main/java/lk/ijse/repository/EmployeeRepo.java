package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getEmployeeName());
        pstm.setObject(3, employee.getEmployeeAddress());
        pstm.setObject(4, employee.getEmployeeContact());
        pstm.setObject(5,employee.getEmployeeSalary());
        pstm.setObject(6,employee.getVehicleNo());

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";

        List<Employee> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));
        }
        return data;

    }
}
