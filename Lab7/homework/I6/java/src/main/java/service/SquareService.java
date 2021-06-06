package service;

import io.grpc.stub.StreamObserver;
import server.SquareServer;
import square.SquareRequest;
import square.SquareResponse;
import square.SquareServiceGrpc;

public class SquareService extends SquareServiceGrpc.SquareServiceImplBase {

    @Override
    public void squareNumber(SquareRequest request, StreamObserver<SquareResponse> responseObserver) {
        System.out.println(SquareServer.printTimestamp() +
                "Received a square request for number " + request.getNumber() + ".");
        float square = (float) Math.sqrt(request.getNumber());
        responseObserver.onNext(SquareResponse.newBuilder().setSquare(square).build());
        responseObserver.onCompleted();
        System.out.println(SquareServer.printTimestamp() + "The result is " + square + ".");
    }
}
