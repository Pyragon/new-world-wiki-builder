package com.cryo.unluac.decompile.target;

import com.cryo.unluac.decompile.Decompiler;
import com.cryo.unluac.decompile.Output;

public class UpvalueTarget extends Target{

  private final String name;
  
  public UpvalueTarget(String name) {
    this.name = name;
  }

  @Override
  public void print(Decompiler d, Output out) {
    out.print(name);    
  }
  
  @Override
  public void printMethod(Decompiler d, Output out) {
    throw new IllegalStateException();
  }
  
}
