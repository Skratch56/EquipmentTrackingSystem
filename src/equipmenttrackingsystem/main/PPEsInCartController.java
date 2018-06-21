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
import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.carts.Cart;
import equipmenttrackingsystem.carts.CartDA;
import equipmenttrackingsystem.closeform.FormClose;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import static equipmenttrackingsystem.main.PPEController.arPPE;
import equipmenttrackingsystem.ppe.PPE;
import equipmenttrackingsystem.ppe.PPEDA;
import equipmenttrackingsystem.ppe.PPEIsnCart;
import equipmenttrackingsystem.ppe.PPEsInCartDA;
import equipmenttrackingsystem.requisition.Requisition;
import equipmenttrackingsystem.screens.OpenForm;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.omg.CORBA.portable.UnknownException;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class PPEsInCartController implements Initializable {

    @FXML
    private Tab eployeeTab;
    @FXML
    private TableView<PPE> tblEquipment;
    @FXML
    private TableColumn<PPE, Integer> colPPECode;
    @FXML
    private TableColumn<PPE, String> colType;
    @FXML
    private TableColumn<PPE, String> colMass;
    @FXML
    private TableColumn<PPE, String> colEquipmentName;
    @FXML
    private JFXButton btnSave;
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    private JFXButton btnNew;
    @FXML
    private ComboBox<String> cmbCartID;
    @FXML
    private JFXTextField txtEmployeeID;
    @FXML
    private JFXTextField txtRequisitionID;

    static ArrayList<PPE> arPPE;
    private int PPECode, RequisitionID, CartID, EmployeeID;
    private PPEDA daPPE;
    private PPEIsnCart daPPE2;
    private CartDA daPPE3;
    private PPEsInCartDA daPPE4;
    public static int globalCartID;
    @FXML
    private AnchorPane frmPPEsInCart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daPPE = new PPEDA();
        daPPE2 = new PPEIsnCart();
        arPPE = new ArrayList<>();
        daPPE3 = new CartDA();
        daPPE4 = new PPEsInCartDA();
        if (MaterialRequisitionController2.globalRequisition != 0) {
            txtRequisitionID.setText(MaterialRequisitionController2.globalRequisition + "");
        } else if (MaterialRequisitionController.globalRequisition != 0) {
            txtRequisitionID.setText(MaterialRequisitionController.globalRequisition + "");
        }
        txtRequisitionID.setDisable(true);
        txtEmployeeID.setText(LoginController.employeeID + "");
        txtEmployeeID.setDisable(true);
        btnDelete.setDisable(true);
        try {
            populateCart();
        } catch (DataStorageException ex) {
            Logger.getLogger(PPEsInCartController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(PPEsInCartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadData();
    }

    @FXML
    private void onClickRow(MouseEvent event) {
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        PPEIsnCart ppe;
        getData();
        int cnt = tblEquipment.getItems().size();
        try {
            for (int x = 0; x < cnt; x++) {

                PPECode = tblEquipment.getItems().get(x).getPPECode();
                ppe = new PPEIsnCart(PPECode, RequisitionID, CartID, EmployeeID);

                System.out.println("check" + cnt);
                if (ppe.addNewPPE()) {
                    System.out.println("add" + cnt);

                    System.out.println("update" + cnt);

                } else {
                    AlertMessages.getInfo("Unsuccessful", "Service record not saved");
                }

            }
            btnDelete.setDisable(false);
            globalCartID = Integer.parseInt(cmbCartID.getValue());
            MaterialRequisitionController2.globalCartIDMat = globalCartID;
            AlertMessages.getInfo("Saved", "Service record saved");

        } catch (DataStorageException ex) {
            AlertMessages.getError("Data repetition", ex.getMessage());

        }

    }

    @FXML
    private void onClickDelete(ActionEvent event) {
        globalCartID = Integer.parseInt(cmbCartID.getValue());
        OpenForm vehForm = new OpenForm();
        String title = "Time Stamp";
        String screen = "/equipmenttrackingsystem/main/JobAllocation.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmPPEsInCart);
    }

    private void populateCart() throws DataStorageException, NotFoundException {
        ArrayList<Cart> arCart = new ArrayList<>();
        arCart = CartDA.getAllCart();
        ObservableList<String> empTypes = FXCollections.observableArrayList();
        for (int x = 0; x < arCart.size(); x++) {
            empTypes.add(arCart.get(x).getCartID() + "");
        }
        cmbCartID.setItems(empTypes);
    }

    private void getData() {
        if (MaterialRequisitionController2.globalRequisition != 0) {
            RequisitionID = Integer.parseInt(txtRequisitionID.getText());
        } else if (MaterialRequisitionController.globalRequisition != 0) {
            RequisitionID = Integer.parseInt(txtRequisitionID.getText());
        }
        EmployeeID = Integer.parseInt(txtEmployeeID.getText());
        CartID = Integer.parseInt(cmbCartID.getValue());

    }

    private void loadData() {

        colPPECode.setCellValueFactory(new PropertyValueFactory<>("PPECode"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colMass.setCellValueFactory(new PropertyValueFactory<>("Mass"));
        colEquipmentName.setCellValueFactory(new PropertyValueFactory<>("EquipmentName"));

        try {
            if (MaterialRequisitionController2.globalRequisition != 0) {
                ArrayList<String> reqs = PPEDA.getPPE(MaterialRequisitionController2.globalRequisition + "");
                for (int x = 0; x < reqs.size(); x++) {
                    arPPE.add(PPEDA.getPPE2(reqs.get(x)));
                }
            } else if (MaterialRequisitionController.globalRequisition != 0) {
                ArrayList<String> reqs = PPEDA.getPPE(MaterialRequisitionController2.globalRequisition + "");
                for (int x = 0; x < reqs.size(); x++) {
                    arPPE.add(PPEDA.getPPE2(reqs.get(x)));
                }
            }

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
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmPPEsInCart);

    }

}
