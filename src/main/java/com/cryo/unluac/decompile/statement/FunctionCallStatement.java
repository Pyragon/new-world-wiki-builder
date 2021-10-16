package com.cryo.unluac.decompile.statement;

import com.cryo.unluac.decompile.Decompiler;
import com.cryo.unluac.decompile.Output;
import com.cryo.unluac.decompile.expression.FunctionCall;

public class FunctionCallStatement extends Statement {

  private FunctionCall call;
  
  public FunctionCallStatement(FunctionCall call) {
    this.call = call;
  }

  @Override
  public void print(Decompiler d, Output out) {
    call.print(d, out);
  }
  
  @Override
  public boolean beginsWithParen() {
    return call.beginsWithParen();
  }
  
}
