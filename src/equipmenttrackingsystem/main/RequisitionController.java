/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import equipmenttrackingsystem.LoginController;
import static equipmenttrackingsystem.LoginController.employeeType;
import static equipmenttrackingsystem.LoginController.openDashboard;
import static equipmenttrackingsystem.LoginController.openForemanDashboard;
import static equipmenttrackingsystem.LoginController.openOperatorDashboard;
import static equipmenttrackingsystem.LoginController.openSystemsDashboard;
import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.closeform.FormClose;
import equipmenttrackingsystem.exceptions.DataEntryCheck;
import equipmenttrackingsystem.exceptions.DataRepetitionException;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import static equipmenttrackingsystem.main.PPEController.arPPE;
import equipmenttrackingsystem.ppe.PPE;
import equipmenttrackingsystem.ppe.PPEDA;
import equipmenttrackingsystem.requisition.MaterialRequisition;
import equipmenttrackingsystem.requisition.MaterialRequisitionDA;
import equipmenttrackingsystem.requisition.Requisition;
import equipmenttrackingsystem.requisition.RequisitionDA;
import equipmenttrackingsystem.screens.OpenForm;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.omg.CORBA.portable.UnknownException;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class RequisitionController implements Initializable {

    @FXML
    private Tab eployeeTab;
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
    @FXML
    private JFXButton btnSave;
    private JFXButton btnEdit;
    private JFXButton btnDelete;
    private JFXButton btnNew;
    @FXML
    private TableColumn<PPE, String> colAdd;
    @FXML
    private ComboBox<String> cmbRequisitionID;
    @FXML
    private JFXDatePicker dtRequisitionDate;
    private MaterialRequisitionDA daMaterialReq;
    private RequisitionDA daReq;
    private PPEDA daPPE;
    private Requisition requsition;
    private int requisitionID, ppeCode;
    private String requisitionDate;
    private JFXButton btnPPEsInCart;
    @FXML
    private JFXTextField txtSearchField;
    @FXML
    private AnchorPane frmRequisition;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daMaterialReq = new MaterialRequisitionDA();
        daReq = new RequisitionDA();
        daPPE = new PPEDA();
        loadData();
        if (MaterialRequisitionController.globalRequisition == 0) {
            populateRequisitionNo();
        } else {
            cmbRequisitionID.setValue(MaterialRequisitionController.globalRequisition + "");
            cmbRequisitionID.setDisable(true);
        }
        if (!"".equals(MaterialRequisitionController.globalRequisitionDate)) {
            dtRequisitionDate.setValue(LocalDate.parse(MaterialRequisitionController.globalRequisitionDate));
            dtRequisitionDate.setDisable(true);
        }

        filterData();
    }

    @FXML
    private void onClickRow(MouseEvent event) {

    }

    private void filterData() {
        ObservableList<PPE> oListemployee = FXCollections.observableArrayList(arPPE);
        FilteredList<PPE> searchedData = new FilteredList<>(oListemployee, e -> true);
        txtSearchField.setOnKeyReleased(e -> {
            txtSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(ppe -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (ppe.getEquipmentName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (ppe.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<PPE> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tblEquipment.comparatorProperty());
            tblEquipment.setItems(sortedData);
        });
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        Requisition req = null;
        PPE ppe;
        if (!nullValue()) {
            getData();
            int cnt = tblEquipment.getItems().size();
            try {
                for (int x = 0; x < cnt; x++) {
                    if (tblEquipment.getItems().get(x).getCheckBox().isSelected() == true) {
                        ppeCode = tblEquipment.getItems().get(x).getPPECode();
                        req = new Requisition(requisitionID, ppeCode, requisitionDate);
                        ppe = new PPE();
                        if (ppe.checkPPEQty(ppeCode)) {
                            System.out.println("check" + cnt);
                            if (req.addNewRequisition()) {
                                System.out.println("add" + cnt);
                                ppe.updatePPEQty(ppeCode);
                                System.out.println("update" + cnt);

                            } else {
                                AlertMessages.getInfo("Unsuccessful", "Requisition record not saved");
                            }

                        } else {
                            AlertMessages.getError("Quantity too low", "Quantity for PPECode " + ppeCode + " is too low please restock PPE");
                        }
//                        req.updateRequisition();
                    }
                }

                AlertMessages.getInfo("Saved", "Requisition record saved");
                loadData();
                FormClose.closeForm(frmRequisition);
                btnSave.setDisable(true);
            } catch (DataStorageException ex) {
                AlertMessages.getError("Data repetition", ex.getMessage());
            }

        }
    }

    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(dtRequisitionDate.getValue() + "")) {
            AlertMessages.getWarning("null value", " Requisition Date is empty. Please enter Requisition Date");
            return true;
        }
        return false;
    }

    private void getData() {
        requisitionDate = dtRequisitionDate.getValue().toString();
        requisitionID = Integer.parseInt(cmbRequisitionID.getValue());

    }

    private void populateRequisitionNo() {
        ArrayList<String> custList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            custList = MaterialRequisitionDA.getRequisitionID();
            custList.forEach(cust -> {
                obList.add(cust);
            });
            cmbRequisitionID.setItems(obList);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknow exce", e.getMessage());
        }
    }

    private void loadData() {

        colPPECode.setCellValueFactory(new PropertyValueFactory<>("PPECode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colMass.setCellValueFactory(new PropertyValueFactory<>("Mass"));
        colEquipmentName.setCellValueFactory(new PropertyValueFactory<>("EquipmentName"));
        colQtyInStock.setCellValueFactory(new PropertyValueFactory<>("QuantityInStock"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

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

    public void openPPEsInCart() {
        OpenForm vehForm = new OpenForm();
        String title = "PPEs In Cart";
        String screen = "/equipmenttrackingsystem/main/PPEsInCart.fxml";
        vehForm.openServiceListScreen(screen, title);
        
    }

    private void onClickPPEsInCart(ActionEvent event) {
        openPPEsInCart();
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmRequisition);

    }
}
