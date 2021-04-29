package Z2;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.receptionist.Receptionist;

import java.util.LinkedList;
import java.util.List;

public class ActorTextService extends AbstractBehavior<ActorTextService.Command> {

    // --- constructor and create
    // TODO: field for message adapter
    private final ActorRef<Receptionist.Listing> messageAdapter;
    private final List<ActorRef<String>> workers = new LinkedList<>();

    // TODO: new message type implementing Command, with Receptionist.Listing field

    public ActorTextService(ActorContext<Command> context) {
        super(context);

        // TODO: create message adapter
        messageAdapter = context.messageAdapter(Receptionist.Listing.class, ListingResponse::new);
        // TODO: subscribe to receptionist (with message adapter)
        context
                .getSystem()
                .receptionist()
                .tell(Receptionist.subscribe(ActorUpperCase.upperCaseServiceKey, messageAdapter));
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(ActorTextService::new);
    }

    // --- define message handlers
    @Override
    public Receive<Command> createReceive() {

        System.out.println("creating receive for text service");

        return newReceiveBuilder()
                .onMessage(Request.class, this::onRequest)
                // TODO: handle the new type of message
                .onMessage(ListingResponse.class, this::onListingRequest)
                .build();
    }

    private Behavior<Command> onRequest(Request msg) {
        System.out.println("request: " + msg.text);
        for (ActorRef<String> worker : workers) {
            System.out.println("sending to worker: " + worker);
            worker.tell(msg.text);
        }
        return this;
    }

    private Behavior<Command> onListingRequest(ListingResponse msg) {
        workers.clear();
        workers.addAll(msg.listing.getAllServiceInstances(ActorUpperCase.upperCaseServiceKey));
        return this;
    }

    // --- messages
    interface Command {
    }

    public static class Request implements Command {
        final String text;

        public Request(String text) {
            this.text = text;
        }
    }

    // TODO: handle the new type of message
    public static class ListingResponse implements Command {
        final Receptionist.Listing listing;

        public ListingResponse(Receptionist.Listing listing) {
            this.listing = listing;
        }
    }

}
