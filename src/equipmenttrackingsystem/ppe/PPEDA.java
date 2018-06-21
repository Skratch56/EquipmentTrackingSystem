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
public class PPEDA {

    private static ArrayList<PPE> arPPEs;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;
    static ArrayList<PPE> arPPEss;

    // Perform initialisation processing
    //Perform termination processing
    public PPEDA() {
        try {
            arPPEss = new ArrayList<>();
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

    static boolean addNewPPE(PPE ppe) throws DataStorageException {
        boolean isAdd = false;
        String custQry = "INSERT INTO ppe (type,mass,equipmentname,quantityinstock) VALUES (?,?,?,?)";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setString(1, ppe.getType());
            ps.setString(2, ppe.getMass());
            ps.setString(3, ppe.getEquipmentName());
            ps.setInt(4, ppe.getQuantityInStock());

            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e + "");
        }
        return isAdd;
    }

    static boolean deletePPE(PPE ppe) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `ppe` where PPECode = " + ppe.getPPECode() + "";
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

    static boolean updatePPE(PPE ppe) throws DataStorageException {
        String editQry = "UPDATE `ppe` SET type= ?, mass= ?, equipmentname= ?, quantityinstock= ? WHERE ppecode=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, ppe.getType());
            ps.setString(2, ppe.getMass());
            ps.setString(3, ppe.getEquipmentName());
            ps.setInt(4, ppe.getQuantityInStock());
            ps.setInt(5, ppe.getPPECode());

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
        String editQry = "select quantityinstock from ppe where ppecode=" + ppe;
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

    static ArrayList<PPE> getAllPPE() throws DataStorageException, NotFoundException {
        ArrayList<PPE> arPPE = new ArrayList<>();

        String selQry = "SELECT * FROM `ppe`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arPPE.add(new PPE(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), null));
            }
        } catch (SQLException e) {

        }

        return arPPE;
    }
    
    static ArrayList<PPE> getAllPPE2(String status) throws DataStorageException, NotFoundException {
        ArrayList<PPE> arPPE = new ArrayList<>();

        String selQry = "SELECT * FROM `ppe` where type='"+ status + "'";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arPPE.add(new PPE(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), null));
            }
        } catch (SQLException e) {

        }

        return arPPE;
    }

   public static PPE getPPE2(String ppecode) throws DataStorageException, NotFoundException {
        PPE arPPE = new PPE();

        String selQry = "SELECT * FROM `ppe` where ppecode=" + ppecode;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arPPE = new PPE(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), null);
            }
        } catch (SQLException e) {

        }

        return arPPE;
    }
    public static ArrayList<String> getPPE(String ppecode) throws DataStorageException, NotFoundException {
        ArrayList<String> arPPE = new ArrayList<>();

        String selQry = "SELECT * FROM `requisition` where requisitionid=" + ppecode;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arPPE.add(result.getString("ppecode"));
            }
        } catch (SQLException e) {

        }

        return arPPE;
    }
     public static int getCountReq(String ppecode) throws DataStorageException, NotFoundException {
        int arPPE = 0;

        String selQry = "SELECT * FROM `requisition` where requisitionid=" + ppecode;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arPPE +=1;
            }
        } catch (SQLException e) {

        }

        return arPPE;
    }

    static ArrayList<PPE> getPPE(int ppecode) throws DataStorageException, NotFoundException {

        String selQry = "SELECT * FROM `ppe` where ppecode=" + ppecode;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arPPEss.add(new PPE(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), null));
            }
        } catch (SQLException e) {

        }

        return arPPEss;
    }
}
