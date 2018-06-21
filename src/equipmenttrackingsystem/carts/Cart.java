/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipmenttrackingsystem.carts;

import equipmenttrackingsystem.employee.*;
import equipmenttrackingsystem.exceptions.DataStorageException;
import equipmenttrackingsystem.exceptions.NotFoundException;

/**
 *
 * @author CE
 */
public class Cart {

    int CartID, LocationID;
    String BatteryLife;

    public Cart() {
        this(0, null, 0);

    }

    public Cart(int CartID, String BatteryLife, int LocationID) {
        this.CartID = CartID;
        this.BatteryLife = BatteryLife;
        this.LocationID = LocationID;
    }

    public void setCartID(int CartID) {
        this.CartID = CartID;
    }

    public void setBatteryLife(String BatteryLife) {
        this.BatteryLife = BatteryLife;
    }

    public void setLocationID(int LocationID) {
        this.LocationID = LocationID;
    }

    public int getCartID() {
        return CartID;
    }

    public String getBatteryLife() {
        return BatteryLife;
    }

    public int getLocationID() {
        return LocationID;
    }

    /**
     * Terminate the data storage
     *
     * @throws DataStorageException This exception is raised when there was a
     * problem saving to the data storage device
     */
    public boolean addNewCart() throws DataStorageException {
        return CartDA.addNewCart(this);
    }

    public boolean deleteCart() throws DataStorageException {
        return CartDA.deleteCart(this);
    }

    public boolean updateCart() throws DataStorageException {
        return CartDA.updateCart(this);
    }

    public static java.util.ArrayList<Cart> getAll() {
        return CartDA.getAll();
    }

    public static int find(int id) throws DataStorageException, NotFoundException {
        return CartDA.find(id);
    }

    public static java.util.ArrayList<Cart> getAllCart() throws DataStorageException, NotFoundException {
        return CartDA.getAllCart();
    }
}
