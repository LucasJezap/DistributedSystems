// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: crypto.proto

package crypto;

public final class Crypto {
  private Crypto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Request_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Request_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Response_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014crypto.proto\"6\n\007Request\022\014\n\004name\030\001 \001(\t\022" +
      "\035\n\ncurrencies\030\002 \003(\0162\t.Currency\"L\n\010Respon" +
      "se\022\033\n\010currency\030\001 \001(\0162\t.Currency\022\r\n\005price" +
      "\030\002 \001(\002\022\024\n\014price_change\030\003 \001(\t*3\n\010Currency" +
      "\022\013\n\007BITCOIN\020\000\022\014\n\010ETHEREUM\020\001\022\014\n\010LITECOIN\020" +
      "\00220\n\010Messages\022$\n\tsubscribe\022\010.Request\032\t.R" +
      "esponse\"\0000\001B\n\n\006cryptoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Request_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Request_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Request_descriptor,
        new java.lang.String[] { "Name", "Currencies", });
    internal_static_Response_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Response_descriptor,
        new java.lang.String[] { "Currency", "Price", "PriceChange", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}