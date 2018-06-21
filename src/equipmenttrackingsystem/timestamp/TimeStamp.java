package equipmenttrackingsystem.timestamp;

import equipmenttrackingsystem.exceptions.DataRepetitionException;

/**
 *
 * @author Joseph
 */
public class TimeStamp {

    private int TimeStampID, AllocationID, LocationID;

    public TimeStamp() {
        this(0, 0, 0);
    }

    public TimeStamp(int TimeStampID, int AllocationID, int LocationID) {
        this.TimeStampID = TimeStampID;
        this.AllocationID = AllocationID;
        this.LocationID = LocationID;
    }

    public void setTimeStampID(int TimeStampID) {
        this.TimeStampID = TimeStampID;
    }

    public void setAllocationID(int AllocationID) {
        this.AllocationID = AllocationID;
    }

    public void setLocationID(int LocationID) {
        this.LocationID = LocationID;
    }

    public int getTimeStampID() {
        return TimeStampID;
    }

    public int getAllocationID() {
        return AllocationID;
    }

    public int getLocationID() {
        return LocationID;
    }

    public boolean addTimeStamp(TimeStamp owners) throws DataRepetitionException {
        return TimeStampDA.isTimeStamp(this);
    }

    @Override
    public String toString() {
        return "TimeStamp{" + "TimeStampID=" + TimeStampID + ", AllocationID=" + AllocationID + ", LocationID=" + LocationID + '}';
    }
}
