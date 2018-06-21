/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.requisition;

import equipmenttrackingsystem.employee.*;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import javafx.scene.control.CheckBox;

/**
 *
 * @author CE
 */
public class MaterialRequisition {

    int requisitionID, employeeID;
    String requisitionType, requisitionName, requisitionDate, status;

    public MaterialRequisition() {
        this(0, 0, "", "", "");
    }

    public MaterialRequisition(int requisitionID, int employeeID, String requisitionName, String requisitionDate, String status) {
        this.requisitionID = requisitionID;
        this.employeeID = employeeID;

        this.requisitionName = requisitionName;
        this.requisitionDate = requisitionDate;
        this.status = status;
    }

    public int getRequisitionID() {
        return requisitionID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getRequisitionName() {
        return requisitionName;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public String getStatus() {
        return status;
    }

    public boolean addNewRequisition() throws DataStorageException {
        return MaterialRequisitionDA.addNewRequisition(this);
    }

    public boolean deleteRequisition() throws DataStorageException {
        return MaterialRequisitionDA.deleteRequisition(this);
    }

    public boolean updateRequisition() throws DataStorageException {
        return MaterialRequisitionDA.updateRequisition(this);
    }

    public static java.util.ArrayList<MaterialRequisition> getAll() {
        return MaterialRequisitionDA.getAll();
    }

    public static java.util.ArrayList<MaterialRequisition> getAllRequisition() throws DataStorageException, NotFoundException {
        return MaterialRequisitionDA.getAllRequisition();
    }

    public static java.util.ArrayList<MaterialRequisition> getAllRequisition2() throws DataStorageException, NotFoundException {
        return MaterialRequisitionDA.getAllRequisition2();
    }

    public static java.util.ArrayList<MaterialRequisition> getAllRequisition3() throws DataStorageException, NotFoundException {
        return MaterialRequisitionDA.getAllRequisition3();
    }

    public static java.util.ArrayList<MaterialRequisition> getAllRequisition4(int id) throws DataStorageException, NotFoundException {
        return MaterialRequisitionDA.getAllRequisition4(id);
    }

    public int getLastAddedID() throws DataStorageException {
        return MaterialRequisitionDA.getLastAddedID();
    }

    @Override
    public String toString() {
        return "MaterialRequisition{" + "requisitionID=" + requisitionID + ", employeeID=" + employeeID + ", requisitionType=" + requisitionType + ", requisitionName=" + requisitionName + ", requisitionDate=" + requisitionDate + ", status=" + status + '}';
    }

}
