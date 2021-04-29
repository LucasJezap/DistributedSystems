package message;

import satellite.SatelliteAPI;

import java.util.HashMap;
import java.util.Map;

public class MonitorResponse implements SatelliteMessage {
    public final int queryID;
    public final int respondedInTime;
    public final HashMap<Integer, SatelliteAPI.Status> errors;

    public MonitorResponse(int queryID, int respondedInTime, HashMap<Integer, SatelliteAPI.Status> errors) {
        this.queryID = queryID;
        this.respondedInTime = respondedInTime;
        this.errors = errors;
    }


    @Override
    public String toString() {
        StringBuilder listing = new StringBuilder("Errors: " + errors.size() + "\n");
        for (Map.Entry<Integer, SatelliteAPI.Status> idErrorPair : errors.entrySet()) {
            listing.append("SatelliteId: ").
                    append(idErrorPair.getKey()).
                    append(" Error: ").
                    append(idErrorPair.getValue()).
                    append("\n");
        }
        return listing.toString();
    }
}
