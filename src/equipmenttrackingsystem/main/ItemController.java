/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import equipmenttrackingsystem.employee.Log;
import equipmenttrackingsystem.employee.LogDA;
import equipmenttrackingsystem.exceptions.DataRepetitionException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class ItemController {

    @FXML
    private Label lblCustName;
    @FXML
    private Label lblTotalAmount;
    private Label lblBookingID;
    private Log Log;
    private long selectedLogId;
    private LogDA daBook;
    @FXML
    private Label lblDay;
    @FXML
    private Label lblMonth;
    @FXML
    private Label lblYear;
    @FXML
    private Label lblBookingStatus;
    @FXML
    private Label lblService;
    @FXML
    private Label lblLogID;

    /**
     * Initializes the controller class.
     */
    public ItemController() {
         lblLogID = new Label();
        lblCustName = new Label();
        lblTotalAmount = new Label();
        lblDay = new Label();
        lblMonth = new Label();
        lblYear = new Label();
        lblBookingStatus = new Label();
        lblService = new Label();
        daBook = new LogDA();
 

    }

    public void setBooking(Log log, long selectedLogId) {

        this.Log = log;
        this.selectedLogId = selectedLogId;
        setData();

    }

    private void setData() {
        lblBookingStatus.setText("User Log");
        lblLogID.setText("User Log ID: " + Log.getLogID());
        lblTotalAmount.setText(Log.getTime());
        lblCustName.setText(LogDA.getEmployeeName(Log.getEmployeeID()));
        lblService.setText(LogDA.getEmployeeType(Log.getEmployeeID()));
        LocalDate date = LocalDate.parse(Log.getDate());
        lblYear.setText((date.getYear()) + "");
        lblMonth.setText((date.getMonth()) + "");
        lblDay.setText((date.getDayOfMonth()) + "");

    }

}
