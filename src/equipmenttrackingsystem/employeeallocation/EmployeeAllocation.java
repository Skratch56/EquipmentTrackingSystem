/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.employeeallocation;

import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;

/**
 *
 * @author CE
 */
public class EmployeeAllocation {

    int AllocationID, EmployeeID,CartID,RequisitionID;
    String Date,Time;

    public EmployeeAllocation() {
        this(0, 0,"",0,"",0);
    }

    public EmployeeAllocation(int AllocationID, int EmployeeID,String Date,int RequisitionID,String Time,int CartID) {
        this.AllocationID = AllocationID;
        this.EmployeeID = EmployeeID;
        this.Date = Date;
        this.Time = Time;
        this.RequisitionID = RequisitionID;
        this.CartID = CartID;
    }

    public int getCartID() {
        return CartID;
    }

    public void setRequisitionID(int RequisitionID) {
        this.RequisitionID = RequisitionID;
    }

    public void setCartID(int CartID) {
        this.CartID = CartID;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public void setAllocationID(int AllocationID) {
        this.AllocationID = AllocationID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public int getAllocationID() {
        return AllocationID;
    }

    public int getRequisitionID() {
        return RequisitionID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }
 public static void init() throws DataStorageException {
        EmployeeAllocationDA.initialse();
    }
    public static void terminate() throws DataStorageException {
        EmployeeAllocationDA.terminate();
    }

    public void addNewTag() throws DataStorageException {
        EmployeeAllocationDA.addNewEmployeeAllocation(this);
    }

    public void deleteTag() throws DataStorageException {
        EmployeeAllocationDA.deleteEmployeeAllocation(this);
    }

    public void updateTag() throws DataStorageException {
        EmployeeAllocationDA.updateEmployeeAllocation(this);
    }

    public static java.util.ArrayList<EmployeeAllocation> getAll() {
        return EmployeeAllocationDA.getAll();
    }

   

    public static java.util.ArrayList<EmployeeAllocation> getAllTag() throws DataStorageException, NotFoundException {
        return EmployeeAllocationDA.getAllEmployeeAllocation();
    }
       public static java.util.ArrayList<EmployeeAllocation> getAllTag2(int id) throws DataStorageException, NotFoundException {
        return EmployeeAllocationDA.getAllEmployeeAllocation2(id);
    }

    @Override
    public String toString() {
        return "EmployeeAllocation{" + "AllocationID=" + AllocationID + ", EmployeeID=" + EmployeeID + ", CartID=" + CartID + ", RequisitionID=" + RequisitionID + ", Date=" + Date + ", Time=" + Time + '}';
    }
}
