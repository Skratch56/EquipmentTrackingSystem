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
public class PPE {

    int PPECode, QuantityInStock;
    String Type, Mass, EquipmentName;
    CheckBox checkBox;

    public PPE() {
        this(0, null, null, null, 0, null);

    }

    public PPE(int PPECode, String Type, String Mass, String EquipmentName, int QuantityInStock, CheckBox checkBox) {
        this.PPECode = PPECode;
        this.Type = Type;
        this.Mass = Mass;
        this.EquipmentName = EquipmentName;
        this.QuantityInStock = QuantityInStock;
        this.checkBox = new CheckBox();
    }

    public void setPPECode(int PPECode) {
        this.PPECode = PPECode;
    }

    public void setQuantityInStock(int QuantityInStock) {
        this.QuantityInStock = QuantityInStock;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setMass(String Mass) {
        this.Mass = Mass;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public int getPPECode() {
        return PPECode;
    }

    public int getQuantityInStock() {
        return QuantityInStock;
    }

    public String getType() {
        return Type;
    }

    public String getMass() {
        return Mass;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    /**
     * Terminate the data storage
     *
     * @throws DataStorageException This exception is raised when there was a
     * problem saving to the data storage device
     */
    public boolean addNewPPE() throws DataStorageException {
        return PPEDA.addNewPPE(this);
    }

    public boolean deletePPE() throws DataStorageException {
        return PPEDA.deletePPE(this);
    }

    public boolean updatePPE() throws DataStorageException {
        return PPEDA.updatePPE(this);
    }

    public boolean updatePPEQty(int ppe) throws DataStorageException {
        return PPEDA.updatePPEQty(ppe);
    }

    public boolean checkPPEQty(int ppe) throws DataStorageException {
        return PPEDA.checkPPEQty(ppe);
    }

    public static java.util.ArrayList<PPE> getAll() {
        return PPEDA.getAll();
    }

    public static int find(int id) throws DataStorageException, NotFoundException {
        return PPEDA.find(id);
    }

    public static java.util.ArrayList<PPE> getAllPPE() throws DataStorageException, NotFoundException {
        return PPEDA.getAllPPE();
    }
    public static java.util.ArrayList<PPE> getAllPPE2(String status) throws DataStorageException, NotFoundException {
        return PPEDA.getAllPPE2(status);
    }

    public static java.util.ArrayList<PPE> getPPE(int ppecode) throws DataStorageException, NotFoundException {
        return PPEDA.getPPE(ppecode);
    }
}
