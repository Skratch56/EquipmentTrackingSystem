/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.carts.Cart;
import equipmenttrackingsystem.carts.CartDA;
import equipmenttrackingsystem.employee.Employee;
import equipmenttrackingsystem.exceptions.DataEntryCheck;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import static equipmenttrackingsystem.main.CartController.arCart;
import equipmenttrackingsystem.ppe.PPE;
import equipmenttrackingsystem.ppe.PPEDA;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.omg.CORBA.portable.UnknownException;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class PPEController implements Initializable {

    @FXML
    private Tab eployeeTab;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnNew;
    @FXML
    private TableView<PPE> tblEquipment;
    @FXML
    private TableColumn<PPE, String> colPPECode;
    @FXML
    private TableColumn<PPE, String> colType;
    @FXML
    private TableColumn<PPE, String> colMass;
    @FXML
    private TableColumn<PPE, String> colEquipmentName;
    @FXML
    private TableColumn<PPE, String> colQtyInStock;
    static ArrayList<PPE> arPPE;
    private PPEDA daPPE;
    private JFXTextField txtType;
    @FXML
    private JFXTextField txtMass;
    @FXML
    private JFXTextField txtEquipmentName;
    @FXML
    private JFXTextField txtQuantity;
    @FXML
    private JFXTextField txtPPECode;
    private int PPECode, QuantityInStock;
    private String Type, Mass, EquipmentName;
    @FXML
    private JFXComboBox<String> cmbType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daPPE = new PPEDA();
        loadData();
        populate();
    }

    public void populate() {
        ObservableList<String> empTypes = FXCollections.observableArrayList();
        empTypes.add("Explosives");
        empTypes.add("Material");
        empTypes.add("Machinery");
        empTypes.add("Other");
        cmbType.setItems(empTypes);
    }

    @FXML
    private void onClickRow(MouseEvent event) {
        PPE ppe = tblEquipment.getSelectionModel().getSelectedItem();
        txtPPECode.setText(ppe.getPPECode() + "");
        cmbType.setValue(ppe.getType());
        txtMass.setText(ppe.getMass());
        txtEquipmentName.setText(ppe.getEquipmentName());
        txtQuantity.setText(ppe.getQuantityInStock() + "");

        btnSave.setDisable(true);
        btnEdit.setDisable(false);
        btnDelete.setDisable(false);
        btnNew.setDisable(false);
    }

    @FXML
    private void onClickSave(ActionEvent event) throws DataStorageException {
        PPE ppe;
        if (!nullValue()) {
            getData();

            if (!isTextOnly()) {

                ppe = new PPE(PPECode, Type, Mass, EquipmentName, QuantityInStock, null);
                if (ppe.addNewPPE()) {
                    AlertMessages.getInfo("Saved", "PPE successfully saved");
                    clearData();
                    loadData();
                } else {
                    AlertMessages.getError("Unsuccessful", "PPE was not saved");
                }

            }

        }
    }

    @FXML
    private void onClickEdit(ActionEvent event) throws DataStorageException {
        PPE ppe;
        if (!nullValue()) {
            getData();

            if (!isTextOnly()) {

                ppe = new PPE(PPECode, Type, Mass, EquipmentName, QuantityInStock, null);
                if (ppe.updatePPE()) {
                    AlertMessages.getInfo("Updated", "PPE successfully updated");
                    clearData();
                    loadData();
                } else {
                    AlertMessages.getError("Unsuccessful", "PPE was not updated");
                }

            }

        }
    }

    @FXML
    private void onClickDelete(ActionEvent event) throws DataStorageException {
        PPE ppe;
        if (!nullValue()) {
            getData();

            if (!isTextOnly()) {

                ppe = new PPE(PPECode, Type, Mass, EquipmentName, QuantityInStock, null);
                if (ppe.deletePPE()) {
                    AlertMessages.getInfo("Deleted", "PPE successfully deleted");
                    clearData();
                    loadData();
                } else {
                    AlertMessages.getError("Unsuccessful", "PPE was not deleted");
                }

            }

        }
    }

    @FXML
    private void onClickNew(ActionEvent event) {
        clearData();
    }

    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(cmbType.getValue())) {
            AlertMessages.getWarning("null value", "Type is empty. Please enter Type");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtEquipmentName.getText())) {
            AlertMessages.getWarning("null value", " EquipmentName is empty. Please enter EquipmentName");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtMass.getText())) {
            AlertMessages.getWarning("null value", " Mass is empty. Please enter Mass");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtQuantity.getText())) {
            AlertMessages.getWarning("null value", " Quantity is empty. Please enter Quantity");
            return true;
        }
        return false;
    }

    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(Type)) {
            AlertMessages.getWarning("input mismatch", "Type may only contain characters");
            return true;
        } else if (!DataEntryCheck.textOnlyTextField(EquipmentName)) {
            AlertMessages.getWarning("input mismatch", "EquipmentName may only contain characters");
            return true;
        }

        return false;
    }

    private void clearData() {
        txtEquipmentName.setText("");
        txtMass.setText("");
        txtPPECode.setText("");
        txtQuantity.setText("");
        cmbType.setValue(null);
        populate();
        eployeeTab.selectedProperty();
        btnSave.setDisable(false);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
    }

    private void getData() {
        if (!"".equals(txtPPECode.getText())) {
            PPECode = Integer.parseInt(txtPPECode.getText());
        }
        Type = cmbType.getValue();
        Mass = txtMass.getText();
        EquipmentName = txtEquipmentName.getText();
        QuantityInStock = Integer.parseInt(txtQuantity.getText());

    }

    private void loadData() {
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        colPPECode.setCellValueFactory(new PropertyValueFactory<>("PPECode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colMass.setCellValueFactory(new PropertyValueFactory<>("Mass"));
        colEquipmentName.setCellValueFactory(new PropertyValueFactory<>("EquipmentName"));
        colQtyInStock.setCellValueFactory(new PropertyValueFactory<>("QuantityInStock"));
        try {
            arPPE = PPE.getAllPPE();
        } catch (UnknownException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataStorageException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblEquipment.getItems().setAll(arPPE);
    }

    @FXML
    private void onMassEntered(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]||([\\.])?")) {
            event.consume();
        }
    }
    private void onQuantitychanged(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]||([\\.])?")) {
            event.consume();
        }
    }
}
