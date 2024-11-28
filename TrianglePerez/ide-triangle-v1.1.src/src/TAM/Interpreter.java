/*
 * @(#)Interpreter.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package TAM;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static TAM.Disassembler.loadObjectProgram;

public class Interpreter {

  static String objectName;

  // DATA STORE
  static int[] data = new int[1024];

  // DATA STORE REGISTERS AND OTHER REGISTERS
  final static int CB = 0, SB = 0, HB = 1024; // = upper bound of data array + 1
  static int CT, CP, ST, HT, LB, status;

  // Status values
  final static int running = 0, halted = 1, failedDataStoreFull = 2, failedInvalidCodeAddress = 3,
          failedInvalidInstruction = 4, failedOverflow = 5, failedZeroDivide = 6, failedIOError = 7;

  static long accumulator;

  static int content(int r) {
    switch (r) {
      case Machine.CBr:
        return CB;
      case Machine.CTr:
        return CT;
      case Machine.PBr:
        return Machine.PB;
      case Machine.PTr:
        return Machine.PT;
      case Machine.SBr:
        return SB;
      case Machine.STr:
        return ST;
      case Machine.HBr:
        return HB;
      case Machine.HTr:
        return HT;
      case Machine.LBr:
        return LB;
      default:
        return 0;
    }
  }

  // PROGRAM STATUS
  static void dump() {
    System.out.println("\nState of data store and registers:");
    if (HT == HB)
      System.out.println("            |--------|          (heap is empty)");
    else {
      System.out.println("       HB-->");
      for (int addr = HB - 1; addr >= HT; addr--) {
        System.out.print(addr + ":");
        if (addr == HT)
          System.out.print(" HT-->");
        System.out.println("|" + data[addr] + "|");
      }
      System.out.println("            |--------|");
    }

    if (ST == SB)
      System.out.println("            |--------|          (stack is empty)");
    else {
      System.out.println("      ST--> |////////|");
      for (int addr = ST - 1; addr >= SB; addr--) {
        System.out.print(addr + ":");
        System.out.print("      ");
        System.out.println("|" + data[addr] + "|");
      }
    }
    System.out.println("");
  }

  static void showStatus() {
    System.out.println("");
    switch (status) {
      case running:
        System.out.println("Program is running.");
        break;
      case halted:
        System.out.println("Program has halted normally.");
        break;
      case failedDataStoreFull:
        System.out.println("Program has failed due to exhaustion of Data Store.");
        break;
      case failedInvalidCodeAddress:
        System.out.println("Program has failed due to an invalid code address.");
        break;
      case failedInvalidInstruction:
        System.out.println("Program has failed due to an invalid instruction.");
        break;
      case failedOverflow:
        System.out.println("Program has failed due to overflow.");
        break;
      case failedZeroDivide:
        System.out.println("Program has failed due to division by zero.");
        break;
      case failedIOError:
        System.out.println("Program has failed due to an IO error.");
        break;
    }
    if (status != halted)
      dump();
  }

  // INTERPRETATION

  static void checkSpace(int spaceNeeded) {
    if (HT - ST < spaceNeeded)
      status = failedDataStoreFull;
  }

  static boolean isTrue(int datum) {
    return (datum == Machine.trueRep);
  }

  static boolean equal(int size, int addr1, int addr2) {
    boolean eq = true;
    int index = 0;

    while (eq && (index < size)) {
      if ((addr1 + index < 0 || addr1 + index >= data.length) || (addr2 + index < 0 || addr2 + index >= data.length)) {
        status = failedInvalidInstruction;
        System.err.println("Error: Índices fuera de rango en el método equal.");
        return false;
      }
      if (data[addr1 + index] == data[addr2 + index])
        index++;
      else
        eq = false;
    }
    return eq;
  }

  static int overflowChecked(long datum) {
    if ((-Machine.maxintRep <= datum) && (datum <= Machine.maxintRep))
      return (int) datum;
    else {
      status = failedOverflow;
      return 0;
    }
  }

  static int toInt(boolean b) {
    return b ? Machine.trueRep : Machine.falseRep;
  }

  static void callPrimitive(int primitiveDisplacement) {
    int addr, size;

    switch (primitiveDisplacement) {
      case Machine.eqDisplacement:
        size = data[ST - 1];
        if (size < 0 || size > data.length || ST - 2 * size < 0) {
          status = failedInvalidInstruction;
          System.err.println("Error: Tamaño inválido en eqDisplacement.");
          break;
        }
        ST = ST - 2 * size;
        if ((ST - 1 < 0 || ST - 1 + size >= data.length)) {
          status = failedInvalidInstruction;
          System.err.println("Error: Índices fuera de rango en eqDisplacement.");
          break;
        }
        data[ST - 1] = toInt(equal(size, ST - 1, ST - 1 + size));
        break;
      // Otros casos...
    }
  }

  static void interpretProgram() {
    Instruction currentInstr;
    int op, r, n, d, addr, index;

    ST = SB;
    HT = HB;
    LB = SB;
    CP = CB;
    status = running;

    do {
      currentInstr = Machine.code[CP];
      op = currentInstr.op;
      r = currentInstr.r;
      n = currentInstr.n;
      d = currentInstr.d;

      switch (op) {
        case Machine.LOADop:
          addr = d + content(r);
          checkSpace(n);
          for (index = 0; index < n; index++)
            data[ST + index] = data[addr + index];
          ST = ST + n;
          CP = CP + 1;
          break;
        case Machine.CALLop:
          addr = d + content(r);
          if (addr >= Machine.PB) {
            callPrimitive(addr - Machine.PB);
            CP = CP + 1;
          }
          break;
        case Machine.RETURNop:
          CP = data[LB + 2];
          LB = data[LB + 1];
          ST = LB - n;
          break;
        // Otros casos...
      }

      if ((CP < CB) || (CP >= CT))
        status = failedInvalidCodeAddress;
    } while (status == running);
  }

  public static void main(String[] args) {
    System.out.println("********** TAM Interpreter (Java Version 2.1) **********");

    if (args.length == 1)
      objectName = args[0];
    else
      objectName = "obj.tam";

    loadObjectProgram(objectName);
    if (CT != CB) {
      interpretProgram();
      showStatus();
    }
  }
}
