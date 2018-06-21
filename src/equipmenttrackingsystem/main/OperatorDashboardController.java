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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class OperatorDashboardController implements Initializable {

    @FXML
    private Label lblLoggedIn;
    @FXML
    private JFXButton btnHome;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private JFXButton btnLogout;
    AnchorPane mainForm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lblLoggedIn.setText(LoginController.employeeName + " " + LoginController.employeeSurname + " ID: " + LoginController.employeeID + "");
            mainForm = FXMLLoader.load(getClass().getResource("Widgets3.fxml"));

            setNode(mainForm);
        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @FXML
    private void switchHome(ActionEvent event) {
        setNode(mainForm);
    }

    @FXML
    private void onRefreshClicked(ActionEvent event) {
        FormClose.closeForm(holderPane);
        OpenForm vehForm = new OpenForm();
        String title = "Dashboard";
        String screen = "/equipmenttrackingsystem/main/OperatorDashboard.fxml";
        vehForm.openServiceListScreen(screen, title);
    }

    @FXML
    private void onLogoutClicked(ActionEvent event) {
        FormClose.closeForm(holderPane);
    }

}
