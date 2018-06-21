package equipmenttrackingsystem.timestamp;


import equipmenttrackingsystem.db.CreateConnection;
import equipmenttrackingsystem.exceptions.DataRepetitionException;
import equipmenttrackingsystem.exceptions.DataStorageException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 *
 * @author
 */
public class TimeStampDA {

    private static Connection connection;
    private static Statement st;
    private static PreparedStatement ps;
    private static ResultSet result;

    public TimeStampDA() {
        try {
            connection = CreateConnection.initialise();
        } catch (DataStorageException e) {
            JOptionPane.showMessageDialog(null, "Connection err", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isTimeStamp(TimeStamp owner) throws DataRepetitionException {
        boolean isAdd = false;
        String insertQry = "INSERT INTO timestamp (AllocationID,LocationID) VALUES (?,?)";
        try {
            ps = connection.prepareStatement(insertQry);
            ps.setInt(1, owner.getAllocationID());
            ps.setInt(2, owner.getLocationID());
            
            ps.executeUpdate();
            isAdd = true;

        } catch (SQLException ex) {
            throw new DataRepetitionException(ex.getMessage());
        }
        return isAdd;
    }




}
