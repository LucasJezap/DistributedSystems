package chat;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.0)",
    comments = "Source: chat.proto")
public final class MessagesGrpc {

  private MessagesGrpc() {}

  public static final String SERVICE_NAME = "Messages";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<chat.Client,
      chat.Message> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subscribe",
      requestType = chat.Client.class,
      responseType = chat.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<chat.Client,
      chat.Message> getSubscribeMethod() {
    io.grpc.MethodDescriptor<chat.Client, chat.Message> getSubscribeMethod;
    if ((getSubscribeMethod = MessagesGrpc.getSubscribeMethod) == null) {
      synchronized (MessagesGrpc.class) {
        if ((getSubscribeMethod = MessagesGrpc.getSubscribeMethod) == null) {
          MessagesGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<chat.Client, chat.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.Client.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.Message.getDefaultInstance()))
              .setSchemaDescriptor(new MessagesMethodDescriptorSupplier("subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<chat.Message,
      chat.Response> getSendMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendMessage",
      requestType = chat.Message.class,
      responseType = chat.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<chat.Message,
      chat.Response> getSendMessageMethod() {
    io.grpc.MethodDescriptor<chat.Message, chat.Response> getSendMessageMethod;
    if ((getSendMessageMethod = MessagesGrpc.getSendMessageMethod) == null) {
      synchronized (MessagesGrpc.class) {
        if ((getSendMessageMethod = MessagesGrpc.getSendMessageMethod) == null) {
          MessagesGrpc.getSendMessageMethod = getSendMessageMethod =
              io.grpc.MethodDescriptor.<chat.Message, chat.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  chat.Response.getDefaultInstance()))
              .setSchemaDescriptor(new MessagesMethodDescriptorSupplier("sendMessage"))
              .build();
        }
      }
    }
    return getSendMessageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MessagesStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesStub>() {
        @java.lang.Override
        public MessagesStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesStub(channel, callOptions);
        }
      };
    return MessagesStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MessagesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesBlockingStub>() {
        @java.lang.Override
        public MessagesBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesBlockingStub(channel, callOptions);
        }
      };
    return MessagesBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MessagesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesFutureStub>() {
        @java.lang.Override
        public MessagesFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesFutureStub(channel, callOptions);
        }
      };
    return MessagesFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class MessagesImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribe(chat.Client request,
        io.grpc.stub.StreamObserver<chat.Message> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }

    /**
     */
    public void sendMessage(chat.Message request,
        io.grpc.stub.StreamObserver<chat.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMessageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubscribeMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                chat.Client,
                chat.Message>(
                  this, METHODID_SUBSCRIBE)))
          .addMethod(
            getSendMessageMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                chat.Message,
                chat.Response>(
                  this, METHODID_SEND_MESSAGE)))
          .build();
    }
  }

  /**
   */
  public static final class MessagesStub extends io.grpc.stub.AbstractAsyncStub<MessagesStub> {
    private MessagesStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesStub(channel, callOptions);
    }

    /**
     */
    public void subscribe(chat.Client request,
        io.grpc.stub.StreamObserver<chat.Message> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendMessage(chat.Message request,
        io.grpc.stub.StreamObserver<chat.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MessagesBlockingStub extends io.grpc.stub.AbstractBlockingStub<MessagesBlockingStub> {
    private MessagesBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<chat.Message> subscribe(
        chat.Client request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }

    /**
     */
    public chat.Response sendMessage(chat.Message request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMessageMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MessagesFutureStub extends io.grpc.stub.AbstractFutureStub<MessagesFutureStub> {
    private MessagesFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<chat.Response> sendMessage(
        chat.Message request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;
  private static final int METHODID_SEND_MESSAGE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MessagesImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MessagesImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((chat.Client) request,
              (io.grpc.stub.StreamObserver<chat.Message>) responseObserver);
          break;
        case METHODID_SEND_MESSAGE:
          serviceImpl.sendMessage((chat.Message) request,
              (io.grpc.stub.StreamObserver<chat.Response>) responseObserver);
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

  private static abstract class MessagesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MessagesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return chat.Chat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Messages");
    }
  }

  private static final class MessagesFileDescriptorSupplier
      extends MessagesBaseDescriptorSupplier {
    MessagesFileDescriptorSupplier() {}
  }

  private static final class MessagesMethodDescriptorSupplier
      extends MessagesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MessagesMethodDescriptorSupplier(String methodName) {
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
      synchronized (MessagesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MessagesFileDescriptorSupplier())
              .addMethod(getSubscribeMethod())
              .addMethod(getSendMessageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
