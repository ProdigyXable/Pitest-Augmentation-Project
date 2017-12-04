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

/*
* @author Samuel Benton
*
*/
public enum AORMutatorFREM implements MethodMutatorFactory {

  AOR_MUTATOR_FREM;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new AORFREMMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
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

class AORFREMMutatorMethodVisitor extends AbstractInsnMutator {

  AORFREMMutatorMethodVisitor(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
    
  }

  private static final Map<Integer, ZeroOperandMutation> MUTATIONS_FREM = new HashMap<Integer, ZeroOperandMutation>();
  
  private static final String MESSAGE_A = "AOR Mutator: Replaced ";
  private static final String MESSAGE_B = " with ";
  
  static {
      
    MUTATIONS_FREM.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '+' + MESSAGE_B + '%' + " (float)"));
    MUTATIONS_FREM.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '-' + MESSAGE_B + '%' + " (float)"));
    MUTATIONS_FREM.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '*' + MESSAGE_B + '%' + " (float)"));
    MUTATIONS_FREM.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '/' + MESSAGE_B + '%' + " (float)"));
    
  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
   
      return MUTATIONS_FREM;

  }

}
