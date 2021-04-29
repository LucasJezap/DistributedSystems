package actor;

import message.*;
import satellite.SatelliteAPI;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class SatelliteWorker extends AbstractBehavior<SatelliteMessage> {

    private final ActorRef<SatelliteMessage> dispatcher;
    private final MongoCollection<Document> database;
    private final ExecutorService threadPool;

    public SatelliteWorker(ActorContext<SatelliteMessage> context, ActorRef<SatelliteMessage> dispatcher, MongoClient database, ExecutorService threadPool) {
        super(context);
        this.dispatcher = dispatcher;
        this.database = database.getDatabase("AstraLink").getCollection("SatelliteErrors");
        this.threadPool = threadPool;
    }

    public static Behavior<SatelliteMessage> create(ActorRef<SatelliteMessage> dispatcher, MongoClient database, ExecutorService threadPool) {
        return Behaviors.setup(context -> new SatelliteWorker(context, dispatcher, database, threadPool).createReceive());
    }

    @Override
    public Receive<SatelliteMessage> createReceive() {
        return newReceiveBuilder().
                onMessage(DatabaseUpdate.class, this::onDatabaseUpdate).
                onMessage(DatabaseRequest.class, this::onDatabaseRequest).
                onMessage(MonitorQuery.class, this::onMonitorQuery).
                build();
    }

    private Behavior<SatelliteMessage> onDatabaseUpdate(DatabaseUpdate msg) {
        this.threadPool.submit(() ->
                this.database.updateMany(
                        Filters.in("_id", msg.errors.keySet()),
                        Updates.inc("Errors", 1)
                ));
        return this;
    }

    private Behavior<SatelliteMessage> onDatabaseRequest(DatabaseRequest msg) {
        Document satelliteData = this.database.
                find(Filters.eq("_id", msg.satelliteID)).first();

        this.dispatcher.tell(new DatabaseResponse(
                msg.station,
                msg.satelliteID,
                (int) Objects.requireNonNull(satelliteData).get("Errors")
        ));

        return this;
    }

    private Behavior<SatelliteMessage> onMonitorQuery(MonitorQuery msg) {
        MonitorQueryResponse timeoutResponse = new MonitorQueryResponse(
                msg.station,
                msg.queryID,
                msg.satelliteID,
                null
        );
        ActorSystem<Void> workerSystem = getContext().getSystem();
        workerSystem.scheduler().scheduleOnce(Duration.ofMillis(msg.timeout),
                () -> this.dispatcher.tell(timeoutResponse),
                workerSystem.executionContext());

        this.threadPool.submit(() -> {
            SatelliteAPI.Status satelliteStatus = SatelliteAPI.getStatus(msg.satelliteID);
            MonitorQueryResponse statusResponse = new MonitorQueryResponse(
                    msg.station,
                    msg.queryID,
                    msg.satelliteID,
                    satelliteStatus
            );
            this.dispatcher.tell(statusResponse);
        });

        return this;
    }
}
