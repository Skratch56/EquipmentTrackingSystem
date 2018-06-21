/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import equipmenttrackingsystem.LoginController;
import equipmenttrackingsystem.carts.Cart;
import equipmenttrackingsystem.employee.Log;
import equipmenttrackingsystem.employee.LogDA;
import equipmenttrackingsystem.employeeallocation.EmployeeAllocation;
import equipmenttrackingsystem.exceptions.DataRepetitionException;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import equipmenttrackingsystem.ppe.PPE;
import equipmenttrackingsystem.requisition.MaterialRequisition;
import equipmenttrackingsystem.requisition.MaterialRequisitionDA;
import equipmenttrackingsystem.timestamp.TimeStamp;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class ItemController3 {

    private Label lblCustName;
    private Label lblTotalAmount;
    private MaterialRequisition MaterialRequisition;
    private long selectedRequisitionId;
    private MaterialRequisitionDA daBook;
    @FXML
    private Label lblDay;
    @FXML
    private Label lblMonth;
    @FXML
    private Label lblYear;
    @FXML
    private Label lblRequisitionStatus;
    @FXML
    private Label lblRequisitionID;
    @FXML
    private Label lblCartID;
    @FXML
    private Label lblPPEs;
    @FXML
    private Label lblLocationFrom;
    @FXML
    private Label lblLocationTo;
    @FXML
    private Label lblEmployeeName;
    @FXML
    private Label lblAssignedEmployee;

    /**
     * Initializes the controller class.
     */
    public ItemController3() {
        lblEmployeeName = new Label();
        lblAssignedEmployee = new Label();
        lblRequisitionStatus = new Label();
        lblRequisitionID = new Label();
        lblCartID = new Label();
        lblPPEs = new Label();
        lblLocationFrom = new Label();
        lblLocationTo = new Label();
        daBook = new MaterialRequisitionDA();

    }

    public void setBooking(MaterialRequisition log, long selectedRequisitionId) {

        try {
            this.MaterialRequisition = log;
            this.selectedRequisitionId = selectedRequisitionId;
            setData();
        } catch (DataStorageException ex) {
            Logger.getLogger(ItemController3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(ItemController3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setData() throws DataStorageException, NotFoundException {
//        EmployeeAllocation aloc2 = MaterialRequisitionDA.getAllocation2(LoginController.employeeID);
//        if (aloc2.getAllocationID() != 0) {
        ArrayList<PPE> arPPE = new ArrayList<>();
        lblRequisitionStatus.setText("Requisition Complete");
        lblRequisitionID.setText("Requisition ID: " + MaterialRequisition.getRequisitionID());
        lblEmployeeName.setText(MaterialRequisitionDA.getEmployeeName(MaterialRequisition.getEmployeeID()));
        arPPE = MaterialRequisitionDA.getAllPPE(MaterialRequisition.getRequisitionID());
        String ppeName = "";
        for (int x = 0; x < arPPE.size(); x++) {
            if (x == 0) {
                ppeName = arPPE.get(x).getEquipmentName();
            } else {
                ppeName += ", " + arPPE.get(x).getEquipmentName();
            }

        }

        lblPPEs.setText(ppeName);

        Cart cart = MaterialRequisitionDA.getCartLocation(MaterialRequisitionDA.getCart(MaterialRequisition.getRequisitionID()));

        lblCartID.setText("ID: " + cart.getCartID() + " BatteryLife: " + cart.getBatteryLife() + "%");

        lblLocationFrom.setText(MaterialRequisitionDA.getLocation(cart.getLocationID()));

        EmployeeAllocation aloc = MaterialRequisitionDA.getAllocation(MaterialRequisition.getRequisitionID());
        System.out.println(aloc.toString());
        lblAssignedEmployee.setText(LoginController.employeeName + " " + LoginController.employeeSurname);

        TimeStamp time = MaterialRequisitionDA.getTimeStamp(aloc.getAllocationID());

        lblLocationTo.setText(MaterialRequisitionDA.getLocation(time.getLocationID()));

        LocalDate date = LocalDate.parse(MaterialRequisition.getRequisitionDate());

        lblYear.setText((date.getYear()) + "");
        lblMonth.setText((date.getMonth()) + "");
        lblDay.setText((date.getDayOfMonth()) + "");
//        } else {
//            
//        }

    }

}
