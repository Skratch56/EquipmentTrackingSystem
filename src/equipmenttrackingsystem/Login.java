
package equipmenttrackingsystem;

import equipmenttrackingsystem.employee.Employee;
import org.omg.CORBA.portable.UnknownException;



/**
 *
 * @author Joseph
 */
public class Login {
  private String username,password;
  private int employeeid;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
      
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public int getEmployeeid() {
        return employeeid;
    }

   
   public boolean isLogin (Login objLog) throws UnknownException{
       return LoginModel.isLogin(this);
   }
   public boolean isEmail (Login objLog) throws UnknownException{
       return LoginModel.isEmail(this);
   }
   public boolean isExists(Login objLog) throws UnknownException{
       return LoginModel.isExists(this);
   }
   public Employee returnEmpID (Login objLog) throws UnknownException{
       return LoginModel.returnEmp(this);
   }
     public Employee returnEmp2 (Login objLog) throws UnknownException{
       return LoginModel.returnEmp2(this);
   }
}
