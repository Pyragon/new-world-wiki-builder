package com.cryo.unluac.parse;

public class LNil extends LObject{

  public static final LNil NIL = new LNil();
  
  private LNil() {
    
  }
 
  @Override
  public boolean equals(Object o) {
    return this == o;
  }
  
}
