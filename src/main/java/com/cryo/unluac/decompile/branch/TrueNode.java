package com.cryo.unluac.decompile.branch;

import com.cryo.unluac.decompile.Constant;
import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.expression.ConstantExpression;
import com.cryo.unluac.decompile.expression.Expression;
import com.cryo.unluac.parse.LBoolean;

public class TrueNode extends Branch {

  public final int register;
  public final boolean invert;
  
  public TrueNode(int register, boolean invert, int line, int begin, int end) {
    super(line, begin, end);
    this.register = register;
    this.invert = invert;
    this.setTarget = register;
    //isTest = true;
  }
  
  @Override
  public Branch invert() {
    return new TrueNode(register, !invert, line, end, begin);
  }
  
  @Override
  public int getRegister() {
    return register; 
  }
  
  @Override
  public Expression asExpression(Registers r) {
    return new ConstantExpression(new Constant(invert ? LBoolean.LTRUE : LBoolean.LFALSE), -1); 
  }
  
  @Override
  public void useExpression(Expression expression) {
    /* Do nothing */
  }
  
  @Override
  public String toString() {
    return "TrueNode[invert=" + invert + ";line=" + line + ";begin=" + begin + ";end=" + end + "]";
  }
  
}