package actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import message.*;

import java.util.HashMap;

public class Station extends AbstractBehavior<SatelliteMessage> {

    private final String stationName;
    private final ActorRef<SatelliteMessage> dispatcher;
    private final HashMap<Integer, Long> queryTimes;
    private int currentQueryId;

    public Station(ActorContext<SatelliteMessage> context, String stationName, ActorRef<SatelliteMessage> dispatcher) {
        super(context);
        this.stationName = stationName;
        this.dispatcher = dispatcher;
        this.queryTimes = new HashMap<>();
        this.currentQueryId = 0;
    }

    public static Behavior<SatelliteMessage> create(String stationName, ActorRef<SatelliteMessage> dispatcher) {
        return Behaviors.setup(context -> new Station(context, stationName, dispatcher).createReceive());
    }

    @Override
    public Receive<SatelliteMessage> createReceive() {
        return newReceiveBuilder().
                onMessage(StationMonitorRequest.class, this::onStationMonitorRequest).
                onMessage(StationDatabaseRequest.class, this::onStationDatabaseRequest).
                onMessage(MonitorResponse.class, this::onMonitorResponse).
                onMessage(DatabaseResponse.class, this::onDatabaseResponse).
                build();
    }

    private Behavior<SatelliteMessage> onStationMonitorRequest(StationMonitorRequest msg) {
        int newQueryID = currentQueryId++;
        this.dispatcher.tell(new MonitorRequest(
                newQueryID,
                msg.firstSatelliteID,
                msg.range,
                msg.timeout,
                getContext().getSelf()
        ));
        this.queryTimes.put(newQueryID, System.currentTimeMillis());
        return this;
    }

    private Behavior<SatelliteMessage> onStationDatabaseRequest(StationDatabaseRequest msg) {
        this.dispatcher.tell(new DatabaseRequest(
                getContext().getSelf(),
                msg.satelliteID
        ));
        return this;
    }

    private Behavior<SatelliteMessage> onMonitorResponse(MonitorResponse msg) {
        long timeElapsed = System.currentTimeMillis() - this.queryTimes.get(msg.queryID);
        System.out.println(
                "StationName: " + stationName + "\n" + "Total time: " + timeElapsed + "ms\nResponses before timeout: "
                        + msg.respondedInTime + "\n" + msg
        );
        this.queryTimes.remove(msg.queryID);
        return this;
    }

    private Behavior<SatelliteMessage> onDatabaseResponse(DatabaseResponse msg) {
        if (msg.errors > 0)
            System.out.println(
                    "(DATABASE) SatelliteId: " + msg.satelliteID + " Errors: " + msg.errors
            );
        return this;
    }
}
