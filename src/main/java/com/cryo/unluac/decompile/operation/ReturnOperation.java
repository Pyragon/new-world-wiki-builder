package com.cryo.unluac.decompile.operation;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.block.Block;
import com.cryo.unluac.decompile.expression.Expression;
import com.cryo.unluac.decompile.statement.Return;
import com.cryo.unluac.decompile.statement.Statement;

public class ReturnOperation extends Operation {

  private Expression[] values;
  
  public ReturnOperation(int line, Expression value) {
    super(line);
    values = new Expression[1];
    values[0] = value;
  }
  
  public ReturnOperation(int line, Expression[] values) {
    super(line);
    this.values = values;
  }

  @Override
  public Statement process(Registers r, Block block) {    
    return new Return(values);
  }
  
}
