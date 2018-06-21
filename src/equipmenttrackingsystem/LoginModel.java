package equipmenttrackingsystem;

import equipmenttrackingsystem.carts.Cart;
import equipmenttrackingsystem.db.CreateConnection;
import equipmenttrackingsystem.employee.Employee;
import equipmenttrackingsystem.exceptions.DataStorageException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import org.omg.CORBA.portable.UnknownException;

/**
 *
 * @author Joseph
 */
public class LoginModel {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public LoginModel() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param login
     * @return true if logged in successful otherwise return false for every
     * failed logged in
     * @throws UnknownException
     */
    public static boolean isLogin(Login login) throws UnknownException {
        boolean res = false;
        String loginQry = "select * from employee where Binary userlogin= '" + login.getUsername() + "' AND Binary password= '" + login.getPassword() + "'";
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                res = true;
            }
        } catch (SQLException e) {

        }

        return res;

    }
     public static boolean isEmail(Login login) throws UnknownException {
        boolean res = false;
        String loginQry = "select * from employee where Binary userlogin= '" + login.getUsername() + "'";
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                res = true;
            }
        } catch (SQLException e) {

        }

        return res;

    }
     public static boolean isExists(Login login) throws UnknownException {
        boolean res = false;
        String loginQry = "select * from employee where Binary userlogin= '" + login.getUsername() + "' and employeeid="+login.getEmployeeid();
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                res = true;
            }
        } catch (SQLException e) {

        }

        return res;

    }
      public static boolean isPassword(Login login) throws UnknownException {
        boolean res = false;
        String loginQry = "select * from employee where Binary userlogin= '" + login.getUsername() + "' AND Binary password= '" + login.getPassword() + "'";
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                res = true;
            }
        } catch (SQLException e) {

        }

        return res;

    }

    public static Employee returnEmp(Login login) throws UnknownException {
        Employee emp = new Employee();

        String loginQry = "select * from employee where userlogin= '" + login.getUsername() + "' AND password= '" + login.getPassword() + "'";
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                emp = new Employee(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6));

            }
        } catch (SQLException e) {

        }

        return emp;

    }
    public static Employee returnEmp2(Login login) throws UnknownException {
        Employee emp = new Employee();

        String loginQry = "select * from employee where userlogin= '" + login.getUsername() + "' AND employeeid= " + login.getEmployeeid() + "";
        try {
            st = connection.createStatement();
            result = st.executeQuery(loginQry);

            if (result.next()) {
                emp = new Employee(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6));

            }
        } catch (SQLException e) {

        }

        return emp;

    }

    public static boolean addLog(int Employeeid, String Date, String Time) throws DataStorageException {
        boolean isAdd = false;
        String custQry = "INSERT INTO log (EmployeeID,Date,Time) VALUES (?,?,?)";
        try {
            ps = connection.prepareStatement(custQry);
            ps.setInt(1, Employeeid);
            ps.setString(2, Date);
            ps.setString(3, Time);

            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException e) {

        }
        return isAdd;
    }

}
