package com.cryo.unluac.decompile.branch;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.expression.Expression;
import com.cryo.unluac.decompile.expression.UnaryExpression;

public class NotBranch extends Branch {

  private final Branch branch;
  
  public NotBranch(Branch branch) {
    super(branch.line, branch.begin, branch.end);
    this.branch = branch;
  }
  
  @Override
  public Branch invert() {
    return branch;
  }

  @Override
  public int getRegister() {
    return branch.getRegister();
  }
  
  @Override
  public Expression asExpression(Registers r) {
    return new UnaryExpression("not ", branch.asExpression(r), Expression.PRECEDENCE_UNARY);
  }
  
  @Override
  public void useExpression(Expression expression) {
    /* Do nothing */
  }
  
}
