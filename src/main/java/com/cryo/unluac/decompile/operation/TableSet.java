package com.cryo.unluac.decompile.operation;

import com.cryo.unluac.decompile.Registers;
import com.cryo.unluac.decompile.block.Block;
import com.cryo.unluac.decompile.expression.Expression;
import com.cryo.unluac.decompile.expression.TableLiteral;
import com.cryo.unluac.decompile.statement.Assignment;
import com.cryo.unluac.decompile.statement.Statement;
import com.cryo.unluac.decompile.target.TableTarget;

public class TableSet extends Operation {
  
  private Expression table;
  private Expression index;
  private Expression value;
  private boolean isTable;
  private int timestamp;
  
  public TableSet(int line, Expression table, Expression index, Expression value, boolean isTable, int timestamp) {
    super(line);
    this.table = table;
    this.index = index;
    this.value = value;
    this.isTable = isTable;
    this.timestamp = timestamp;
  }

  @Override
  public Statement process(Registers r, Block block) {
    // .isTableLiteral() is sufficient when there is debugging info
    if(table.isTableLiteral() && (value.isMultiple() || table.isNewEntryAllowed())) {
      table.addEntry(new TableLiteral.Entry(index, value, !isTable, timestamp));
      return null;
    } else {
      return new Assignment(new TableTarget(table, index), value);
    }
  }

}
