package lk.ijse;

import javafx.scene.control.Alert;
import lk.ijse.Launcher;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LauncherWrapper {
    public static void main(String[] args) {
        Launcher.main(args);
    }
}
