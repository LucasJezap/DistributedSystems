package query;

import satellite.SatelliteAPI;

import java.util.HashMap;

public class Query {
    private int satelliteResponses;
    private int satelliteResponsesBeforeTimeout;
    private final int range;
    private final int firstSatelliteID;
    private final boolean[] responseRecorded;
    private final SatelliteAPI.Status[] responseStatus;

    public Query(int range, int firstSatelliteID) {
        this.satelliteResponses = 0;
        this.satelliteResponsesBeforeTimeout = 0;
        this.range = range;
        this.firstSatelliteID = firstSatelliteID;
        this.responseRecorded = new boolean[range];
        this.responseStatus = new SatelliteAPI.Status[range];
    }

    public boolean isResponseRecorded(int satelliteID) {
        return this.responseRecorded[(satelliteID - this.firstSatelliteID) % 100];
    }

    public void addResponse(int satelliteID, SatelliteAPI.Status response) {
        this.satelliteResponses++;
        if (response != null) {
            this.satelliteResponsesBeforeTimeout++;
        }
        this.responseRecorded[(satelliteID - this.firstSatelliteID) % 100] = true;
        this.responseStatus[(satelliteID - this.firstSatelliteID) % 100] = response;
    }

    public boolean isQueryCompleted() {
        return this.satelliteResponses == this.range;
    }

    public HashMap<Integer, SatelliteAPI.Status> getErrors() {
        HashMap<Integer, SatelliteAPI.Status> errors = new HashMap<>();
        for (int i = 0; i < this.range; i++) {
            if (this.responseStatus[i] != null && this.responseStatus[i] != SatelliteAPI.Status.OK) {
                errors.put(this.firstSatelliteID + i, this.responseStatus[i]);
            }
        }
        return errors;
    }

    public int getSatelliteResponsesBeforeTimeout() {
        return satelliteResponsesBeforeTimeout;
    }
}
