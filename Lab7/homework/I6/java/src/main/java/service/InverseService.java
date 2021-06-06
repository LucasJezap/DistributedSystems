package service;

import inverse.InverseRequest;
import inverse.InverseResponse;
import inverse.InverseServiceGrpc;
import io.grpc.stub.StreamObserver;
import server.SquareServer;

public class InverseService extends InverseServiceGrpc.InverseServiceImplBase {

    @Override
    public void inverseNumber(InverseRequest request, StreamObserver<InverseResponse> responseObserver) {
        System.out.println(SquareServer.printTimestamp() +
                "Received an inverse request for number " + request.getNumber() + ".");
        float inverse = (float) 1.0 / request.getNumber();
        responseObserver.onNext(InverseResponse.newBuilder().setInverse(inverse).build());
        responseObserver.onCompleted();
        System.out.println(SquareServer.printTimestamp() + "The result is " + inverse + ".");
    }
}
