package com.cryo.unluac.decompile.operation;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.block.Block;
import com.cryo.unluac.decompile.expression.FunctionCall;
import com.cryo.unluac.decompile.statement.FunctionCallStatement;
import com.cryo.unluac.decompile.statement.Statement;

public class CallOperation extends Operation {

  private FunctionCall call;
  
  public CallOperation(int line, FunctionCall call) {
    super(line);
    this.call = call;
  }

  @Override
  public Statement process(Registers r, Block block) {
    return new FunctionCallStatement(call);
  }
  
}
