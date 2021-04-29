package message;

import akka.actor.typed.ActorRef;

public class DatabaseRequest implements SatelliteMessage {
    public final ActorRef<SatelliteMessage> station;
    public final int satelliteID;


    public DatabaseRequest(ActorRef<SatelliteMessage> station, int satelliteID) {
        this.station = station;
        this.satelliteID = satelliteID;
    }
}
