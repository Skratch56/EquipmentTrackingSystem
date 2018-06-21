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
public class Log {

    int LogID, EmployeeID;
    String Date, Time;

    public Log() {
        this(0, 0, "", "");
    }

    public Log(int LogID, int EmployeeID, String Date, String Time) {
        this.LogID = LogID;
        this.EmployeeID = EmployeeID;
        this.Date = Date;
        this.Time = Time;
    }

    public int getLogID() {
        return LogID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public void setLogID(int LogID) {
        this.LogID = LogID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    @Override
    public String toString() {
        return "Log{" + "LogID=" + LogID + ", EmployeeID=" + EmployeeID + ", Date=" + Date + ", Time=" + Time + '}';
    }

}
