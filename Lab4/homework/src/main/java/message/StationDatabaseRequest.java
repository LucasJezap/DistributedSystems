package message;

public class StationDatabaseRequest implements SatelliteMessage {
    public final int satelliteID;

    public StationDatabaseRequest(int satelliteID) {
        this.satelliteID = satelliteID;
    }
}
