package com.cryo.unluac.decompile.block;

import com.cryo.unluac.decompile.Decompiler;
import com.cryo.unluac.decompile.Output;
import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.branch.Branch;
import com.cryo.unluac.decompile.operation.Operation;
import com.cryo.unluac.decompile.operation.RegisterSet;
import com.cryo.unluac.decompile.statement.Statement;
import com.cryo.unluac.parse.LFunction;

public class CompareBlock extends Block {

  public int target;
  public Branch branch;
  
  public CompareBlock(LFunction function, int begin, int end, int target, Branch branch) {
    super(function, begin, end);
    this.target = target;
    this.branch = branch;
  }
  
  @Override
  public boolean isContainer() {
    return false;
  }

  @Override
  public boolean breakable() {
    return false;
  }
  
  @Override
  public void addStatement(Statement statement) {
    /* Do nothing */
  }

  @Override
  public boolean isUnprotected() {
    return false;
  }

  @Override
  public int getLoopback() {
    throw new IllegalStateException();
  }

  @Override
  public void print(Decompiler d, Output out) {
    out.print("-- unhandled compare assign");    
  }
  
  @Override
  public Operation process(Decompiler d) {
    return new Operation(end - 1) {

      @Override
      public Statement process(Registers r, Block block) {
        return new RegisterSet(end - 1, target, branch.asExpression(r)).process(r, block);
      }
      
    };
  }

}
