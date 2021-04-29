package message;


import akka.actor.typed.ActorRef;
import satellite.SatelliteAPI;

public class MonitorQueryResponse implements SatelliteMessage {

    public final ActorRef<SatelliteMessage> station;
    public final int queryID;
    public final int satelliteID;
    public final SatelliteAPI.Status replyCode;

    public MonitorQueryResponse(ActorRef<SatelliteMessage> station, int queryID, int satelliteID, SatelliteAPI.Status replyCode) {
        this.station = station;

        this.queryID = queryID;
        this.satelliteID = satelliteID;
        this.replyCode = replyCode;
    }
}
