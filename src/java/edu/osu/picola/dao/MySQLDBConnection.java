package edu.osu.picola.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class connects to the MySQL db
 *
 * @author akers.79
 */
public class MySQLDBConnection {

    private static final String DRIVER_CLASS = "jdbc.driver";
    private static final String DB_URL = "jdbc.url";
    private static final String USER_NAME = "jdbc.username";
    private static final String PASSWORD = "jdbc.password";
    private static Connection conn;
    private static MySQLDBConnection instance;

    public static Connection getConnection() {
        if (instance == null) {
            instance = new MySQLDBConnection();
        }
        return conn;
    }

    private MySQLDBConnection() {
        try {
//			Properties props = new Properties();
//			FileInputStream in = new FileInputStream(
//					"conf\\db.props");
//			props.load(in);
//			in.close();
//			String driver = props.getProperty(DRIVER_CLASS);
//			String url = props.getProperty(DB_URL);
//			String username = props.getProperty(USER_NAME);
//			String password = props.getProperty(PASSWORD);
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://164.107.127.203:3306/picola";
            String username = "root";
            String password = "CSE5911group4";
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to locate " + DRIVER_CLASS + " driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to establish connection");
            e.printStackTrace();
        }
    }
}
