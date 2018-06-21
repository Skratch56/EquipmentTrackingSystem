/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.employee;

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
public class LogDA {

    private static ArrayList<Log> arEmployees;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    // Perform initialisation processing
    //Perform termination processing
    public LogDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<Log> getAllLogs() throws DataStorageException, NotFoundException {
        ArrayList<Log> arEmployee = new ArrayList<>();

        String selQry = "SELECT * FROM `log` ORDER BY Date, Time DESC";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arEmployee.add(new Log(result.getInt(1), result.getInt(2), result.getString(3), result.getString(4)));
            }
        } catch (SQLException e) {

        }

        return arEmployee;
    }

    public static String getEmployeeName(int id) throws UnknownException {
        String arCust = "";
        String selQry = "SELECT surname,employeename FROM employee where employeeid=" + id;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getString("employeename") + " " + result.getString("surname");
            }
        } catch (SQLException e) {
        
        }
        return arCust;
    }
    public static String getEmployeeType(int id) throws UnknownException {
        String arCust = "";
        String selQry = "SELECT Type FROM employee where employeeid=" + id;

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arCust = result.getString("Type");
            }
        } catch (SQLException e) {
        
        }
        return arCust;
    }

    static ArrayList<Log> getAll() {
        return arEmployees;  //return a shallow copy of the data
    }

    public static ArrayList<Employee> getAllEmployee() throws DataStorageException, NotFoundException {
        ArrayList<Employee> arEmployee = new ArrayList<>();

        String selQry = "SELECT * FROM `employee`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arEmployee.add(new Employee(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6)));
            }
        } catch (SQLException e) {

        }

        return arEmployee;
    }

    public static ArrayList<Employee> getAllEmployee2() throws DataStorageException, NotFoundException {
        ArrayList<Employee> arEmployee = new ArrayList<>();

        String selQry = "SELECT * FROM `employee`";

        try {
            ps = connection.prepareStatement(selQry);
            result = ps.executeQuery();

            while (result.next()) {
                arEmployee.add(new Employee(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6)));
            }
        } catch (SQLException e) {

        }

        return arEmployee;
    }
}
