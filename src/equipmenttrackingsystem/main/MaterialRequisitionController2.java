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
import equipmenttrackingsystem.employee.Employee;
import equipmenttrackingsystem.employee.EmployeeDA;
import equipmenttrackingsystem.exceptions.DataEntryCheck;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import static equipmenttrackingsystem.main.EmployeeController.arEmployee;
import equipmenttrackingsystem.requisition.MaterialRequisition;
import equipmenttrackingsystem.requisition.MaterialRequisitionDA;
import equipmenttrackingsystem.screens.OpenForm;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.LocalDateStringConverter;
import org.omg.CORBA.portable.UnknownException;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class MaterialRequisitionController2 implements Initializable {

    private JFXTextField txtRequisitionName;
    @FXML
    private JFXDatePicker dtRequisitionDate;
    private JFXTextField txtRequisitionType;
    @FXML
    private JFXTextField txtRequisitionID;
    @FXML
    private JFXTextField txtEmployeeID;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private TableView<MaterialRequisition> tblRequisition;
    @FXML
    private TableColumn<MaterialRequisition, String> colRequisitionID;
    private TableColumn<MaterialRequisition, String> colRequisitionType;
    @FXML
    private TableColumn<MaterialRequisition, String> colRequisitionName;
    @FXML
    private TableColumn<MaterialRequisition, String> colRequisitionDate;
    @FXML
    private TableColumn<MaterialRequisition, String> colStatus;
    @FXML
    private TableColumn<MaterialRequisition, String> colEmployeeID;
    private JFXButton btnSave;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnNew;
    private MaterialRequisitionDA daReq;
    static ArrayList<MaterialRequisition> arRequisition;
    private String reqname, reqtype, reqdate, status;
    private int reqid, empid;
    public static int globalRequisition = 0;
    public static String globalRequisitionDate = "";
    public static String globalRequisitionType = "";
    @FXML
    private JFXButton btnContinue;
    @FXML
    private AnchorPane frmMaterail2;

    public static int globalCartIDMat;
     public static int globalAllocation;
    @FXML
    private JFXTextField txtComment;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtRequisitionID.setDisable(true);
        daReq = new MaterialRequisitionDA();
        txtEmployeeID.setDisable(true);
        txtEmployeeID.setText(LoginController.employeeID + "");

        loadData();

        dateInitializer();
    }

    private void dateInitializer() {
        dtRequisitionDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    private void loadData() {
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        btnContinue.setDisable(true);
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colRequisitionDate.setCellValueFactory(new PropertyValueFactory<>("requisitionDate"));
        colRequisitionID.setCellValueFactory(new PropertyValueFactory<>("requisitionID"));
        colRequisitionName.setCellValueFactory(new PropertyValueFactory<>("requisitionName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        try {
            arRequisition = MaterialRequisition.getAllRequisition2();
        } catch (UnknownException | DataStorageException | NotFoundException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblRequisition.getItems().setAll(arRequisition);
    }

    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(txtComment.getText())) {
            AlertMessages.getWarning("null value", "Requisition Name is empty. Please enter Requisition Name");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(cmbStatus.getValue() + "")) {
            AlertMessages.getWarning("null value", " Status is empty. Please enter Status");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(dtRequisitionDate.getValue() + "")) {
            AlertMessages.getWarning("null value", " Requisition Date is empty. Please enter Requisition Date");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtEmployeeID.getText())) {
            AlertMessages.getWarning("null value", " Employee ID. Please Employee ID");
            return true;
        }
        return false;
    }

    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(reqname)) {
            AlertMessages.getWarning("input mismatch", "Requisition Name may only contain characters");
            return true;
        }

        return false;
    }

    private boolean isMin() {
        if (!DataEntryCheck.minLength(reqname, 3)) {
            AlertMessages.getWarning("Minimum characters", "Requisition Name is too short. Please enter more characters");
            return true;
        }
        return false;
    }

    private void getData() {
        if (!"".equals(txtEmployeeID.getText())) {
            empid = Integer.parseInt(txtEmployeeID.getText());
        }
        if (!"".equals(txtRequisitionID.getText())) {
            reqid = Integer.parseInt(txtRequisitionID.getText());
        }

        reqname = txtComment.getText();
        status = cmbStatus.getValue();
        reqdate = dtRequisitionDate.getValue().toString();

    }

    private void clearData() {
        txtEmployeeID.setText(LoginController.employeeID + "");
        txtRequisitionID.setText("");
        txtComment.setText("");
        dtRequisitionDate.setValue(null);
        cmbStatus.setValue(null);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        btnContinue.setDisable(true);
    }



    @FXML
    private void onEditClicked(ActionEvent event) throws DataStorageException {
        MaterialRequisition req;

        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {

                    req = new MaterialRequisition(reqid, empid, reqname, reqdate, status);
                    if (req.updateRequisition()) {
                        AlertMessages.getInfo("Updated", "Requisition successfully updated");
                        clearData();
                        loadData();
                    } else {
                        AlertMessages.getError("Unsuccessful", "Requisition was not updated");
                    }

                }
            }
        }
    }

    @FXML
    private void onDeleteClicked(ActionEvent event) throws DataStorageException {
        MaterialRequisition req;

        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {

                    req = new MaterialRequisition(reqid, empid, reqname, reqdate, status);
                    if (req.deleteRequisition()) {
                        AlertMessages.getInfo("Deleted", "Requisition successfully deleted");
                        clearData();
                        loadData();
                    } else {
                        AlertMessages.getError("Unsuccessful", "Requisition was not deleted");
                    }

                }
            }
        }
    }

    @FXML
    private void onNewClicked(ActionEvent event) throws DataStorageException {
        clearData();
    }

    @FXML
    private void onRowClicked(MouseEvent event) throws ParseException {

        MaterialRequisition req = tblRequisition.getSelectionModel().getSelectedItem();

        txtRequisitionID.setText(req.getRequisitionID() + "");
        txtComment.setText(req.getRequisitionName());
        dtRequisitionDate.setValue(LocalDate.parse(req.getRequisitionDate()));
        cmbStatus.setValue(req.getStatus());
        txtEmployeeID.setText(req.getEmployeeID() + "");

        btnEdit.setDisable(false);
        btnDelete.setDisable(false);
        btnNew.setDisable(false);
        btnContinue.setDisable(false);
    }

    @FXML
    private void onContinueClicked(ActionEvent event) {
        if ("Pending".equals(cmbStatus.getValue())) {
            AlertMessages.getError("Unsuccessful", "Requisition Please Approve requisition before you continue");
        } else {
            globalRequisition = Integer.parseInt(txtRequisitionID.getText());
            globalRequisitionDate = dtRequisitionDate.getValue().toString();
            globalRequisitionType = txtComment.getText() + "";
            openRequisition();
        }

    }

    public void openRequisition() {
        OpenForm vehForm = new OpenForm();
        String title = "Requisition for service";
        String screen = "/equipmenttrackingsystem/main/PPEsInCart.fxml";
        vehForm.openServiceListScreen(screen, title);
    }
}
