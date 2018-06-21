/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.ppe;

import equipmenttrackingsystem.ppe.*;
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
public class PPEsInCartDA {

    private static ArrayList<PPE> arPPEs;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    // Perform initialisation processing
    //Perform termination processing
    public PPEsInCartDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    static int find(int id) throws DataStorageException, NotFoundException {
        int code = 0;
        String sqlLine = "SELECT * FROM ppe WHERE PPECode= " + id + "";
        ResultSet rsData;

        try {
            Statement stmt = connection.createStatement();
            rsData = stmt.executeQuery(sqlLine);

            while (rsData.next()) {

                code = rsData.getInt("PPECode");
            }

            if (code == 0) {
                throw new NotFoundException(code + "Not found");
            }
        } catch (SQLException e) {
            throw new DataStorageException("Error while closing the connection" + e.getMessage());
        }
        return code;
    }

    static boolean addNewPPE(PPEIsnCart ppe) throws DataStorageException {
        boolean isAdd = false;
        String custQry = "INSERT INTO ppesincart (ppecode,requisitionid,cartid,employeeid) VALUES (?,?,?,?)";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setInt(1, ppe.getPPECode());
            ps.setInt(2, ppe.getRequisitionID());
            ps.setInt(3, ppe.getCartID());
            ps.setInt(4, ppe.getEmployeeID());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "");
        }
        return isAdd;
    }

    static boolean deletePPE(PPEIsnCart ppe) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `ppesincart` where PPECode = " + ppe.getPPECode() + "";
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

    static boolean updatePPE(PPEIsnCart ppe) throws DataStorageException {
        String editQry = "UPDATE `ppesincart` SET ppecode= ?, cartid= ?, employeeid= ? WHERE ppecode=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setInt(1, ppe.getPPECode());
            ps.setInt(2, ppe.getCartID());
            ps.setInt(3, ppe.getEmployeeID());
            ps.setInt(4, ppe.getPPECode());

            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {

        }
        return isUpdated;
    }

    static ArrayList<PPE> getAll() {
        return arPPEs;  //return a shallow copy of the data
    }

    static boolean updatePPEQty(int ppe) throws DataStorageException {
        String editQry = "UPDATE `ppe` set quantityinstock = quantityinstock - 1 WHERE ppecode=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setInt(1, ppe);
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return isUpdated;
    }

    static boolean checkPPEQty(int ppe) throws DataStorageException {
        String editQry = "select qty from ppe where ppecode=" + ppe;
        int qty = 0;
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);

            result = ps.executeQuery();

            while (result.next()) {
                qty = result.getInt("QuantityInStock");
            }
            if (qty > 0) {
                isUpdated = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return isUpdated;
    }

    static ArrayList<PPEIsnCart> getAllPPE() throws DataStorageException, NotFoundException {
        ArrayList<PPEIsnCart> arPPE = new ArrayList<>();

        String selQry = "SELECT * FROM `ppesincart`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arPPE.add(new PPEIsnCart(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4)));
            }
        } catch (SQLException e) {

        }

        return arPPE;
    }
}
