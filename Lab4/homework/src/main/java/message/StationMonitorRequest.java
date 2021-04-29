package message;

public class StationMonitorRequest implements SatelliteMessage {
    public final int firstSatelliteID;
    public final int range;
    public final int timeout;

    public StationMonitorRequest(int firstSatelliteID, int range, int timeout) {
        this.firstSatelliteID = firstSatelliteID;
        this.range = range;
        this.timeout = timeout;
    }
}
