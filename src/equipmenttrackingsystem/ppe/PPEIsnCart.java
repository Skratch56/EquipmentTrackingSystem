/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.ppe;

import equipmenttrackingsystem.employee.*;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import javafx.scene.control.CheckBox;

/**
 *
 * @author CE
 */
public class PPEIsnCart {

    int PPECode, RequisitionID, CartID, EmployeeID;

    public PPEIsnCart() {
        this(0, 0, 0, 0);
    }

    public PPEIsnCart(int PPECode, int RequisitionID, int CartID, int EmployeeID) {
        this.PPECode = PPECode;
        this.CartID = CartID;
        this.EmployeeID = EmployeeID;
        this.RequisitionID = RequisitionID;
    }

    public void setRequisitionID(int RequisitionID) {
        this.RequisitionID = RequisitionID;
    }

    public void setPPECode(int PPECode) {
        this.PPECode = PPECode;
    }

    public void setCartID(int CartID) {
        this.CartID = CartID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public int getPPECode() {
        return PPECode;
    }

    public int getCartID() {
        return CartID;
    }

    public int getRequisitionID() {
        return RequisitionID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    /**
     * Terminate the data storage
     *
     * @throws DataStorageException This exception is raised when there was a
     * problem saving to the data storage device
     */
    public boolean addNewPPE() throws DataStorageException {
        return PPEsInCartDA.addNewPPE(this);
    }

    public boolean deletePPE() throws DataStorageException {
        return PPEsInCartDA.deletePPE(this);
    }

    public boolean updatePPE() throws DataStorageException {
        return PPEsInCartDA.updatePPE(this);
    }

    public boolean updatePPEQty(int ppe) throws DataStorageException {
        return PPEDA.updatePPEQty(ppe);
    }

    public boolean checkPPEQty(int ppe) throws DataStorageException {
        return PPEDA.checkPPEQty(ppe);
    }

//    public static java.util.ArrayList<PPEIsnCart> getAll() {
//        return PPEsInCartDA.getAll();
//    }
    public static int find(int id) throws DataStorageException, NotFoundException {
        return PPEsInCartDA.find(id);
    }

    public static java.util.ArrayList<PPEIsnCart> getAllPPE() throws DataStorageException, NotFoundException {
        return PPEsInCartDA.getAllPPE();
    }
}
