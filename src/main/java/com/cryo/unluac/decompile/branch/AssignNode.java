package com.cryo.unluac.decompile.branch;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.expression.Expression;

public class AssignNode extends Branch {

  private Expression expression;
  
  public AssignNode(int line, int begin, int end) {
    super(line, begin, end);
  }
  
  @Override
  public Branch invert() {
    throw new IllegalStateException();
  }

  @Override
  public int getRegister() {
    throw new IllegalStateException();
  }
  
  @Override
  public Expression asExpression(Registers r) {
    return expression;
  }
  
  @Override
  public void useExpression(Expression expression) {
    this.expression = expression;
  }
  
}
