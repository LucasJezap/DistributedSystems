package message;

import akka.actor.typed.ActorRef;

public class MonitorRequest implements SatelliteMessage {
    public final int queryID;
    public final int firstSatelliteID;
    public final int range;
    public final int timeout;
    public final ActorRef<SatelliteMessage> station;

    public MonitorRequest(int queryID, int firstSatelliteID, int range, int timeout, ActorRef<SatelliteMessage> station) {
        this.queryID = queryID;
        this.firstSatelliteID = firstSatelliteID;
        this.range = range;
        this.timeout = timeout;
        this.station = station;
    }
}
