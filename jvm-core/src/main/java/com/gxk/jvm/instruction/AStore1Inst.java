package com.gxk.jvm.instruction;

import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.heap.KObject;

public class AStore1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    KObject tmp = frame.popRef();
    frame.setRef(1, tmp);
  }

  @Override
  public String format() {
    return "astore_1";
  }
}
