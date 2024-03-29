package Z2;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.Behaviors;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;


public class Z2_NodeA {

    public static Behavior<Void> create() {
        return Behaviors.setup(
                context -> {

                    // TODO : only workers
                    // create workers (they will register by themselves)
                    context.spawn(ActorUpperCase.create(), "upper1");
                    context.spawn(ActorUpperCase.create(), "upper2");

                    return Behaviors.receive(Void.class)
                            .onSignal(Terminated.class, sig -> Behaviors.stopped())
                            .build();
                });
    }

    public static void main(String[] args) {
        File configFile = new File("src/main/nodeA.conf");
        Config config = ConfigFactory.parseFile(configFile);
        System.out.println("Node A: config: " + config);

        ActorSystem.create(Z2_NodeA.create(), "ClusterSystem", config);
    }
}
