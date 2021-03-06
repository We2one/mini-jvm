package com.gxk.jvm.rtda;

import com.gxk.jvm.rtda.heap.KObject;

public class OperandStack {

  private final Stack<Slot> slots;

  public OperandStack(int size) {
    slots = new Stack<>(size);
  }

  public void pushInt(int val) {
    this.slots.push(Slot.val(val));
  }

  public Integer popInt() {
    return this.slots.pop().val;
  }

  public void pushLong(long val) {
    int low = (int) (val & 0x000000ffffffffL); //低32位
    int high = (int) (val >> 32); //高32位
    this.slots.push(Slot.val(low));
    this.slots.push(Slot.val(high));
  }

  public long popLong() {
    int high = this.slots.pop().val;
    int low = this.slots.pop().val;

    long l1 = (high & 0x000000ffffffffL) << 32;
    long l2 = low & 0x00000000ffffffffL;
    return l1 | l2;
  }

  public void pushFloat(float val) {
    int tmp = Float.floatToIntBits(val);
    this.slots.push(Slot.val(tmp));
  }

  public float popFloat() {
    int tmp = this.slots.pop().val;
    return Float.intBitsToFloat(tmp);
  }

  public void pushDouble(double val) {
    long tmp = Double.doubleToLongBits(val);

    int low = (int) (tmp & 0x000000ffffffffL); //低32位
    int high = (int) (tmp >> 32); //高32位
    this.slots.push(Slot.val(low));
    this.slots.push(Slot.val(high));
  }

  public double popDouble() {
    long tmp = this.popLong();
    return Double.longBitsToDouble(tmp);
  }

  public void pushRef(KObject val) {
    this.slots.push(Slot.ref(val));
  }

  public KObject popRef() {
    return this.slots.pop().ref;
  }

  public Slot popSlot() {
    return this.slots.pop();
  }

  public void pushSlot(Slot val) {
    this.slots.push(val);
  }

  public Stack<Slot> getSlots() {
    return this.slots;
  }

  public String debug(String space) {
    StringBuilder sb = new StringBuilder();
    sb.append(space).append(String.format("OperandStack: %d", this.slots.maxSize)).append("\n");
    for (int i = 0; i < this.slots.size(); i++) {
      Slot slot = this.slots.get(i);
      if (slot == null) {
        sb.append(space).append(String.format("%d | null      | null", i)).append("\n");
        continue;
      }
      if (slot.ref != null) {
        sb.append(space).append(String.format("%d | ref       | %s", i, slot.ref)).append("\n");
        continue;
      }
      sb.append(space).append(String.format("%d | primitive | %s ", i, slot.val)).append("\n");
    }
    return sb.append("\n").toString();
  }
}
