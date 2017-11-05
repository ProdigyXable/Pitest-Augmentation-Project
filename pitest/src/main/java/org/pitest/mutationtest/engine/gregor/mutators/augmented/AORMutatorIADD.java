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
public enum AORMutatorIADD implements MethodMutatorFactory {

  AOR_MUTATOR_IADD;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new AORIADDMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
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

class AORIADDMutatorMethodVisitor extends AbstractInsnMutator {

  AORIADDMutatorMethodVisitor(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
    
  }

  private static final Map<Integer, ZeroOperandMutation> MUTATIONS_IADD = new HashMap<Integer, ZeroOperandMutation>();
  
  private static final String MESSAGE_A = "AOR Mutator: Replaced ";
  private static final String MESSAGE_B = " with ";
  
  static {

    MUTATIONS_IADD.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IADD, MESSAGE_A + '-' + MESSAGE_B + '+' + " (int)"));
    MUTATIONS_IADD.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IADD, MESSAGE_A + '*' + MESSAGE_B + '+' + " (int)"));
    MUTATIONS_IADD.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.IADD, MESSAGE_A + '/' + MESSAGE_B + '+' + " (int)"));
    MUTATIONS_IADD.put(Opcodes.IREM, new InsnSubstitution(Opcodes.IADD, MESSAGE_A + '%' + MESSAGE_B + '+' + " (int)"));
    
    /*
    
    MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LSUB, MESSAGE_A + '+' + MESSAGE_B + '-' + " (long)"));
    MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LMUL, MESSAGE_A + '+' + MESSAGE_B + '*' + " (long)"));
    MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LDIV, MESSAGE_A + '+' + MESSAGE_B + '/' + " (long)"));
    MUTATIONS.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '+' + MESSAGE_B + '%' + " (long)"));

    MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LADD, MESSAGE_A + '-' + MESSAGE_B + '+' + " (long)"));
    MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LMUL, MESSAGE_A + '-' + MESSAGE_B + '*' + " (long)"));
    MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LDIV, MESSAGE_A + '-' + MESSAGE_B + '/' + " (long)"));
    MUTATIONS.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '-' + MESSAGE_B + '%' + " (long)"));
    
    MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LADD, MESSAGE_A + '*' + MESSAGE_B + '+' + " (long)"));
    MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LSUB, MESSAGE_A + '*' + MESSAGE_B + '-' + " (long)"));
    MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LDIV, MESSAGE_A + '*' + MESSAGE_B + '/' + " (long)"));
    MUTATIONS.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '*' + MESSAGE_B + '%' + " (long)"));
    
    MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LADD, MESSAGE_A + '/' + MESSAGE_B + '+' + " (long)"));
    MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LSUB, MESSAGE_A + '/' + MESSAGE_B + '-' + " (long)"));
    MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LMUL, MESSAGE_A + '/' + MESSAGE_B + '*' + " (long)"));
    MUTATIONS.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '/' + MESSAGE_B + '%' + " (long)"));
    
    MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.IADD, MESSAGE_A + '%' + MESSAGE_B + '+' + " (long)"));
    MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.ISUB, MESSAGE_A + '%' + MESSAGE_B + '-' + " (long)"));
    MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.IMUL, MESSAGE_A + '%' + MESSAGE_B + '*' + " (long)"));
    MUTATIONS.put(Opcodes.LREM, new InsnSubstitution(Opcodes.IDIV, MESSAGE_A + '%' + MESSAGE_B + '/' + " (long)"));
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '+' + MESSAGE_B + '-' + " (float)"));
    MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '+' + MESSAGE_B + '*' + " (float)"));
    MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '+' + MESSAGE_B + '/' + " (float)"));
    MUTATIONS.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '+' + MESSAGE_B + '%' + " (float)"));

    MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '-' + MESSAGE_B + '+' + " (float)"));
    MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '-' + MESSAGE_B + '*' + " (float)"));
    MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '-' + MESSAGE_B + '/' + " (float)"));
    MUTATIONS.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '-' + MESSAGE_B + '%' + " (float)"));
    
    MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '*' + MESSAGE_B + '+' + " (float)"));
    MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '*' + MESSAGE_B + '-' + " (float)"));
    MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '*' + MESSAGE_B + '/' + " (float)"));
    MUTATIONS.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '*' + MESSAGE_B + '%' + " (float)"));
    
    MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '/' + MESSAGE_B + '+' + " (float)"));
    MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '/' + MESSAGE_B + '-' + " (float)"));
    MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '/' + MESSAGE_B + '*' + " (float)"));
    MUTATIONS.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FREM, MESSAGE_A + '/' + MESSAGE_B + '%' + " (float)"));
    
    MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '%' + MESSAGE_B + '+' + " (float)"));
    MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '%' + MESSAGE_B + '-' + " (float)"));
    MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '%' + MESSAGE_B + '*' + " (float)"));
    MUTATIONS.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '%' + MESSAGE_B + '/' + " (float)"));
    
    MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '+' + MESSAGE_B + '-' + " (double)"));
    MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '+' + MESSAGE_B + '*' + " (double)"));
    MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '+' + MESSAGE_B + '/' + " (double)"));
    MUTATIONS.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '+' + MESSAGE_B + '%' + " (double)"));

    MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '-' + MESSAGE_B + '+' + " (double)"));
    MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '-' + MESSAGE_B + '*' + " (double)"));
    MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '-' + MESSAGE_B + '/' + " (double)"));
    MUTATIONS.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '-' + MESSAGE_B + '%' + " (double)"));
    
    MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '*' + MESSAGE_B + '+' + " (double)"));
    MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '*' + MESSAGE_B + '-' + " (double)"));
    MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '*' + MESSAGE_B + '/' + " (double)"));
    MUTATIONS.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '*' + MESSAGE_B + '%' + " (double)"));
    
    MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '/' + MESSAGE_B + '+' + " (double)"));
    MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '/' + MESSAGE_B + '-' + " (double)"));
    MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '/' + MESSAGE_B + '*' + " (double)"));
    MUTATIONS.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '/' + MESSAGE_B + '%' + " (double)"));
    
    MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '%' + MESSAGE_B + '+' + " (double)"));
    MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '%' + MESSAGE_B + '-' + " (double)"));
    MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '%' + MESSAGE_B + '*' + " (double)"));
    MUTATIONS.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '%' + MESSAGE_B + '/' + " (double)"));
   
    */
  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
   
      return MUTATIONS_IADD;

  }

}
