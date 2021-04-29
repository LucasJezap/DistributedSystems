package message;

import akka.actor.typed.ActorRef;

public class DatabaseResponse implements SatelliteMessage {
    public final ActorRef<SatelliteMessage> station;
    public final int satelliteID;
    public final int errors;

    public DatabaseResponse(ActorRef<SatelliteMessage> station, int satelliteID, int errors) {
        this.station = station;
        this.satelliteID = satelliteID;
        this.errors = errors;
    }
}
