package equipmenttrackingsystem;

import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.closeform.FormClose;
import equipmenttrackingsystem.db.CreateConnection;
import equipmenttrackingsystem.employee.Employee;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.screens.OpenForm;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.omg.CORBA.portable.UnknownException;

/**
 *
 * @author Joseph
 */
public class LoginController implements Initializable {

    private Label label;
    private AnchorPane loginForm;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    private ComboBox<String> cmbType;
    private Label lblUsernameError;
    private Label lblPassworderror;
    private LoginModel modelLogin;
    private static Connection con = null;
    private static PreparedStatement pr;
    private static Statement st;
    private static ResultSet rs;
    public static int employeeID;
    public static String employeeName, employeeSurname, employeeType;
    private final int MAX_ATTEMPT = 3;
    private int numAttempts = 0;
    @FXML
    private AnchorPane apMother;
    @FXML
    private AnchorPane apDesignPane;
    @FXML
    private Hyperlink hlCrateAccount;
    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!EquipmentTrackingSystem.isOpenSplash) {
            openSplash();
        }
        try {
            modelLogin = new LoginModel();
        } catch (Exception e) {
            AlertMessages.getError("Connection err", e.getMessage());
        }
    }

    @FXML
    private void createLogin(ActionEvent event) throws DataStorageException {
        Login objLogin;
        String username, password, type;
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            AlertMessages.getWarning("Null", "All fields are required. Please enter your login details");
        } else {
            username = txtUsername.getText();
            password = txtPassword.getText();

            objLogin = new Login(username, password);
            try {
                if (numAttempts < MAX_ATTEMPT) {
                    if (objLogin.isEmail(objLogin)) {
                        if (objLogin.isLogin(objLogin)) {
                            Employee emp = objLogin.returnEmpID(objLogin);
                            employeeID = emp.getEmployeeID();
                            employeeName = emp.getEmployeeName();
                            employeeSurname = emp.getSurname();
                            employeeType = emp.getType();
                            DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date();
                            String found = dt.format(date);
                            DateFormat dt2 = new SimpleDateFormat("HH:mm:ss");
                            Date date2 = new Date();
                            String found2 = dt2.format(date2);
                            System.out.println(found + " " + found2);
                            LoginModel.addLog(employeeID, found, found2);
                            txtPassword.setText("");
                            txtUsername.setText("");
                            if ("Foreman".equals(employeeType)) {
                                openForemanDashboard();
                            } else if ("Systems Technician".equals(employeeType)) {
                                openSystemsDashboard();
                            } else if ("Chief Technician".equals(employeeType)) {
                                openDashboard();
                            } else if ("Operator".equals(employeeType)) {
                                openOperatorDashboard();
                            }
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            stage.setIconified(true);

                        } else {
                            AlertMessages.getError("unsuccessful", "Invalid Password Please Try Again");
                            numAttempts++;
                        }
                    } else {
                        AlertMessages.getError("unsuccessful", "Invalid Email Please Try Again");
                        numAttempts++;
                    }
                } else if (numAttempts != MAX_ATTEMPT) {
                    AlertMessages.getError("Login authentication", "Try once again " + numAttempts);

                } else {
                    AlertMessages.getError("Login authentication", "Attempts exceeded " + numAttempts);
                    txtUsername.setDisable(true);
                    txtPassword.setEditable(false);
                }
                if (numAttempts == 3) {
                    AlertMessages.getError("Login authentication", "Attempts exceeded " + numAttempts);

                    openSplashAfterFailed();
                }

            } catch (UnknownException ue) {
                AlertMessages.getError("Null", ue.getMessage());
            }
        }
    }

    private void checkUsername(ActionEvent event) {
        if (txtUsername.getText().isEmpty()) {
            lblUsernameError.setText("Please enter username");
            lblUsernameError.setStyle("-fx-text-fill: red");
            lblUsernameError.setStyle("-fx-font-size: 18");

        }
    }

    private void checkPassword(ActionEvent event) {
        if (txtPassword.getText().isEmpty()) {
            lblPassworderror.setText("Please enter password");
            lblPassworderror.setStyle("-fx-text-fill: red");
            lblPassworderror.setStyle("-fx-font-size: 18");

        }

    }

    void openSplash() {
        EquipmentTrackingSystem.isOpenSplash = true;
        try {
            StackPane splashPane = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
            apMother.getChildren().setAll(splashPane);
            FadeTransition startFade = new FadeTransition(Duration.seconds(2), splashPane);
            startFade.setFromValue(0);
            startFade.setToValue(1);
            startFade.setCycleCount(1);

            FadeTransition endFade = new FadeTransition(Duration.seconds(2), splashPane);
            endFade.setFromValue(1);
            endFade.setToValue(0);
            endFade.setCycleCount(1);
            startFade.play();

            startFade.setOnFinished(ev -> {
                endFade.play();
            });

            endFade.setOnFinished(ev -> {
                try {
                    AnchorPane loginPane = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    apMother.getChildren().setAll(loginPane);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void openSplashAfterFailed() {
        EquipmentTrackingSystem.isOpenSplash = true;
        try {
            StackPane splashPane = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
            apMother.getChildren().setAll(splashPane);
            FadeTransition startFade = new FadeTransition(Duration.seconds(5), splashPane);
            startFade.setFromValue(0);
            startFade.setToValue(1);
            startFade.setCycleCount(1);

            FadeTransition endFade = new FadeTransition(Duration.seconds(5), splashPane);
            endFade.setFromValue(1);
            endFade.setToValue(0);
            endFade.setCycleCount(1);
            startFade.play();

            startFade.setOnFinished(ev -> {
                endFade.play();
            });

            endFade.setOnFinished(ev -> {
                try {
                    AnchorPane loginPane = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    apMother.getChildren().setAll(loginPane);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * populate employee type combo box
     */
    /*
    open dashboard
     */
    public static void openDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/equipmenttrackingsystem/main/MainForm.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public static void openForemanDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/equipmenttrackingsystem/main/ForemanDashboard.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public static void openOperatorDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/equipmenttrackingsystem/main/OperatorDashboard.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    public static void openSystemsDashboard() {
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/equipmenttrackingsystem/main/SystemsDashboard.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    /**
     * close form
     */
    String getEmpData() throws DataStorageException {
        String qry = "SELECT firstname,surname FROM employee WHERE username=? AND password= ?";
        String name = "";
        String surname = "";
        try {
            con = CreateConnection.initialise();
            pr = con.prepareStatement(qry);
            pr.setString(1, txtUsername.getText().trim());
            pr.setString(2, txtPassword.getText().trim());

            rs = pr.executeQuery();
            if (rs.next()) {

                name = rs.getString("firstname");
                surname = rs.getString("surname");

            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Welcome " + name + " " + surname;
    }

    public void closeForm() {
        FormClose.closeForm(loginForm);
    }

    @FXML
    private void hlForgotPassword(ActionEvent event) {
        String email = JOptionPane.showInputDialog(null, "Please Enter your employee Email", "Password Recovery", JOptionPane.INFORMATION_MESSAGE);
        Login objLogin = new Login(email, "");
        if (objLogin.isEmail(objLogin)) {
            String empid = JOptionPane.showInputDialog(null, "Please Enter your employee ID", "Password Recovery", JOptionPane.INFORMATION_MESSAGE);
            objLogin.setEmployeeid(Integer.parseInt(empid));
            if(objLogin.isExists(objLogin)){
                Employee emp=objLogin.returnEmp2(objLogin);
                JOptionPane.showMessageDialog(null, "Your Password is "+emp.getPassword(), "Password Recovery", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "The employee id you have entered is incorrect, Please Try Again", "Password Recovery", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "The email you have entered is incorrect, Please Try Again", "Password Recovery", JOptionPane.ERROR_MESSAGE);
        }
    }
}
