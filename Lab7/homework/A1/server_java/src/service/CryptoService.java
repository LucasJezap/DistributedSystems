package service;

import crypto.MessagesGrpc;
import crypto.Request;
import crypto.Response;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import subscription.ClientSubscription;

import java.sql.Timestamp;

public class CryptoService extends MessagesGrpc.MessagesImplBase {
    private final ClientSubscription clientSubscription;

    public CryptoService(ClientSubscription clientSubscription) {
        this.clientSubscription = clientSubscription;
    }

    @Override
    public void subscribe(Request request, StreamObserver<Response> responseObserver) {
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "] New client has joined the server.");
        ServerCallStreamObserver<Response> serverCallStreamObserver =
                (ServerCallStreamObserver<Response>) responseObserver;
        serverCallStreamObserver.setOnCancelHandler(() -> {
            clientSubscription.removeSubscriber(request);
            System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "] The client has disconnected.");
        });
        clientSubscription.addClient(request, responseObserver);
    }
}
