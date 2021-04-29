import actor.Dispatcher;
import actor.Station;
import akka.actor.typed.*;
import akka.actor.typed.javadsl.Behaviors;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import message.SatelliteMessage;
import message.StationDatabaseRequest;
import message.StationMonitorRequest;
import org.bson.Document;

import java.io.File;
import java.util.Random;

public class Main {
    public static void initDatabase(MongoClient client) {
        MongoCollection<Document> satelliteCollection = client.getDatabase("AstraLink").getCollection("SatelliteErrors");

        for (int satelliteID = 100; satelliteID <= 199; satelliteID++) {
            satelliteCollection.updateOne(Filters.eq("_id", satelliteID), Updates.set("Errors", 0), new UpdateOptions().upsert(true));
        }
    }

    public static Behavior<Void> createSatelliteSystem(int numWorkers, MongoClient databaseConnection) {
        return Behaviors.setup(context -> {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");

            ActorRef<SatelliteMessage> dispatcher = context.
                    spawn(Dispatcher.create(numWorkers, databaseConnection), "dispatcher",
                            DispatcherSelector.fromConfig("custom-dispatcher"));
            ActorRef<SatelliteMessage> station1 = context.
                    spawn(Station.create("station1", dispatcher), "station1",
                            DispatcherSelector.fromConfig("custom-dispatcher"));
            ActorRef<SatelliteMessage> station2 = context.
                    spawn(Station.create("station2", dispatcher), "station2",
                            DispatcherSelector.fromConfig("custom-dispatcher"));
            ActorRef<SatelliteMessage> station3 = context.
                    spawn(Station.create("station3", dispatcher), "station3",
                            DispatcherSelector.fromConfig("custom-dispatcher"));

            Random random = new Random();
            station1.tell(new StationMonitorRequest(100 + random.nextInt(50), 50, 300));
            station1.tell(new StationMonitorRequest(100 + random.nextInt(50), 50, 300));
            station2.tell(new StationMonitorRequest(100 + random.nextInt(50), 50, 300));
            station2.tell(new StationMonitorRequest(100 + random.nextInt(50), 50, 300));
            station3.tell(new StationMonitorRequest(100 + random.nextInt(50), 50, 300));
            station3.tell(new StationMonitorRequest(100 + random.nextInt(50), 50, 300));

            Thread.sleep(1000);

            for (int satelliteID = 100; satelliteID <= 199; satelliteID++) {
                station1.tell(new StationDatabaseRequest(satelliteID));
            }

            return Behaviors.receive(Void.class)
                    .onSignal(Terminated.class, sig -> Behaviors.stopped())
                    .build();
        });
    }

    public static void main(String[] args) {
        File file = new File("src/main/resources/application.conf");
        Config config = ConfigFactory.parseFile(file);
        MongoClient databaseConnection = new MongoClient();
        Main.initDatabase(databaseConnection);
        ActorSystem.create(Main.createSatelliteSystem(100, databaseConnection), "AstraLink", config);
    }
}
