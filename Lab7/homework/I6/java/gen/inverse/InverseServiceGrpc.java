package inverse;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: java/src/main/proto/inverse.proto")
public final class InverseServiceGrpc {

  private InverseServiceGrpc() {}

  public static final String SERVICE_NAME = "inverse.InverseService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<inverse.InverseRequest,
      inverse.InverseResponse> getInverseNumberMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "inverseNumber",
      requestType = inverse.InverseRequest.class,
      responseType = inverse.InverseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<inverse.InverseRequest,
      inverse.InverseResponse> getInverseNumberMethod() {
    io.grpc.MethodDescriptor<inverse.InverseRequest, inverse.InverseResponse> getInverseNumberMethod;
    if ((getInverseNumberMethod = InverseServiceGrpc.getInverseNumberMethod) == null) {
      synchronized (InverseServiceGrpc.class) {
        if ((getInverseNumberMethod = InverseServiceGrpc.getInverseNumberMethod) == null) {
          InverseServiceGrpc.getInverseNumberMethod = getInverseNumberMethod =
              io.grpc.MethodDescriptor.<inverse.InverseRequest, inverse.InverseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "inverseNumber"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  inverse.InverseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  inverse.InverseResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InverseServiceMethodDescriptorSupplier("inverseNumber"))
              .build();
        }
      }
    }
    return getInverseNumberMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InverseServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InverseServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InverseServiceStub>() {
        @java.lang.Override
        public InverseServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InverseServiceStub(channel, callOptions);
        }
      };
    return InverseServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InverseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InverseServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InverseServiceBlockingStub>() {
        @java.lang.Override
        public InverseServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InverseServiceBlockingStub(channel, callOptions);
        }
      };
    return InverseServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InverseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InverseServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InverseServiceFutureStub>() {
        @java.lang.Override
        public InverseServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InverseServiceFutureStub(channel, callOptions);
        }
      };
    return InverseServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class InverseServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void inverseNumber(inverse.InverseRequest request,
        io.grpc.stub.StreamObserver<inverse.InverseResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInverseNumberMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getInverseNumberMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                inverse.InverseRequest,
                inverse.InverseResponse>(
                  this, METHODID_INVERSE_NUMBER)))
          .build();
    }
  }

  /**
   */
  public static final class InverseServiceStub extends io.grpc.stub.AbstractAsyncStub<InverseServiceStub> {
    private InverseServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InverseServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InverseServiceStub(channel, callOptions);
    }

    /**
     */
    public void inverseNumber(inverse.InverseRequest request,
        io.grpc.stub.StreamObserver<inverse.InverseResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInverseNumberMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InverseServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<InverseServiceBlockingStub> {
    private InverseServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InverseServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InverseServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public inverse.InverseResponse inverseNumber(inverse.InverseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInverseNumberMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InverseServiceFutureStub extends io.grpc.stub.AbstractFutureStub<InverseServiceFutureStub> {
    private InverseServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InverseServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InverseServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<inverse.InverseResponse> inverseNumber(
        inverse.InverseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInverseNumberMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INVERSE_NUMBER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InverseServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InverseServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INVERSE_NUMBER:
          serviceImpl.inverseNumber((inverse.InverseRequest) request,
              (io.grpc.stub.StreamObserver<inverse.InverseResponse>) responseObserver);
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

  private static abstract class InverseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InverseServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return inverse.Inverse.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InverseService");
    }
  }

  private static final class InverseServiceFileDescriptorSupplier
      extends InverseServiceBaseDescriptorSupplier {
    InverseServiceFileDescriptorSupplier() {}
  }

  private static final class InverseServiceMethodDescriptorSupplier
      extends InverseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    InverseServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (InverseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InverseServiceFileDescriptorSupplier())
              .addMethod(getInverseNumberMethod())
              .build();
        }
      }
    }
    return result;
  }
}
