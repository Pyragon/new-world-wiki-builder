package com.cryo.unluac.decompile.operation;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.block.Block;
import com.cryo.unluac.decompile.expression.Expression;
import com.cryo.unluac.decompile.statement.Assignment;
import com.cryo.unluac.decompile.statement.Statement;
import com.cryo.unluac.decompile.target.UpvalueTarget;

public class UpvalueSet extends Operation {

  private UpvalueTarget target;
  private Expression value;
  
  public UpvalueSet(int line, String upvalue, Expression value) {
    super(line);
    target = new UpvalueTarget(upvalue);
    this.value = value;
  }

  @Override
  public Statement process(Registers r, Block block) {
    return new Assignment(target, value);
  }
  
}
