package application.Controllers;

import java.sql.Connection;

import org.apache.commons.dbcp2.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DatabaseConnection {
    private String username;
    private String password;
    private String databaseSource = "jdbc:mysql://localhost:3306/customer";
    private BasicDataSource ds;
    private Connection conn;
    private PreparedStatement mySt;
    private Statement st;
    private ResultSet result;


    public DatabaseConnection(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public Connection getConnection() throws SQLException {
        ds = new BasicDataSource();

        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(databaseSource);

        if (conn == null) {
            conn = ds.getConnection();

        }
        return conn;
    }

    public ResultSet searchForAllCustomers() throws SQLException {
        Connection myConn = getConnection();
        mySt = myConn.prepareStatement("SELECT * FROM Customer");
        result = mySt.executeQuery();

        return result;

    }

    public void deleteCustomer(int id) throws SQLException {

        Connection myConn = getConnection();
        mySt = myConn.prepareStatement("DELETE FROM `customer`.`customer` WHERE id = ?;");
        mySt.setLong(1, id);
        mySt.executeUpdate();

    }

    public void addCustomer(String firstName, String lastName, String email) throws SQLException {
        Connection myConn = getConnection();
        PreparedStatement stmt = myConn.prepareStatement("INSERT INTO `customer`.`customer` (`firstName`, `lastName`, `email`) "
                + "VALUES (?,?,?)");
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, email);
        stmt.executeUpdate();

    }

    public void updateCustomer(String firstName, String lastName, String email, int id) throws SQLException {
        conn = getConnection();

        mySt = conn.prepareStatement("UPDATE customer SET firstName=?, lastName=?, email=? "
                + "WHERE id=?");

        mySt.setString(1, firstName);
        mySt.setString(2, lastName);
        mySt.setString(3, email);
        mySt.setLong(4, id);


        mySt.executeUpdate();

    }

    public ResultSet verifyLogin(String username, String password) throws SQLException {

        mySt = getConnection().prepareStatement("SELECT * FROM users WHERE username=? AND password=?");

        mySt.setString(1, username);
        mySt.setString(2, password);

        ResultSet result = mySt.executeQuery();
        return result;

    }


}
