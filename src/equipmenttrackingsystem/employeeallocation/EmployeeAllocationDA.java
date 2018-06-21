/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.employeeallocation;

import equipmenttrackingsystem.db.CreateConnection;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import equipmenttrackingsystem.exceptions.UnknownException;
import equipmenttrackingsystem.requisition.Requisition;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author CE
 */
public class EmployeeAllocationDA {

    private static ArrayList<EmployeeAllocation> arEmployeeAllocations;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    // Perform initialisation processing
    static void initialse() throws DataStorageException {

        arEmployeeAllocations = new ArrayList();
        try {
            // load the jdbc driver:
            Class.forName("com.mysql.jdbc.Driver");

            // Make the connection to the db:
            //jdbc:mysql://[hostname][:port]/[dbname]
            String url = "jdbc:mysql://localhost/trackingdb";
            String userLogin = "root";
            String password = "";
            connection = DriverManager.getConnection(url, userLogin, password);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "The application is not able to locate " + "the driver for the database - please contact the administrator\n" + e.getMessage());
            terminate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Could not open the connection to the " + "database - please contact the administrator\n" + e.getMessage());
            terminate();
        }

    }

    public EmployeeAllocationDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    //Perform termination processing
    static void terminate() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error closing the connection. " + ex.getMessage());
        }

    }

    static void addNewEmployeeAllocation(EmployeeAllocation employeeallocation) throws DataStorageException {

        String sqlLine = "INSERT INTO `joballocation` (`Date`, `Time`, `RequisitionID`,`EmployeeID`,`CartID`) "
                + "VALUES ('" + employeeallocation.getDate() + "','" + employeeallocation.getTime() + "'," + employeeallocation.getRequisitionID() + "," + employeeallocation.getEmployeeID() + "," + employeeallocation.getCartID() + ");";
        System.out.println(sqlLine);

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlLine);
            stmt.execute();
        } catch (SQLException ex) {
            throw new DataStorageException("Error while closing the connection" + ex.getMessage());
        }
    }

    static void deleteEmployeeAllocation(EmployeeAllocation employeeallocation) throws DataStorageException {

        String sqlLine = "delete from joballocation where AllocationID = " + employeeallocation.getAllocationID() + "";
        System.out.println(sqlLine);

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlLine);
            stmt.execute();
        } catch (SQLException ex) {
            throw new DataStorageException("Error while closing the connection" + ex.getMessage());
        }
    }

    static void updateEmployeeAllocation(EmployeeAllocation employeeallocation) throws DataStorageException {

        String sqlLine = "update joballocation set "
                + "AllocationID=" + employeeallocation.getAllocationID() + ",EmployeeID=" + employeeallocation.getEmployeeID() + " where AllocationID = " + employeeallocation.getAllocationID();
        System.out.println(sqlLine);

        try {
            PreparedStatement stmt = connection.prepareStatement(sqlLine);
            stmt.execute();
        } catch (SQLException ex) {
            throw new DataStorageException("Error while closing the connection" + ex.getMessage());
        }
    }

    static ArrayList<EmployeeAllocation> getAll() {
        return arEmployeeAllocations;  //return a shallow copy of the data
    }

    static ArrayList<EmployeeAllocation> getAllEmployeeAllocation() throws DataStorageException, NotFoundException {
        String sqlLine = "SELECT * FROM joballocation";
        ResultSet rsData;
        EmployeeAllocation objEmployeeAllocation;

        ArrayList<EmployeeAllocation> arEmployeeAllocation = new ArrayList<EmployeeAllocation>();
        try {

            Statement stmt = connection.createStatement();
            rsData = stmt.executeQuery(sqlLine);

            while (rsData.next()) {
                objEmployeeAllocation = new EmployeeAllocation(rsData.getInt("AllocationID"),
                        rsData.getInt("EmployeeID"), rsData.getString("Date"), rsData.getInt("RequisitionID"), rsData.getString("Time"), rsData.getInt("CartID"));
                arEmployeeAllocation.add(objEmployeeAllocation);

            }
        } catch (SQLException e) {
            throw new DataStorageException("Error while closing the connection" + e.getMessage());
        }

        return arEmployeeAllocation;
    }
    static ArrayList<EmployeeAllocation> getAllEmployeeAllocation2(int ID) throws DataStorageException, NotFoundException {
        String sqlLine = "SELECT * FROM joballocation where EmployeeID="+ID;
        ResultSet rsData;
        EmployeeAllocation objEmployeeAllocation;

        ArrayList<EmployeeAllocation> arEmployeeAllocation = new ArrayList<EmployeeAllocation>();
        try {

            Statement stmt = connection.createStatement();
            rsData = stmt.executeQuery(sqlLine);

            while (rsData.next()) {
                objEmployeeAllocation = new EmployeeAllocation(rsData.getInt("AllocationID"),
                        rsData.getInt("EmployeeID"), rsData.getString("Date"), rsData.getInt("RequisitionID"), rsData.getString("Time"), rsData.getInt("CartID"));
                arEmployeeAllocation.add(objEmployeeAllocation);

            }
        } catch (SQLException e) {
            throw new DataStorageException("Error while closing the connection" + e.getMessage());
        }

        return arEmployeeAllocation;
    }

    public static int getLastAddedID() throws UnknownException {
        int arCust = 0;
        String selQry = "SELECT * FROM joballocation ORDER BY AllocationID DESC LIMIT 1";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getInt("AllocationID");
            }
        } catch (SQLException e) {
            throw new UnknownException(e.getMessage());
        }
        return arCust;
    }
     public static boolean updateRequisition(int requisition) throws DataStorageException {
        String editQry = "UPDATE `materialrequisition` SET status= 'Complete' WHERE requisitionid="+requisition;
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);

            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {

        }
        return isUpdated;
    }
}
