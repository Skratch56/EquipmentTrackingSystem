/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import equipmenttrackingsystem.LoginController;
import equipmenttrackingsystem.employee.Log;
import equipmenttrackingsystem.employee.LogDA;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import equipmenttrackingsystem.requisition.MaterialRequisition;
import equipmenttrackingsystem.requisition.MaterialRequisitionDA;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class WidgetsController3 implements Initializable {

    @FXML
    private Label lblNewReqs;
    @FXML
    private ScrollPane pnlscrol;
    @FXML
    private VBox pnl_scroll;
    private ArrayList<MaterialRequisition> arRequisitions;
    private MaterialRequisitionDA daReq;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            daReq = new MaterialRequisitionDA();
            refreshNodes();
        } catch (DataStorageException ex) {
            Logger.getLogger(WidgetsController3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(WidgetsController3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshNodes() throws DataStorageException, NotFoundException {
        pnl_scroll.getChildren().clear();
        ArrayList<MaterialRequisition> daBook2 = MaterialRequisition.getAllRequisition4(LoginController.employeeID);
        Node[] nodes = new Node[daBook2.size()];
 
        for (int i = 0; i < daBook2.size(); i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(MainFormController.class.getResource("Item3.fxml"));
                Pane pane = loader.load();

                ItemController3 c = (ItemController3) loader.getController();
                System.out.println(daBook2.get(i).toString());
                c.setBooking(daBook2.get(i), daBook2.get(i).getRequisitionID());

                pnl_scroll.getChildren().add(pane);

            } catch (IOException ex) {
                Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void onTotal(MouseEvent event) {
    }

}
