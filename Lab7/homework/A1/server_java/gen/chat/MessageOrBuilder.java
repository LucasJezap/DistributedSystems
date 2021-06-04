// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package chat;

public interface MessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Message)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.Room room = 1;</code>
   * @return The enum numeric value on the wire for room.
   */
  int getRoomValue();
  /**
   * <code>.Room room = 1;</code>
   * @return The room.
   */
  chat.Room getRoom();

  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>.Client sender = 3;</code>
   * @return Whether the sender field is set.
   */
  boolean hasSender();
  /**
   * <code>.Client sender = 3;</code>
   * @return The sender.
   */
  chat.Client getSender();
  /**
   * <code>.Client sender = 3;</code>
   */
  chat.ClientOrBuilder getSenderOrBuilder();
}
