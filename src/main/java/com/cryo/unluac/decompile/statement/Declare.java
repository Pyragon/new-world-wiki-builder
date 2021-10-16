package com.cryo.unluac.decompile.statement;

import java.util.List;

import com.cryo.unluac.decompile.Declaration;
import com.cryo.unluac.decompile.Decompiler;
import com.cryo.unluac.decompile.Output;

public class Declare extends Statement {

  private final List<Declaration> decls;
  
  public Declare(List<Declaration> decls) {
    this.decls = decls;
  }

  @Override
  public void print(Decompiler d, Output out) {
    out.print("local ");
    out.print(decls.get(0).name);
    for(int i = 1; i < decls.size(); i++) {
      out.print(", ");
      out.print(decls.get(i).name);
    }
  }
  
}
