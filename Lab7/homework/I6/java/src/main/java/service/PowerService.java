package service;

import io.grpc.stub.StreamObserver;
import power.PowerRequest;
import power.PowerResponse;
import power.PowerServiceGrpc;
import server.SquareServer;

public class PowerService extends PowerServiceGrpc.PowerServiceImplBase {

    @Override
    public void powerNumber(PowerRequest request, StreamObserver<PowerResponse> responseObserver) {
        System.out.println(SquareServer.printTimestamp() +
                "Received a power request for number " + request.getNumber() + ".");
        float power = (float) Math.pow(request.getNumber(), 2);
        responseObserver.onNext(PowerResponse.newBuilder().setPower(power).build());
        responseObserver.onCompleted();
        System.out.println(SquareServer.printTimestamp() + "The result is " + power + ".");
    }
}
