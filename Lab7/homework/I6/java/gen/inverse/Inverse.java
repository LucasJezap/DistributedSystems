// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: java/src/main/proto/inverse.proto

package inverse;

public final class Inverse {
  private Inverse() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_inverse_InverseRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_inverse_InverseRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_inverse_InverseResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_inverse_InverseResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n!java/src/main/proto/inverse.proto\022\007inv" +
      "erse\" \n\016InverseRequest\022\016\n\006number\030\001 \001(\002\"\"" +
      "\n\017InverseResponse\022\017\n\007inverse\030\001 \001(\0022V\n\016In" +
      "verseService\022D\n\rinverseNumber\022\027.inverse." +
      "InverseRequest\032\030.inverse.InverseResponse" +
      "\"\000B\024\n\007inverseB\007InverseP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_inverse_InverseRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_inverse_InverseRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_inverse_InverseRequest_descriptor,
        new java.lang.String[] { "Number", });
    internal_static_inverse_InverseResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_inverse_InverseResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_inverse_InverseResponse_descriptor,
        new java.lang.String[] { "Inverse", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}