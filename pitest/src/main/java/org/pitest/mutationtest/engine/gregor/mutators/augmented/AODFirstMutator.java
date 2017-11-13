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

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractInsnMutator;
import org.pitest.mutationtest.engine.gregor.InsnSubstitution;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;
import org.pitest.mutationtest.engine.gregor.ZeroOperandMutation;

public enum AODFirstMutator implements MethodMutatorFactory {

  AOD_FIRST;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new   AODFirstMethodVisitor(this,
      methodInfo,context, methodVisitor);
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

class AODFirstMethodVisitor extends AbstractInsnMutator {

  AODFirstMethodVisitor(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
  }
  
  private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<Integer, ZeroOperandMutation>();
  
  static
  {
      MUTATIONS.put(Opcodes.IADD, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from an addition formula (int)"));
      MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from an addition formula (double)"));
      MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from an addition formula (float)"));
      MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from an addition formula (long)"));
      
      MUTATIONS.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a subtraction formula (int)"));
      MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a subtraction formula (double)"));
      MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a subtraction formula (float)"));
      MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a subtraction formula (long)"));
      
      MUTATIONS.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a multiplication formula (int)"));
      MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a multiplication formula (double)"));
      MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a multiplication formula (float)"));
      MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a multiplication formula (long)"));
      
      MUTATIONS.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a division formula (int)"));
      MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a division formula (double)"));
      MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a division formula (float)"));
      MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a division formula (long)"));
      
      MUTATIONS.put(Opcodes.IREM, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a modulus formula (int)"));
      MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a modulus formula (double)"));
      MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.POP, "AOD Mutator: Removed the second operator from a modulus formula (float)"));
      MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.POP2, "AOD Mutator: Removed the second operator from a modulus formula (long)"));
  }
  
    @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
    return MUTATIONS;
  } 

}