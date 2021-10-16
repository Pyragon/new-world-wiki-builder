package com.cryo.unluac.decompile.branch;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.expression.BinaryExpression;
import com.cryo.unluac.decompile.expression.Expression;

public class OrBranch extends Branch {

  private final Branch left;
  private final Branch right;
  
  public OrBranch(Branch left, Branch right) {
    super(right.line, right.begin, right.end);
    this.left = left;
    this.right = right;
  }
  
  @Override
  public Branch invert() {
    return new AndBranch(left.invert(), right.invert());
  }
  
  /*
  @Override
  public Branch invert() {
    return new NotBranch(new AndBranch(left.invert(), right.invert()));
  }
  */

  @Override
  public int getRegister() {
    int rleft = left.getRegister();
    int rright = right.getRegister();
    return rleft == rright ? rleft : -1;
  }
  
  @Override
  public Expression asExpression(Registers r) {
    return new BinaryExpression("or", left.asExpression(r), right.asExpression(r), Expression.PRECEDENCE_OR, Expression.ASSOCIATIVITY_NONE);
  }
  
  @Override
  public void useExpression(Expression expression) {
    left.useExpression(expression);
    right.useExpression(expression);
  }

  
}
