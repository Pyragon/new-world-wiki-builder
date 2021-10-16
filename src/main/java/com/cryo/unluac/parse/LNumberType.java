package com.cryo.unluac.parse;

import java.nio.ByteBuffer;


public class LNumberType extends BObjectType<LNumber> {

  public static enum NumberMode {
    MODE_NUMBER, // Used for Lua 5.0 - 5.2 where numbers can represent integers or floats
    MODE_FLOAT, // Used for floats in Lua 5.3
    MODE_INTEGER, // Used for integers in Lua 5.3
  }
  
  public final int size;
  public final boolean integral;
  public final NumberMode mode;
  
  public LNumberType(int size, boolean integral, NumberMode mode) {
    this.size = size;
    this.integral = integral;
    this.mode = mode;
    if(!(size == 4 || size == 8)) {
      throw new IllegalStateException("The input chunk has an unsupported Lua number size: " + size);
    }
  }
  
  public double convert(double number) {
    if(integral) {
      switch(size) {
        case 4:
          return (int)number;
        case 8:
          return (long)number;
      }
    } else {
      switch(size) {
        case 4:
          return (float)number;
        case 8:
          return number;
      }
    }
    throw new IllegalStateException("The input chunk has an unsupported Lua number format");
  }
  
  @Override
  public LNumber parse(ByteBuffer buffer, BHeader header) {
    LNumber value = null;
    if(integral) {
      switch(size) {
        case 4:
          value = new LIntNumber(buffer.getInt());
          break;
        case 8:
          value = new LLongNumber(buffer.getLong());
          break;
      }
    } else {
      switch(size) {
        case 4:
          value = new LFloatNumber(buffer.getFloat(), mode);
          break;
        case 8:
          value = new LDoubleNumber(buffer.getDouble(), mode);
          break;
      }
    }
    if(value == null) {
      throw new IllegalStateException("The input chunk has an unsupported Lua number format");
    }
    if(header.debug) {
      System.out.println("-- parsed <number> " + value);
    }
    return value;
  }

}
