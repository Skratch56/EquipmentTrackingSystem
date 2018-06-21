/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import equipmenttrackingsystem.LoginController;
import equipmenttrackingsystem.closeform.FormClose;
import equipmenttrackingsystem.screens.OpenForm;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class ForemanDashboardController implements Initializable {

    @FXML
    private Label lblLoggedIn;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnRequisition;
    @FXML
    private AnchorPane holderPane;
    AnchorPane requisitionForm, cartForm, equipmentForm, profiles, mainForm, employeeForm, requisitionEquipment;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private JFXButton btnLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblLoggedIn.setText(LoginController.employeeName + " " + LoginController.employeeSurname + " ID: " + LoginController.employeeID + "");
        try {
            requisitionForm = FXMLLoader.load(getClass().getResource("MaterialRequisition.fxml"));
            cartForm = FXMLLoader.load(getClass().getResource("Cart.fxml"));
            equipmentForm = FXMLLoader.load(getClass().getResource("PPE.fxml"));
            mainForm = FXMLLoader.load(getClass().getResource("Widgets.fxml"));
            employeeForm = FXMLLoader.load(getClass().getResource("Employee.fxml"));
            requisitionEquipment = FXMLLoader.load(getClass().getResource("Requisition.fxml"));
            setNode(mainForm);
        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void switchControls(ActionEvent event) {
        setNode(equipmentForm);
    }

    private void setNode(Node node) {

        holderPane.getChildren().clear();

        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(2000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void switchEmployees(ActionEvent event) {
        setNode(employeeForm);
    }

    @FXML
    private void switchRequisiton(ActionEvent event) {
        setNode(requisitionForm);
    }

    @FXML
    private void switchHome(ActionEvent event) {
//        FormClose.closeForm(mainForm);
//         OpenForm vehForm = new OpenForm();
//        String title = "Dashboard";
//        String screen = "Widgets.fxml";
//        vehForm.openServiceListScreen(screen, title);
        setNode(mainForm);
    }

    private void switchCart(ActionEvent event) {
        setNode(cartForm);
    }

    private void switchRequisitionEquipment(ActionEvent event) {
        setNode(requisitionEquipment);
    }

    @FXML
    private void onMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onClose(ActionEvent event) {
        FormClose.closeForm(holderPane);
    }

    @FXML
    private void onRefreshClicked(ActionEvent event) {
        FormClose.closeForm(holderPane);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/equipmenttrackingsystem/main/ForemanDashboard.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void onLogoutClicked(ActionEvent event) {
         FormClose.closeForm(holderPane);
    }

}
