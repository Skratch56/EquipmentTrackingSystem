/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.employee;

import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;

/**
 *
 * @author CE
 */
public class Employee {

    int EmployeeID;
    String Surname, EmployeeName, UserLogin, Type, Password;

    public Employee() {
        this(0, null, null, null, null, null);

    }

    public Employee(int EmployeeID, String Surname, String EmployeeName, String UserLogin, String Type, String Password) {
        this.EmployeeID = EmployeeID;
        this.Surname = Surname;
        this.EmployeeName = EmployeeName;
        this.UserLogin = UserLogin;
        this.Type = Type;
        this.Password = Password;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

    public void setUserLogin(String UserLogin) {
        this.UserLogin = UserLogin;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public String getSurname() {
        return Surname;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public String getUserLogin() {
        return UserLogin;
    }

    public String getType() {
        return Type;
    }

    public String getPassword() {
        return Password;
    }


    /**
     * Terminate the data storage
     *
     * @throws DataStorageException This exception is raised when there was a
     * problem saving to the data storage device
     */


    public  boolean addNewEmployee() throws DataStorageException {
        return EmployeeDA.addNewEmployee(this);
    }

    public  boolean deleteEmployee() throws DataStorageException {
        return EmployeeDA.deleteEmployee(this);
    }

    public  boolean updateEmployee() throws DataStorageException {
        return EmployeeDA.updateEmployee(this);
    }

    public static java.util.ArrayList<Employee> getAll() {
        return EmployeeDA.getAll();
    }

    public static int find(int id) throws DataStorageException, NotFoundException {
        return EmployeeDA.find(id);
    }

    public static java.util.ArrayList<Employee> getAllEmployee() throws DataStorageException, NotFoundException {
        return EmployeeDA.getAllEmployee();
    }
}
