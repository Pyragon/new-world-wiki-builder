package com.cryo.unluac.decompile.operation;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.block.Block;
import com.cryo.unluac.decompile.statement.Statement;

abstract public class Operation {

  public final int line;
  
  public Operation(int line) {
    this.line = line;
  }
  
  abstract public Statement process(Registers r, Block block);
  
}
