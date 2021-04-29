package actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.DispatcherSelector;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.mongodb.MongoClient;
import message.*;
import query.QueryStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher extends AbstractBehavior<SatelliteMessage> {

    private final List<ActorRef<SatelliteMessage>> satelliteWorkers;
    private final QueryStorage queryStorage;

    public Dispatcher(ActorContext<SatelliteMessage> context, int workers, MongoClient database) {
        super(context);
        this.satelliteWorkers = new ArrayList<>();
        this.queryStorage = new QueryStorage();
        ExecutorService threadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < workers; i++) {
            this.satelliteWorkers.add(getContext().spawn(
                    Behaviors.supervise(SatelliteWorker.create(getContext().getSelf(), database, threadPool)).
                            onFailure(Exception.class, SupervisorStrategy.restart()), "SatelliteWorker" + (i + 1),
                    DispatcherSelector.fromConfig("custom-dispatcher")));
        }
    }

    public static Behavior<SatelliteMessage> create(int workers, MongoClient database) {
        return Behaviors.setup(context -> new Dispatcher(context, workers, database).createReceive());
    }

    @Override
    public Receive<SatelliteMessage> createReceive() {
        return newReceiveBuilder().
                onMessage(MonitorRequest.class, this::onMonitorRequest).
                onMessage(MonitorQueryResponse.class, this::onMonitorQueryResponse).
                onMessage(DatabaseRequest.class, this::onDatabaseRequest).
                onMessage(DatabaseResponse.class, this::onDatabaseResponse).
                build();
    }

    private Behavior<SatelliteMessage> onMonitorRequest(MonitorRequest msg) {
        this.queryStorage.addStationQuery(msg);
        for (int i = 0; i < msg.range; i++) {
            this.getRandomSatelliteWorker().tell(new MonitorQuery(
                    msg.station,
                    msg.queryID,
                    msg.firstSatelliteID + i,
                    msg.timeout
            ));
        }
        return this;
    }

    private Behavior<SatelliteMessage> onMonitorQueryResponse(MonitorQueryResponse msg) {
        if (this.queryStorage.isQueryActive(msg)) {
            this.queryStorage.updateQuery(msg);
            if (this.queryStorage.isQueryCompleted(msg)) {
                MonitorResponse response = this.queryStorage.getMonitorResponse(msg);
                msg.station.tell(response);
                this.getRandomSatelliteWorker().tell(new DatabaseUpdate(response.errors));
            }
        }
        return this;
    }

    private Behavior<SatelliteMessage> onDatabaseRequest(DatabaseRequest msg) {
        this.getRandomSatelliteWorker().tell(msg);
        return this;
    }

    private Behavior<SatelliteMessage> onDatabaseResponse(DatabaseResponse msg) {
        msg.station.tell(msg);
        return this;
    }

    private ActorRef<SatelliteMessage> getRandomSatelliteWorker() {
        return this.satelliteWorkers.get(new Random().nextInt(this.satelliteWorkers.size()));
    }
}
