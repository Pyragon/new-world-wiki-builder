package com.cryo.unluac.decompile.target;

import com.cryo.unluac.decompile.Declaration;
import com.cryo.unluac.decompile.Decompiler;
import com.cryo.unluac.decompile.Output;

abstract public class Target {

  abstract public void print(Decompiler d, Output out);
  
  abstract public void printMethod(Decompiler d, Output out);
  
  public boolean isDeclaration(Declaration decl) {
    return false;
  }
  
  public boolean isLocal() {
    return false;
  }
  
  public int getIndex() {
    throw new IllegalStateException();
  }
  
  public boolean isFunctionName() {
    return true;
  }
  
  public boolean beginsWithParen() {
    return false;
  }
  
}
