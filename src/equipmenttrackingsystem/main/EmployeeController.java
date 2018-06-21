/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.employee.Employee;
import equipmenttrackingsystem.employee.EmployeeDA;
import equipmenttrackingsystem.exceptions.DataEntryCheck;
import equipmenttrackingsystem.exceptions.DataRepetitionException;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.omg.CORBA.portable.UnknownException;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class EmployeeController implements Initializable {

    @FXML
    private JFXTextField txtSurname;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private TableColumn<Employee, String> colEmpID;
    @FXML
    private TableColumn<Employee, String> colSurname;
    @FXML
    private TableColumn<Employee, String> colName;
    @FXML
    private TableColumn<Employee, String> colUserLogin;
    @FXML
    private TableColumn<Employee, String> colEmployeeType;
    @FXML
    private TableColumn<Employee, String> colPassword;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnNew;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtUserLogin;
    private JFXTextField txtEmployeeType;
    private EmployeeDA daEmp;
    static ArrayList<Employee> arEmployee;
    @FXML
    private TableView<Employee> tblEmployee;
    @FXML
    private JFXTextField txtEmployeeID;
    private String name, surname, userlogin, type, password;
    private int empid;
    @FXML
    private Tab eployeeTab;
    @FXML
    private ComboBox<String> cmbType;
    @FXML
    JFXSnackbar snackbar;
    @FXML
    private AnchorPane frmEmployee;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWORD_ADDRESS_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", Pattern.CASE_INSENSITIVE);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        snackbar = new JFXSnackbar(frmEmployee);
        txtEmployeeID.setDisable(true);

        daEmp = new EmployeeDA();
        loadData();
        populate();
    }

    public void populate() {
        ObservableList<String> empTypes = FXCollections.observableArrayList();
        empTypes.add("Foreman");
        empTypes.add("Systems Technician");
        empTypes.add("Chief Technician");
        empTypes.add("Operator");
        cmbType.setItems(empTypes);
    }

    private void onClickRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void onClickEdit(ActionEvent event) throws DataStorageException {
        Employee emp;

        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (validate(userlogin)) {
                        if (validatePassword(password)) {
                            emp = new Employee(empid, surname, name, userlogin, type, password);
                            if (emp.updateEmployee()) {
                                AlertMessages.getInfo("Updated", "Employee successfully deleted");
                                clearData();
                                loadData();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Employee was not deleted");
                            }
                        } else {
                            AlertMessages.getError("Unsuccessful", " Password is not valid \n a digit must occur at least once \n a lower case letter must occur at least once \n an upper case letter must occur at least once \n a special character must occur at least once \n no whitespace allowed in the entire string \n at least eight characters long");
                        }
                    } else {
                        AlertMessages.getError("Unsuccessful", "Email is not valid");
                    }
                }
            }
        }
    }

    @FXML
    private void onClickDelete(ActionEvent event) throws DataStorageException {
        Employee emp;
        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (validate(userlogin)) {
                        if (validatePassword(password)) {
                            emp = new Employee(empid, surname, name, userlogin, type, password);
                            if (emp.deleteEmployee()) {
                                AlertMessages.getInfo("Updated", "Employee successfully deleted");
                                clearData();
                                loadData();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Employee was not deleted");
                            }
                        } else {
                            AlertMessages.getError("Unsuccessful", "Password is not valid \n a digit must occur at least once \n a lower case letter must occur at least once \n an upper case letter must occur at least once \n a special character must occur at least once \n no whitespace allowed in the entire string \n at least eight characters long");
                        }
                    } else {
                        AlertMessages.getError("Unsuccessful", "Email is not valid");
                    }
                }
            }
        }

    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePassword(String emailStr) {
        Matcher matcher = VALID_PASSWORD_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @FXML
    private void onClickNew(ActionEvent event) {
        txtSurname.setText("");
        txtName.setText("");

        txtUserLogin.setText("");
        txtEmployeeType.setText("");
        txtPassword.setText("");
        txtEmployeeID.setText("");

        btnSave.setDisable(false);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
    }

    @FXML
    private void onClickRow(MouseEvent event) {
        Employee employee = tblEmployee.getSelectionModel().getSelectedItem();
        txtSurname.setText(employee.getSurname());
        txtName.setText(employee.getEmployeeName());

        txtUserLogin.setText(employee.getUserLogin() + "");
        cmbType.setValue(employee.getType() + "");
        txtPassword.setText(employee.getPassword());
        txtEmployeeID.setText(employee.getEmployeeID() + "");

        btnSave.setDisable(true);
        btnEdit.setDisable(false);
        btnDelete.setDisable(false);
        btnNew.setDisable(false);
    }

    private boolean nullValue() {
        if (!DataEntryCheck.textFieldNotEmpty(txtName.getText())) {
            AlertMessages.getWarning("null value", "Full Name is empty. Please enter employee name");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtSurname.getText())) {
            AlertMessages.getWarning("null value", " Surname is empty. Please enter employee surname");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtUserLogin.getText())) {
            AlertMessages.getWarning("null value", " User Login is empty. Please enter User Login");
            return true;
        } else if (!DataEntryCheck.textFieldNotEmpty(txtPassword.getText())) {
            AlertMessages.getWarning("null value", " Password is empty. Please enter a password");
            return true;
        }
        return false;
    }

    private boolean isTextOnly() {
        if (!DataEntryCheck.textOnlyTextField(name)) {
            AlertMessages.getWarning("input mismatch", "Employee name may only contain characters");
            return true;
        } else if (!DataEntryCheck.textOnlyTextField(surname)) {
            AlertMessages.getWarning("input mismatch", "Employee surname may only contain characters");
            return true;
        }

        return false;
    }

    private boolean isMin() {
        if (!DataEntryCheck.minLength(name, 3)) {
            AlertMessages.getWarning("Minimum characters", "Employee name is too short. Please enter more characters");
            return true;
        } else if (!DataEntryCheck.minLength(surname, 3)) {
            AlertMessages.getWarning("Minimum characters", "Employee surname is too short. Please enter more characters");
            return true;
        }
        return false;
    }

    private void getData() {
        if (!"".equals(txtEmployeeID.getText())) {
            empid = Integer.parseInt(txtEmployeeID.getText());
        }

        name = txtName.getText();
        surname = txtSurname.getText();
        type = cmbType.getValue();
        userlogin = txtUserLogin.getText();
        password = txtPassword.getText();

    }

    private void clearData() {
        txtSurname.setText("");
        txtName.setText("");

        txtUserLogin.setText("");
        cmbType.setValue(null);
        txtPassword.setText("");
        txtEmployeeID.setText("");
        eployeeTab.selectedProperty();
        btnSave.setDisable(false);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
    }

    private void loadData() {
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        colName.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        colUserLogin.setCellValueFactory(new PropertyValueFactory<>("UserLogin"));
        colEmployeeType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        try {
            arEmployee = Employee.getAllEmployee();
        } catch (UnknownException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataStorageException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblEmployee.getItems().setAll(arEmployee);
    }

    @FXML
    private void onClickSave(ActionEvent event) throws DataStorageException {
        Employee emp;
        if (!nullValue()) {
            getData();
            if (!isMin()) {
                if (!isTextOnly()) {
                    if (validate(userlogin)) {
                        if (validatePassword(password)) {
                            emp = new Employee(empid, surname, name, userlogin, type, password);
                            if (emp.addNewEmployee()) {
                                AlertMessages.getInfo("Saved", "Employee successfully saved");
                                clearData();
                                loadData();
                            } else {
                                AlertMessages.getError("Unsuccessful", "Employee was not saved");
                            }
                        } else {
                            AlertMessages.getError("Unsuccessful", "Password is not valid \n a digit must occur at least once \n a lower case letter must occur at least once \n an upper case letter must occur at least once \n a special character must occur at least once \n no whitespace allowed in the entire string \n at least eight characters long");
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void onSurname(KeyEvent event) {
        if (!txtSurname.getText().matches("[a-zA-Z]+\\.?")) {
            txtSurname.setText(txtSurname.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Surname must only contain text", 4000);
        }
    }

    @FXML
    private void onPassword(KeyEvent event) {
    }

    @FXML
    private void onName(KeyEvent event) {
        if (!txtName.getText().matches("[a-zA-Z]+\\.?")) {
            txtName.setText(txtName.getText().replaceAll("[^A-Za-z]", ""));
            snackbar.show("Surname must only contain text", 4000);
        }
    }
}
