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
public class EmployeeDA {

    private static ArrayList<Employee> arEmployees;
    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    // Perform initialisation processing
    //Perform termination processing
    public EmployeeDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    static int find(int id) throws DataStorageException, NotFoundException {
        int code = 0;
        String sqlLine = "SELECT * FROM employee WHERE EmployeeID= " + id + "";
        ResultSet rsData;

        try {
            Statement stmt = connection.createStatement();
            rsData = stmt.executeQuery(sqlLine);

            while (rsData.next()) {

                code = rsData.getInt("EmployeeID");
            }

            if (code == 0) {
                throw new NotFoundException(code + "Not found");
            }
        } catch (SQLException e) {
            throw new DataStorageException("Error while closing the connection" + e.getMessage());
        }
        return code;
    }

    static boolean addNewEmployee(Employee employee) throws DataStorageException {
        boolean isAdd = false;
        String custQry = "INSERT INTO employee (Surname,EmployeeName,UserLogin,Type,Password) VALUES (?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setString(1, employee.getSurname());
            ps.setString(2, employee.getEmployeeName());
            ps.setString(3, employee.getUserLogin());
            ps.setString(4, employee.getType());
            ps.setString(5, employee.getPassword());
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {

        }
        return isAdd;
    }

    static boolean deleteEmployee(Employee employee) throws DataStorageException {
        boolean isDel = false;
        String sqlLine = "delete from `employee` where employeeid = " + employee.getEmployeeID() + "";
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

    static boolean updateEmployee(Employee employee) throws DataStorageException {
        String editQry = "UPDATE `employee` SET surname= ?, employeename= ?, userlogin= ?, type= ?, password= ? WHERE employeeid=?";
        boolean isUpdated = false;
        try {
            ps = connection.prepareStatement(editQry);
            ps.setString(1, employee.getSurname());
            ps.setString(2, employee.getEmployeeName());
            ps.setString(3, employee.getUserLogin());
            ps.setString(4, employee.getType());
            ps.setString(5, employee.getPassword());
            ps.setInt(6, employee.getEmployeeID());
            ps.executeUpdate();
            isUpdated = true;
        } catch (SQLException e) {

        }
        return isUpdated;
    }

    static ArrayList<Employee> getAll() {
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
