// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: crypto.proto

package crypto;

public interface ResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Response)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.Currency currency = 1;</code>
   * @return The enum numeric value on the wire for currency.
   */
  int getCurrencyValue();
  /**
   * <code>.Currency currency = 1;</code>
   * @return The currency.
   */
  crypto.Currency getCurrency();

  /**
   * <code>float price = 2;</code>
   * @return The price.
   */
  float getPrice();

  /**
   * <code>string price_change = 3;</code>
   * @return The priceChange.
   */
  java.lang.String getPriceChange();
  /**
   * <code>string price_change = 3;</code>
   * @return The bytes for priceChange.
   */
  com.google.protobuf.ByteString
      getPriceChangeBytes();
}
