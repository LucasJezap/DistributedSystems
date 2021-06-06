package square;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: java/src/main/proto/square.proto")
public final class SquareServiceGrpc {

  private SquareServiceGrpc() {}

  public static final String SERVICE_NAME = "square.SquareService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<square.SquareRequest,
      square.SquareResponse> getSquareNumberMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "squareNumber",
      requestType = square.SquareRequest.class,
      responseType = square.SquareResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<square.SquareRequest,
      square.SquareResponse> getSquareNumberMethod() {
    io.grpc.MethodDescriptor<square.SquareRequest, square.SquareResponse> getSquareNumberMethod;
    if ((getSquareNumberMethod = SquareServiceGrpc.getSquareNumberMethod) == null) {
      synchronized (SquareServiceGrpc.class) {
        if ((getSquareNumberMethod = SquareServiceGrpc.getSquareNumberMethod) == null) {
          SquareServiceGrpc.getSquareNumberMethod = getSquareNumberMethod =
              io.grpc.MethodDescriptor.<square.SquareRequest, square.SquareResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "squareNumber"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  square.SquareRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  square.SquareResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SquareServiceMethodDescriptorSupplier("squareNumber"))
              .build();
        }
      }
    }
    return getSquareNumberMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SquareServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SquareServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SquareServiceStub>() {
        @java.lang.Override
        public SquareServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SquareServiceStub(channel, callOptions);
        }
      };
    return SquareServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SquareServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SquareServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SquareServiceBlockingStub>() {
        @java.lang.Override
        public SquareServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SquareServiceBlockingStub(channel, callOptions);
        }
      };
    return SquareServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SquareServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SquareServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SquareServiceFutureStub>() {
        @java.lang.Override
        public SquareServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SquareServiceFutureStub(channel, callOptions);
        }
      };
    return SquareServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SquareServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void squareNumber(square.SquareRequest request,
        io.grpc.stub.StreamObserver<square.SquareResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSquareNumberMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSquareNumberMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                square.SquareRequest,
                square.SquareResponse>(
                  this, METHODID_SQUARE_NUMBER)))
          .build();
    }
  }

  /**
   */
  public static final class SquareServiceStub extends io.grpc.stub.AbstractAsyncStub<SquareServiceStub> {
    private SquareServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SquareServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SquareServiceStub(channel, callOptions);
    }

    /**
     */
    public void squareNumber(square.SquareRequest request,
        io.grpc.stub.StreamObserver<square.SquareResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSquareNumberMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SquareServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SquareServiceBlockingStub> {
    private SquareServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SquareServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SquareServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public square.SquareResponse squareNumber(square.SquareRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSquareNumberMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SquareServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SquareServiceFutureStub> {
    private SquareServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SquareServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SquareServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<square.SquareResponse> squareNumber(
        square.SquareRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSquareNumberMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SQUARE_NUMBER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SquareServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SquareServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SQUARE_NUMBER:
          serviceImpl.squareNumber((square.SquareRequest) request,
              (io.grpc.stub.StreamObserver<square.SquareResponse>) responseObserver);
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

  private static abstract class SquareServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SquareServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return square.Square.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SquareService");
    }
  }

  private static final class SquareServiceFileDescriptorSupplier
      extends SquareServiceBaseDescriptorSupplier {
    SquareServiceFileDescriptorSupplier() {}
  }

  private static final class SquareServiceMethodDescriptorSupplier
      extends SquareServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SquareServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SquareServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SquareServiceFileDescriptorSupplier())
              .addMethod(getSquareNumberMethod())
              .build();
        }
      }
    }
    return result;
  }
}
