/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.mutators.augmented;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum AODLastMutator implements MethodMutatorFactory {

  AOD_LAST;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new AODLastMethodVisitor(this, context, methodVisitor);
  }

  @Override
  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  @Override
  public String getName() {
    return name();
  }
}

class AODLastMethodVisitor extends MethodVisitor {

  private final MethodMutatorFactory factory;
  private final MutationContext      context;
    
  AODLastMethodVisitor(final MethodMutatorFactory factory, final MutationContext context, final MethodVisitor methodVisitor) {
      super(Opcodes.ASM6, methodVisitor);
      this.factory = factory;
      this.context = context;
    
  }
  
  @Override
  public void visitInsn(int opcode) {
      if ((opcode == Opcodes.IADD) || (opcode == Opcodes.FADD) ) {
          replaceSmallAddOperand(opcode);
      }  else if ((opcode == Opcodes.ISUB) || (opcode == Opcodes.FSUB)) {
          replaceSmallSubOperand(opcode);
       } else if ((opcode == Opcodes.IMUL) || (opcode == Opcodes.FMUL)) {
          replaceSmallMulOperand(opcode);
      } else if ((opcode == Opcodes.IDIV) || (opcode == Opcodes.FDIV)) {
          replaceSmallDivOperand(opcode);
      } else if ((opcode == Opcodes.IREM) || (opcode == Opcodes.DREM) || (opcode == Opcodes.FREM)) {
          replaceSmallRemOperand(opcode);    
      }  else if ((opcode == Opcodes.LADD) || (opcode == Opcodes.LSUB) || (opcode == Opcodes.LMUL) || (opcode == Opcodes.LDIV) /* || (opcode == Opcodes.LREM) */ ) {
          replaceLongOperand(opcode);
      }  else if ((opcode == Opcodes.DADD) || (opcode == Opcodes.DSUB) || (opcode == Opcodes.DMUL) || (opcode == Opcodes.DDIV) /* || (opcode == Opcodes.DREM) */ ) {
          replaceDoubleOperand(opcode);
      } else {
          super.visitInsn(opcode);
      }
  
  }

  private void replaceSmallAddOperand(int opcode) {
      final MutationIdentifier muID = this.context.registerMutation(factory, "AOD_Mutator: removed the first operand of addition formula");
      
      if (this.context.shouldMutate(muID)) {
          removeSmallFirstOperand();
      } else {
        super.visitInsn(opcode);
      }
  }
  
  private void replaceSmallSubOperand(int opcode) {
      final MutationIdentifier muID = this.context.registerMutation(factory, "AOD_Mutator: removed the first operand of subtraction formula");
      
      if (this.context.shouldMutate(muID)) {
          removeSmallFirstOperand();
      } else {
        super.visitInsn(opcode);
      }
  }
  
  private void replaceSmallMulOperand(int opcode) {
      final MutationIdentifier muID = this.context.registerMutation(factory, "AOD_Mutator: removed the first operand of multiplication formula");
      
      if (this.context.shouldMutate(muID)) {
          removeSmallFirstOperand();
      } else {
        super.visitInsn(opcode);
      }
  }
  
  private void replaceSmallDivOperand(int opcode) {
      final MutationIdentifier muID = this.context.registerMutation(factory, "AOD_Mutator: removed the first operand of division formula");
      
      if (this.context.shouldMutate(muID)) {
          removeSmallFirstOperand();
      } else {
        super.visitInsn(opcode);
      }
  }
  
  private void replaceSmallRemOperand(int opcode) {
      final MutationIdentifier muID = this.context.registerMutation(factory, "AOD_Mutator: removed the first operand of modulus formula");
      
      if (this.context.shouldMutate(muID)) {
          removeSmallFirstOperand();
      } else {
        super.visitInsn(opcode);
      }
  }
  
  private void replaceLongOperand(int opcode) {
      final MutationIdentifier muID = this.context.registerMutation(factory, "AOD_Mutator: removed the first operand of a formula involving longs");
      
      if (this.context.shouldMutate(muID)) {
          removeLargeFirstOperand();
      } else {
        super.visitInsn(opcode);
      }
  }
  private void replaceDoubleOperand(int opcode) {
      final MutationIdentifier muID = this.context.registerMutation(factory, "AOD_Mutator: removed the first operand of a formula involving doubless");
      
      if (this.context.shouldMutate(muID)) {
          removeLargeFirstOperand();
      } else {
        super.visitInsn(opcode);
      }
  }
  
  private void removeSmallFirstOperand() {
      super.visitInsn(Opcodes.SWAP);
      
      super.visitInsn(Opcodes.POP);
  }
  
  private void removeLargeFirstOperand() {
      super.visitInsn(Opcodes.DUP2_X2);
      super.visitInsn(Opcodes.POP2);
      
      super.visitInsn(Opcodes.POP2);
  }
}