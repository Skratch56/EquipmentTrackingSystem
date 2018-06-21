/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import equipmenttrackingsystem.LoginController;
import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.carts.CartDA;
import equipmenttrackingsystem.closeform.FormClose;
import equipmenttrackingsystem.exceptions.DataRepetitionException;
import equipmenttrackingsystem.timestamp.TimeStamp;
import equipmenttrackingsystem.timestamp.TimeStampDA;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.omg.CORBA.portable.UnknownException;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class TimeStampController implements Initializable {

    @FXML
    private JFXButton btnSave;
    @FXML
    private ComboBox<String> cmbLocationID;
    @FXML
    private JFXTextField txtAllocationID;
    @FXML
    private JFXTextField txtTimeStampID;
    private CartDA daCart;
    private TimeStampDA daCart2;
    private int locationID, allocationid;
    @FXML
    private AnchorPane frmTimeStamp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daCart = new CartDA();
        daCart2 = new TimeStampDA();
        populateLocation();
        txtAllocationID.setText(MaterialRequisitionController2.globalAllocation + "");
        txtAllocationID.setDisable(true);
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        try {
            getData();
            TimeStamp time = new TimeStamp(0, allocationid, locationID);
            System.out.println(time);
            if (time.addTimeStamp(time)) {
                FormClose.closeForm(frmTimeStamp);
                AlertMessages.getInfo("Saved", "TimeStamp record saved");
            } else {
                AlertMessages.getInfo("Unsuccessful", "TimeStamp record not saved");
            }

        } catch (DataRepetitionException ex) {
            Logger.getLogger(TimeStampController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void populateLocation() {
        ArrayList<String> custList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            custList = CartDA.getLocation();
            custList.forEach(cust -> {
                obList.add(cust);
            });
            cmbLocationID.setItems(obList);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknow exce", e.getMessage());
        }
    }

    private void getData() {
        allocationid = Integer.parseInt(txtAllocationID.getText());
        locationID = getLocationID();
    }

    private int getLocationID() {
        int id = 1;
        try {
            id = CartDA.getLocationID(cmbLocationID.getValue());
        } catch (UnknownException e) {
            AlertMessages.getError("Unknown exce", e.getMessage());
        }
        return id;
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmTimeStamp);

    }

}
