/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.requisition;

import equipmenttrackingsystem.carts.Cart;
import equipmenttrackingsystem.employee.*;
import equipmenttrackingsystem.db.CreateConnection;
import equipmenttrackingsystem.employeeallocation.EmployeeAllocation;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import equipmenttrackingsystem.ppe.PPE;
import equipmenttrackingsystem.ppe.PPEIsnCart;
import equipmenttrackingsystem.timestamp.TimeStamp;
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
public class MaterialRequisitionDA {

    private static ArrayList<MaterialRequisition> arRequisitions;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;
    private static PreparedStatement ps2;
    private static ResultSet result2;

    // Perform initialisation processing
    //Perform termination processing
    public MaterialRequisitionDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    static boolean addNewRequisition(MaterialRequisition requisition) throws DataStorageException {
        boolean isAdd = false;
        String custQry = "INSERT INTO materialrequisition (requisitionname,requisitiondate,status,employeeid) VALUES (?,?,?,?)";
        try {
            ps = connection.prepareStatement(custQry);

            ps.setString(1, requisition.getRequisitionName());
            ps.setString(2, requisition.getRequisitionDate());
            ps.setString(3, requisition.getStatus());
            ps.setInt(4, requisition.getEmployeeID());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return isAdd;
    }

    static boolean deleteRequisition(MaterialRequisition requisition) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `materialrequisition` where requisitionid = " + requisition.getRequisitionID() + "";
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

    static boolean updateRequisition(MaterialRequisition requisition) throws DataStorageException {
        String editQry = "UPDATE `materialrequisition` SET  requisitionname= ?, requisitiondate= ?, status= ?, employeeid= ? WHERE requisitionid=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);

            ps.setString(1, requisition.getRequisitionName());
            ps.setString(2, requisition.getRequisitionDate());
            ps.setString(3, requisition.getStatus());
            ps.setInt(4, requisition.getEmployeeID());
            ps.setInt(5, requisition.getRequisitionID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {

        }
        return isUpdated;
    }

    static ArrayList<MaterialRequisition> getAll() {
        return arRequisitions;  //return a shallow copy of the data
    }

    static ArrayList<MaterialRequisition> getAllRequisition() throws DataStorageException, NotFoundException {
        ArrayList<MaterialRequisition> arRequisition = new ArrayList<>();

        String selQry = "SELECT * FROM `materialrequisition`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arRequisition.add(new MaterialRequisition(result.getInt(1), result.getInt(5), result.getString(2), result.getString(3), result.getString(4)));
            }
        } catch (SQLException e) {

        }

        return arRequisition;
    }

    static ArrayList<MaterialRequisition> getAllRequisition2() throws DataStorageException, NotFoundException {
        ArrayList<MaterialRequisition> arRequisition = new ArrayList<>();

        String selQry = "SELECT * FROM `materialrequisition` where status='Pending' Or status='Approved'";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arRequisition.add(new MaterialRequisition(result.getInt(1), result.getInt(5), result.getString(2), result.getString(3), result.getString(4)));
            }
        } catch (SQLException e) {

        }

        return arRequisition;
    }

    static ArrayList<MaterialRequisition> getAllRequisition3() throws DataStorageException, NotFoundException {
        ArrayList<MaterialRequisition> arRequisition = new ArrayList<>();

        String selQry = "SELECT * FROM `materialrequisition` where status='Complete'";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arRequisition.add(new MaterialRequisition(result.getInt(1), result.getInt(5), result.getString(2), result.getString(3), result.getString(4)));
            }
        } catch (SQLException e) {

        }

        return arRequisition;
    }

    static ArrayList<MaterialRequisition> getAllRequisition4(int id) throws DataStorageException, NotFoundException {
        ArrayList<MaterialRequisition> arRequisition = new ArrayList<>();

        String selQry = "SELECT * FROM `joballocation` where EmployeeID=" + id;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();
            System.out.println(selQry);
            while (result.next()) {
                String selQry2 = "SELECT * FROM `materialrequisition` where RequisitionID=" + result.getInt(4);
                System.out.println(selQry2);
                ps2 = connection.prepareStatement(selQry2);
                result2 = ps2.executeQuery();
                while (result2.next()) {
                    arRequisition.add(new MaterialRequisition(result2.getInt(1), result2.getInt(5), result2.getString(2), result2.getString(3), result2.getString(4)));
                }
            }

        } catch (SQLException e) {

        }

        return arRequisition;
    }

    public static ArrayList<String> getRequisitionID() throws UnknownException {
        ArrayList<String> arCust = new ArrayList<>();
        String selQry = "SELECT requisitionid FROM materialrequisition";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust.add(result.getString("requisitionid"));
            }
        } catch (SQLException e) {

        }
        return arCust;
    }

    public static int getLastAddedID() throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT * FROM materialrequisition ORDER BY requisitionid DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("requisitionid");
            }
        } catch (SQLException e) {

        }
        return arCust;
    }

    public static String getEmployeeName(int EmployeeID) throws UnknownException {
        String arCust = "Not Found";
        String selQry = "SELECT * FROM employee where employeeid=" + EmployeeID;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getString("surname") + " " + result.getString("employeename");
            }
        } catch (SQLException e) {

        }
        return arCust;
    }

    public static ArrayList<PPE> getAllPPE(int reqID) throws DataStorageException, NotFoundException {
        ArrayList<PPE> arPPE = new ArrayList<>();

        String selQry = "SELECT * FROM `ppesincart` where requisitionid=" + reqID;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                String selQry2 = "SELECT * FROM `ppe` where ppecode=" + result.getInt(1);
                ps2 = connection.prepareStatement(selQry2);
                result2 = ps2.executeQuery();
                while (result2.next()) {
                    arPPE.add(new PPE(result2.getInt(1), result2.getString(2), result2.getString(3), result2.getString(4), result2.getInt(5), null));
                }
            }
        } catch (SQLException e) {

        }

        return arPPE;
    }

    public static int getCart(int reqID) throws DataStorageException, NotFoundException {
        int cart = 0;

        String selQry = "SELECT * FROM `ppesincart` where requisitionid=" + reqID;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                cart = result.getInt(3);

            }
        } catch (SQLException e) {

        }

        return cart;
    }

    public static Cart getCartLocation(int cartid) throws DataStorageException, NotFoundException {
        Cart cart = new Cart();

        String selQry = "SELECT * FROM `cart` where cartid=" + cartid;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                cart = new Cart(result.getInt(1), result.getString(2), result.getInt(3));

            }
        } catch (SQLException e) {

        }

        return cart;
    }

    public static String getLocation(int locationid) throws DataStorageException, NotFoundException {
        String location = "";

        String selQry = "SELECT * FROM `location` where locationid=" + locationid;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                location = result.getString(3);

            }
        } catch (SQLException e) {

        }

        return location;
    }

    public static EmployeeAllocation getAllocation(int reqid) throws DataStorageException, NotFoundException {
        EmployeeAllocation aloc = new EmployeeAllocation();

        String selQry = "SELECT * FROM `joballocation` where RequisitionID=" + reqid;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                aloc = new EmployeeAllocation(result.getInt(1), result.getInt(5), result.getString(2), result.getInt(4), result.getString(3), result.getInt(6));

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return aloc;
    }

    public static EmployeeAllocation getAllocation2(int reqid) throws DataStorageException, NotFoundException {
        EmployeeAllocation aloc = new EmployeeAllocation();

        String selQry = "SELECT * FROM `joballocation` where employeeid=" + reqid;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                aloc = new EmployeeAllocation(result.getInt(1), result.getInt(5), result.getString(2), result.getInt(4), result.getString(3), result.getInt(6));

            }
        } catch (SQLException e) {

        }

        return aloc;
    }

    public static TimeStamp getTimeStamp(int allocaid) throws DataStorageException, NotFoundException {
        TimeStamp aloc = new TimeStamp();

        String selQry = "SELECT * FROM `TimeStamp` where AllocationID=" + allocaid;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                aloc = new TimeStamp(result.getInt(1), result.getInt(2), result.getInt(3));

            }
        } catch (SQLException e) {

        }

        return aloc;
    }

}
