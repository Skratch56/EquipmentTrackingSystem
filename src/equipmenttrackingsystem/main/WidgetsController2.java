/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import equipmenttrackingsystem.db.CreateConnection;
import equipmenttrackingsystem.employee.Log;
import equipmenttrackingsystem.employee.LogDA;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import equipmenttrackingsystem.requisition.MaterialRequisition;
import equipmenttrackingsystem.requisition.MaterialRequisitionDA;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class WidgetsController2 implements Initializable {

    @FXML
    private Label lblNewReqs;
    private ArrayList<MaterialRequisition> arRequisitions;
    private MaterialRequisitionDA daReq;
    private LogDA daReq2;
    @FXML
    private Label lblPending;
    @FXML
    private Label lblComplete;
    @FXML
    private Label lblCancel;
    @FXML
    private ScrollPane pnlscrol;
    @FXML
    private VBox pnl_scroll;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daReq = new MaterialRequisitionDA();
        daReq2 = new LogDA();
        try {
            countReqs();
            refreshNodes();
        } catch (DataStorageException | NotFoundException ex) {
            Logger.getLogger(WidgetsController2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void countReqs() throws DataStorageException, NotFoundException {
        ArrayList<MaterialRequisition> arRequisitionsl;
        arRequisitionsl = MaterialRequisition.getAllRequisition();
        int cntP = 0, cntC = 0, cntCanc = 0;
        for (int x = 0; arRequisitionsl.size() > x; x++) {
            if ("Pending".equals(arRequisitionsl.get(x).getStatus())) {
                cntP += 1;
            } else if ("Complete".equals(arRequisitionsl.get(x).getStatus())) {
                cntC += 1;
            } else if ("Cancelled".equals(arRequisitionsl.get(x).getStatus())) {
                cntCanc += 1;
            }
        }
        lblNewReqs.setText(arRequisitionsl.size() + "");
        lblPending.setText(cntP + "");
        lblCancel.setText(cntCanc + "");
        lblComplete.setText(cntC + "");
    }

    private void refreshNodes() throws DataStorageException, NotFoundException {
        pnl_scroll.getChildren().clear();
        ArrayList<Log> daBook2 = LogDA.getAllLogs();
        Node[] nodes = new Node[daBook2.size()];

        for (int i = 0; i < daBook2.size(); i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                loader.setLocation(MainFormController.class.getResource("Item.fxml"));
                Pane pane = loader.load();

                ItemController c = (ItemController) loader.getController();
                System.out.println(daBook2.get(i).toString());
                c.setBooking(daBook2.get(i), daBook2.get(i).getLogID());

                pnl_scroll.getChildren().add(pane);

            } catch (IOException ex) {
                Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void onTotal(MouseEvent event) throws DataStorageException, JRException {
        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\EquipmentTrackingSystem\\src\\equipmenttrackingsystem\\reports\\RequisitionReport.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, null, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onPending(MouseEvent event) throws JRException, DataStorageException {
        HashMap params = new HashMap();
        params.put("Status", "Pending");

        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\EquipmentTrackingSystem\\src\\equipmenttrackingsystem\\reports\\RequisitionReportStatus.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, params, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onComplete(MouseEvent event) throws JRException, DataStorageException {
        HashMap params = new HashMap();
        params.put("Status", "Complete");

        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\EquipmentTrackingSystem\\src\\equipmenttrackingsystem\\reports\\RequisitionReportStatus.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, params, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }

    @FXML
    private void onCancelled(MouseEvent event) throws JRException, DataStorageException {
        HashMap params = new HashMap();
        params.put("Status", "Cancelled");

        JasperReport jr = JasperCompileManager.compileReport("C:\\Users\\CE\\Documents\\NetBeansProjects\\EquipmentTrackingSystem\\src\\equipmenttrackingsystem\\reports\\RequisitionReportStatus.jrxml");
        JasperPrint jp = JasperFillManager.fillReport(jr, params, CreateConnection.initialise());
        JRViewer viewer = new JRViewer(jp);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        JFrame jj = new JFrame();
        jj.add(viewer);
        jj.show();
    }
}
