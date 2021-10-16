package com.cryo.unluac.decompile.operation;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.block.Block;
import com.cryo.unluac.decompile.expression.Expression;
import com.cryo.unluac.decompile.statement.Assignment;
import com.cryo.unluac.decompile.statement.Statement;
import com.cryo.unluac.decompile.target.GlobalTarget;

public class GlobalSet extends Operation {

  private String global;
  private Expression value;
  
  public GlobalSet(int line, String global, Expression value) {
    super(line);
    this.global = global;
    this.value = value;
  }

  @Override
  public Statement process(Registers r, Block block) {
    return new Assignment(new GlobalTarget(global), value);
  }
  
}
