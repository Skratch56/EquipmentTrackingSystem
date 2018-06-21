/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.carts;

import equipmenttrackingsystem.carts.*;
import equipmenttrackingsystem.db.CreateConnection;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.omg.CORBA.portable.UnknownException;

/**
 *
 * @author CE
 */
public class CartDA {

    private static ArrayList<Cart> arCarts;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    // Perform initialisation processing
    //Perform termination processing
    public CartDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    static int find(int id) throws DataStorageException, NotFoundException {
        int code = 0;
        String sqlLine = "SELECT * FROM cart WHERE CartID= " + id + "";
        ResultSet rsData;

        try {
            Statement stmt = connection.createStatement();
            rsData = stmt.executeQuery(sqlLine);

            while (rsData.next()) {

                code = rsData.getInt("CartID");
            }

            if (code == 0) {
                throw new NotFoundException(code + "Not found");
            }
        } catch (SQLException e) {
            throw new DataStorageException("Error while closing the connection" + e.getMessage());
        }
        return code;
    }

    static boolean addNewCart(Cart cart) throws DataStorageException {
        boolean isAdd = false;
        String custQry = "INSERT INTO cart (BatteryLife,LocationID) VALUES (?,?)";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setString(1, cart.getBatteryLife());
            ps.setInt(2, cart.getLocationID());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {

        }
        return isAdd;
    }

    static boolean deleteCart(Cart cart) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `cart` where cartid = " + cart.getCartID() + "";
        System.out.println(sqlLine);

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlLine);
            stmt.execute();
            isDel = true;
        } catch (SQLException ex) {
            throw new DataStorageException("Error while closing the connection" + ex.getMessage());
        }
        return isDel;
    }

    static boolean updateCart(Cart cart) throws DataStorageException {
        String editQry = "UPDATE `cart` SET batterylife= ?, locationid= ? WHERE cartid=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, cart.getBatteryLife());
            ps.setInt(2, cart.getLocationID());
            ps.setInt(3, cart.getCartID());

            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {

        }
        return isUpdated;
    }

    static ArrayList<Cart> getAll() {
        return arCarts;  //return a shallow copy of the data
    }

    public static ArrayList<Cart> getAllCart() throws DataStorageException, NotFoundException {
        ArrayList<Cart> arCart = new ArrayList<>();

        String selQry = "SELECT * FROM `cart`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCart.add(new Cart(result.getInt(1), result.getString(2), result.getInt(3)));
            }
        } catch (SQLException e) {

        }

        return arCart;
    }

    public static ArrayList<String> getLocation() throws UnknownException {
        ArrayList<String> arLoc = new ArrayList<>();
        String selQry = "SELECT locationname FROM location";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arLoc.add(result.getString("locationname"));
            }
        } catch (SQLException e) {

        }
        return arLoc;
    }

    public static int getLocationID(String regNo) throws UnknownException {
        int id = 0;
        String selQry = "SELECT locationid FROM location WHERE locationname=?";
        System.out.println(selQry);
        
        try {
            ps = connection.prepareStatement(selQry);
            ps.setString(1, regNo);
            result = ps.executeQuery();
     
            if (result.next()) {
                id = result.getInt("locationid");
            }
        } catch (SQLException e) {
           // JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }
}
