package message;

import satellite.SatelliteAPI;

import java.util.HashMap;

public class DatabaseUpdate implements SatelliteMessage {
    public final HashMap<Integer, SatelliteAPI.Status> errors;

    public DatabaseUpdate(HashMap<Integer, SatelliteAPI.Status> errors) {
        this.errors = errors;
    }
}
