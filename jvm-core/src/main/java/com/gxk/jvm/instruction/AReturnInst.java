package com.gxk.jvm.instruction;

import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.heap.KObject;

public class AReturnInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    KObject tmp = frame.popRef();
    frame.thread.popFrame();
    if (!frame.thread.empty()) {
      frame.thread.currentFrame().pushRef(tmp);
    }
//    System.out.println("do ret " + tmp);
  }

  @Override
  public String format() {
    return "areturn";
  }
}
