/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.requisition;

import equipmenttrackingsystem.employee.*;
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
public class RequisitionDA {

    private static ArrayList<Requisition> arRequisitions;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    // Perform initialisation processing
    //Perform termination processing
    public RequisitionDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    static boolean addNewRequisition(Requisition requisition) throws DataStorageException {
        boolean isAdd = false;
        String custQry = "INSERT INTO requisition (requisitionid,ppecode,date) VALUES (?,?,?)";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setInt(1, requisition.getRequisitionID());
            ps.setInt(2, requisition.getPpeCode());
            ps.setString(3, requisition.getRequisitionDate());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return isAdd;
    }

    static boolean deleteRequisition(Requisition requisition) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `requisition` where requisitionid = " + requisition.getRequisitionID() + " and ppecode = " + requisition.getPpeCode() + "";
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

    static boolean updateRequisition(Requisition requisition) throws DataStorageException {
        String editQry = "UPDATE `materialrequisition` SET status= 'Complete' WHERE requisitionid=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);

            ps.setInt(1, requisition.getRequisitionID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {

        }
        return isUpdated;
    }

    static ArrayList<Requisition> getAll() {
        return arRequisitions;  //return a shallow copy of the data
    }

    static ArrayList<Requisition> getAllRequisition() throws DataStorageException, NotFoundException {
        ArrayList<Requisition> arRequisition = new ArrayList<>();

        String selQry = "SELECT * FROM `requisition`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arRequisition.add(new Requisition(result.getInt(1), result.getInt(2), result.getString(3)));
            }
        } catch (SQLException e) {

        }

        return arRequisition;
    }
}
