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

public enum OBBNXORMutator implements MethodMutatorFactory {

  OBBN_XOR_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new OBBNXORMethodVisitor(this, methodInfo, context, methodVisitor);
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

class OBBNXORMethodVisitor extends AbstractInsnMutator {

  OBBNXORMethodVisitor(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
  }

  private static final String MESSAGE_A = "Replaced ";
  private static final String MESSASGE_B = " with ";
  private static final Map<Integer, ZeroOperandMutation> MUTATIONS_XOR = new HashMap<Integer, ZeroOperandMutation>();

  static {
    MUTATIONS_XOR.put(Opcodes.IAND, new InsnSubstitution(Opcodes.IOR, MESSAGE_A + " & " + MESSASGE_B + " | (int)"));
    MUTATIONS_XOR.put(Opcodes.IXOR, new InsnSubstitution(Opcodes.IOR, MESSAGE_A + " & " + MESSASGE_B + " | (int)"));
    
    MUTATIONS_XOR.put(Opcodes.LAND, new InsnSubstitution(Opcodes.LOR, MESSAGE_A + " ^ " + MESSASGE_B + " | (int)"));
    MUTATIONS_XOR.put(Opcodes.LXOR, new InsnSubstitution(Opcodes.LOR, MESSAGE_A + " ^ " + MESSASGE_B + " | (int)"));
  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
    return MUTATIONS_XOR;
  }

}