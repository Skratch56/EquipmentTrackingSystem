/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.requisition;

import equipmenttrackingsystem.employee.*;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;

/**
 *
 * @author CE
 */
public class Requisition {

    int requisitionID, ppeCode;
    String requisitionDate;

    public Requisition() {
        this(0, 0, "");
    }

    public Requisition(int requisitionID, int ppeCode, String requisitionDate) {
        this.requisitionID = requisitionID;
        this.ppeCode = ppeCode;
        this.requisitionDate = requisitionDate;

    }

    public int getRequisitionID() {
        return requisitionID;
    }


    public String getRequisitionDate() {
        return requisitionDate;
    }

    public int getPpeCode() {
        return ppeCode;
    }

   

    public boolean addNewRequisition() throws DataStorageException {
        return RequisitionDA.addNewRequisition(this);
    }

    public boolean deleteRequisition() throws DataStorageException {
        return RequisitionDA.deleteRequisition(this);
    }

    public boolean updateRequisition() throws DataStorageException {
        return RequisitionDA.updateRequisition(this);
    }

    public static java.util.ArrayList<Requisition> getAll() {
        return RequisitionDA.getAll();
    }

    public static java.util.ArrayList<Requisition> getAllRequisition() throws DataStorageException, NotFoundException {
        return RequisitionDA.getAllRequisition();
    }

}
