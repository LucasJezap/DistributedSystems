package message;

import akka.actor.typed.ActorRef;

public class MonitorQuery implements SatelliteMessage {

    public final ActorRef<SatelliteMessage> station;
    public final int queryID;
    public final int satelliteID;
    public final int timeout;

    public MonitorQuery(ActorRef<SatelliteMessage> station, int queryID, int satelliteID, int timeout) {
        this.station = station;
        this.queryID = queryID;
        this.satelliteID = satelliteID;
        this.timeout = timeout;
    }
}
