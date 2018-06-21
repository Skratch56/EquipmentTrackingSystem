/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import equipmenttrackingsystem.alert.AlertMessages;
import equipmenttrackingsystem.carts.Cart;
import equipmenttrackingsystem.carts.CartDA;
import equipmenttrackingsystem.employee.Employee;
import equipmenttrackingsystem.employee.EmployeeDA;
import equipmenttrackingsystem.exceptions.DataEntryCheck;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;
import static equipmenttrackingsystem.main.EmployeeController.arEmployee;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import org.omg.CORBA.portable.UnknownException;

/**
 * FXML Controller class
 *
 * @author CE
 */
public class CartController implements Initializable {

    @FXML
    private Tab eployeeTab;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnNew;
    private JFXTextField txtEmployeeID;
    @FXML
    private TableView<Cart> tblCarts;
    @FXML
    private TableColumn<Cart, String> colCartID;
    @FXML
    private TableColumn<Cart, String> colBattery;
    @FXML
    private TableColumn<Cart, String> colLocation;
    @FXML
    private ProgressBar progressBaterryLife;
    @FXML
    private Label txtProgress;
    @FXML
    private Slider sliderProgress;
    static ArrayList<Cart> arCart;
    @FXML
    private JFXTextField txtCartID;
    @FXML
    private ComboBox<String> cmbLocation;
    private double batteryLife;
    private int cartID, locationID;
    private CartDA daCart;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        daCart = new CartDA();
        loadData();
        populateLocation();
        sliderProgress.setDisable(true);
        progressBaterryLife.setDisable(true);
        batteryLife = Math.round(sliderProgress.getValue());
        sliderProgress.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                progressBaterryLife.setProgress(new_val.doubleValue() / 100);
                txtProgress.setText(Math.round(new_val.doubleValue()) + " %");
                batteryLife = Math.round(new_val.doubleValue());
            }
        });
    }

    @FXML
    private void onClickRow(MouseEvent event) {
        Cart cart = tblCarts.getSelectionModel().getSelectedItem();
        txtCartID.setText(cart.getCartID() + "");
        txtProgress.setText(cart.getBatteryLife());
        progressBaterryLife.setProgress(Double.parseDouble(cart.getBatteryLife()) / 100);
        sliderProgress.setValue(Double.parseDouble(cart.getBatteryLife()));
        //cmbLocation.setValue(cart.getLocationID() + "");
        btnSave.setDisable(true);
        btnEdit.setDisable(false);
        btnDelete.setDisable(false);
        btnNew.setDisable(false);
        sliderProgress.setDisable(false);
        progressBaterryLife.setDisable(false);
    }

    @FXML
    private void onClickSave(ActionEvent event) throws DataStorageException {
        Cart cart;

        if (cmbLocation.getValue() != null) {
            getData();
            cart = new Cart(cartID, batteryLife + "", locationID);
            if (cart.addNewCart()) {
                AlertMessages.getInfo("Saved", "Cart successfully saved");
                clearData();
                loadData();
            } else {
                AlertMessages.getError("Unsuccessful", "Cart was not saved");
            }
        }
    }

    @FXML
    private void onClickEdit(ActionEvent event) throws DataStorageException {
        Cart cart;
        getData();
        if (cmbLocation.getValue() != null) {
            cart = new Cart(cartID, batteryLife + "", locationID);
            if (cart.updateCart()) {
                AlertMessages.getInfo("Updated", "Cart successfully Updated");
                clearData();
                loadData();
            } else {
                AlertMessages.getError("Unsuccessful", "Cart was not Updated");
            }
        }
    }

    @FXML
    private void onClickDelete(ActionEvent event) throws DataStorageException {
        Cart cart;
        getData();
        cart = new Cart(cartID, batteryLife + "", locationID);
        if (cart.deleteCart()) {
            AlertMessages.getInfo("Deleted", "Cart successfully deleted");
            clearData();
            loadData();
        } else {
            AlertMessages.getError("Unsuccessful", "Cart was not deleted");
        }
    }

    @FXML
    private void onClickNew(ActionEvent event) {
        clearData();
    }

    private void populateLocation() {
        ArrayList<String> custList;
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            custList = CartDA.getLocation();
            custList.forEach(cust -> {
                obList.add(cust);
            });
            cmbLocation.setItems(obList);
        } catch (UnknownException e) {
            AlertMessages.getError("Unknow exce", e.getMessage());
        }
    }

    private int getLocationID() {
        int id = 1;
        try {
            id = CartDA.getLocationID(cmbLocation.getValue());
        } catch (UnknownException e) {
            AlertMessages.getError("Unknown exce", e.getMessage());
        }
        return id;
    }

    private void getData() {
        if (!"".equals(txtCartID.getText())) {
            cartID = Integer.parseInt(txtCartID.getText());
        }

        locationID = getLocationID();
        System.out.print(locationID);
    }

    private void loadData() {
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
        colCartID.setCellValueFactory(new PropertyValueFactory<>("CartID"));
        colBattery.setCellValueFactory(new PropertyValueFactory<>("BatteryLife"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("LocationID"));

        try {
            arCart = Cart.getAllCart();
        } catch (UnknownException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataStorageException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tblCarts.getItems().setAll(arCart);
    }

    private void clearData() {
        txtCartID.setText("");
        txtProgress.setText("0 %");
        cmbLocation.setValue(null);
        sliderProgress.setValue(0);
        progressBaterryLife.setProgress(0);
        eployeeTab.selectedProperty();
        btnSave.setDisable(false);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnNew.setDisable(true);
    }

}
