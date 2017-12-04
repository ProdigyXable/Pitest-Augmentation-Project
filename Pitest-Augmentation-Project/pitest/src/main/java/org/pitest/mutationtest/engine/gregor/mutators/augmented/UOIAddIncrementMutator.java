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

public enum UOIAddIncrementMutator implements MethodMutatorFactory {

  UOI_ADD_INCREMENT_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new UOIAddIncrementMutatorMethodVisitor(this, context, methodVisitor);
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

// This operator works exclusively on function local variables, not object variables
class UOIAddIncrementMutatorMethodVisitor extends MethodVisitor {

  private final MethodMutatorFactory factory;
  private final MutationContext      context;

  UOIAddIncrementMutatorMethodVisitor(final MethodMutatorFactory factory,
      final MutationContext context, final MethodVisitor delegateMethodVisitor) {
    super(Opcodes.ASM6, delegateMethodVisitor);
    this.factory = factory;
    this.context = context;
  }
  
  @Override
  public void visitIincInsn(final int var, final int increment) {
      
      final MutationIdentifier newId = this.context.registerMutation(this.factory, "UOI Mutator: Added unary increment "
            + increment + " -> " + (increment + 1) + " to local variable");
      
    if (this.context.shouldMutate(newId)) {
    this.mv.visitIincInsn(var, increment + 1);
    } else {
       this.mv.visitIincInsn(var, increment);
    }
    
  }
  
 

}