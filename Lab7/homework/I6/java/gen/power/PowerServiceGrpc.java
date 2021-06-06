package power;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: java/src/main/proto/power.proto")
public final class PowerServiceGrpc {

  private PowerServiceGrpc() {}

  public static final String SERVICE_NAME = "power.PowerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<power.PowerRequest,
      power.PowerResponse> getPowerNumberMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "powerNumber",
      requestType = power.PowerRequest.class,
      responseType = power.PowerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<power.PowerRequest,
      power.PowerResponse> getPowerNumberMethod() {
    io.grpc.MethodDescriptor<power.PowerRequest, power.PowerResponse> getPowerNumberMethod;
    if ((getPowerNumberMethod = PowerServiceGrpc.getPowerNumberMethod) == null) {
      synchronized (PowerServiceGrpc.class) {
        if ((getPowerNumberMethod = PowerServiceGrpc.getPowerNumberMethod) == null) {
          PowerServiceGrpc.getPowerNumberMethod = getPowerNumberMethod =
              io.grpc.MethodDescriptor.<power.PowerRequest, power.PowerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "powerNumber"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  power.PowerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  power.PowerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PowerServiceMethodDescriptorSupplier("powerNumber"))
              .build();
        }
      }
    }
    return getPowerNumberMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PowerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PowerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PowerServiceStub>() {
        @java.lang.Override
        public PowerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PowerServiceStub(channel, callOptions);
        }
      };
    return PowerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PowerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PowerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PowerServiceBlockingStub>() {
        @java.lang.Override
        public PowerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PowerServiceBlockingStub(channel, callOptions);
        }
      };
    return PowerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PowerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PowerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PowerServiceFutureStub>() {
        @java.lang.Override
        public PowerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PowerServiceFutureStub(channel, callOptions);
        }
      };
    return PowerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PowerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void powerNumber(power.PowerRequest request,
        io.grpc.stub.StreamObserver<power.PowerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPowerNumberMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPowerNumberMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                power.PowerRequest,
                power.PowerResponse>(
                  this, METHODID_POWER_NUMBER)))
          .build();
    }
  }

  /**
   */
  public static final class PowerServiceStub extends io.grpc.stub.AbstractAsyncStub<PowerServiceStub> {
    private PowerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PowerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PowerServiceStub(channel, callOptions);
    }

    /**
     */
    public void powerNumber(power.PowerRequest request,
        io.grpc.stub.StreamObserver<power.PowerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPowerNumberMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PowerServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<PowerServiceBlockingStub> {
    private PowerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PowerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PowerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public power.PowerResponse powerNumber(power.PowerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPowerNumberMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PowerServiceFutureStub extends io.grpc.stub.AbstractFutureStub<PowerServiceFutureStub> {
    private PowerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PowerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PowerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<power.PowerResponse> powerNumber(
        power.PowerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPowerNumberMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_POWER_NUMBER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PowerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PowerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_POWER_NUMBER:
          serviceImpl.powerNumber((power.PowerRequest) request,
              (io.grpc.stub.StreamObserver<power.PowerResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PowerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PowerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return power.Power.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PowerService");
    }
  }

  private static final class PowerServiceFileDescriptorSupplier
      extends PowerServiceBaseDescriptorSupplier {
    PowerServiceFileDescriptorSupplier() {}
  }

  private static final class PowerServiceMethodDescriptorSupplier
      extends PowerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PowerServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PowerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PowerServiceFileDescriptorSupplier())
              .addMethod(getPowerNumberMethod())
              .build();
        }
      }
    }
    return result;
  }
}
