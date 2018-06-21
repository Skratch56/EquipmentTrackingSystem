/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import equipmenttrackingsystem.LoginController;
import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.carts.Cart;
import equipmenttrackingsystem.carts.CartDA;
import equipmenttrackingsystem.closeform.FormClose;
import equipmenttrackingsystem.employee.Employee;
import equipmenttrackingsystem.employee.EmployeeDA;
import equipmenttrackingsystem.employeeallocation.EmployeeAllocation;
import equipmenttrackingsystem.employeeallocation.EmployeeAllocationDA;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import static equipmenttrackingsystem.main.PPEsInCartController.arPPE;
import static equipmenttrackingsystem.main.PPEsInCartController.globalCartID;
import equipmenttrackingsystem.ppe.PPE;
import equipmenttrackingsystem.ppe.PPEDA;
import equipmenttrackingsystem.ppe.PPEIsnCart;
import equipmenttrackingsystem.ppe.PPEsInCartDA;
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
import javafx.scene.control.DateCell;
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
public class JobAllocationController implements Initializable {

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
    private JFXButton btnSave;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private ComboBox<String> cmbCartID;
    @FXML
    private JFXComboBox<String> cmbEmployee;
    @FXML
    private JFXDatePicker dtDate;
    static ArrayList<PPE> arPPE;
    private PPEDA daPPE;
    private PPEIsnCart daPPE2;
    private CartDA daPPE3;
    private PPEsInCartDA daPPE4;
    private EmployeeDA daPPE5;
    private EmployeeAllocationDA daPPE6;
    int AllocationID, EmployeeID, RequisitionID, CartID;
    String Date, Time;
    @FXML
    private JFXTimePicker dtTime;
    public static int globalAllocation;
    @FXML
    private AnchorPane frmJobAllocation;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daPPE = new PPEDA();
        daPPE2 = new PPEIsnCart();
        arPPE = new ArrayList<>();
        daPPE3 = new CartDA();
        daPPE4 = new PPEsInCartDA();
        daPPE5 = new EmployeeDA();
        daPPE6 = new EmployeeAllocationDA();
        cmbCartID.setValue(MaterialRequisitionController2.globalCartIDMat + "");
        loadData();
        try {
            populateEmployee();
        } catch (DataStorageException ex) {
            Logger.getLogger(JobAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(JobAllocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dateInitializer();
    }
     private void dateInitializer() {
        dtDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
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
                ArrayList<String> reqs = PPEDA.getPPE(MaterialRequisitionController.globalRequisition + "");
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
    private void onClickRow(MouseEvent event) {

    }

    @FXML
    private void onClickSave(ActionEvent event) throws DataStorageException, equipmenttrackingsystem.exceptions.UnknownException {
        getData();
        if (cmbEmployee.getValue() != null) {

            EmployeeAllocation aloc = new EmployeeAllocation(0, EmployeeID, Date, RequisitionID, Time, CartID);
            aloc.init();
            aloc.addNewTag();
            globalAllocation = EmployeeAllocationDA.getLastAddedID();
            MaterialRequisitionController2.globalAllocation = globalAllocation;
            AlertMessages.getInfo("Saved", "Job Created Successfully");
            if (MaterialRequisitionController2.globalRequisition != 0) {
                EmployeeAllocationDA.updateRequisition(MaterialRequisitionController2.globalRequisition);
            } else if (MaterialRequisitionController.globalRequisition != 0) {
                EmployeeAllocationDA.updateRequisition(MaterialRequisitionController.globalRequisition);
            }
        }

    }

    private void populateEmployee() throws DataStorageException, NotFoundException {
        ArrayList<Employee> arCart = new ArrayList<>();
        arCart = EmployeeDA.getAllEmployee();
        ObservableList<String> empTypes = FXCollections.observableArrayList();

        for (int x = 0; x < arCart.size(); x++) {
            empTypes.add(arCart.get(x).getEmployeeID() + " " + arCart.get(x).getUserLogin());
        }
        cmbEmployee.setItems(empTypes);
    }

    private void getData() {
        String str = cmbEmployee.getValue();
        String[] splited = str.split("\\s+");
        EmployeeID = Integer.parseInt(splited[0]);
        Date = dtDate.getValue() + "";
        Time = dtTime.getValue() + "";
        CartID = MaterialRequisitionController2.globalCartIDMat;
        if (MaterialRequisitionController2.globalRequisition != 0) {
            RequisitionID = MaterialRequisitionController2.globalRequisition;

        } else if (MaterialRequisitionController.globalRequisition != 0) {
            RequisitionID = MaterialRequisitionController.globalRequisition;
        }
    }

    @FXML
    private void onClickDelete(ActionEvent event) throws equipmenttrackingsystem.exceptions.UnknownException {
        globalAllocation = EmployeeAllocationDA.getLastAddedID();
        MaterialRequisitionController2.globalAllocation = globalAllocation;
        OpenForm vehForm = new OpenForm();
        String title = "Time Stamp";
        String screen = "/equipmenttrackingsystem/main/TimeStamp.fxml";
        vehForm.openServiceListScreen(screen, title);
        FormClose.closeForm(frmJobAllocation);

    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(frmJobAllocation);

    }

}
