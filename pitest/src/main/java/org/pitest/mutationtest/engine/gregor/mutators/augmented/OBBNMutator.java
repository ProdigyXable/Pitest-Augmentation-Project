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

public class OBBNMutator implements MethodMutatorFactory {

    public enum MutantType {

        AND, OR, XOR
    }

    private final MutantType mutatorType;

    public OBBNMutator(MutantType mt) {
        this.mutatorType = mt;
    }

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        if (this.mutatorType == OBBNMutator.MutantType.AND) {
            return new OBBNANDMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == OBBNMutator.MutantType.OR) {
            return new OBBNORMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == OBBNMutator.MutantType.XOR) {
            return new OBBNXORMethodVisitor(this, methodInfo, context, methodVisitor);
        } else {
            return null;
        }
    }

    @Override
    public String getGloballyUniqueId() {
        return this.getClass().getName() + "_" + this.mutatorType.name();
    }

    @Override
    public String getName() {
        return "OBBN Mutator - " + this.mutatorType.name();
    }

}

class OBBNANDMethodVisitor extends AbstractInsnMutator {

    OBBNANDMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);
    }

    private static final String MESSAGE_A = "Replaced ";
    private static final String MESSAGE_B = " with ";
    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_AND = new HashMap<Integer, ZeroOperandMutation>();

    static {
        MUTATIONS_AND.put(Opcodes.IAND, new InsnSubstitution(Opcodes.IOR, MESSAGE_A + " & " + MESSAGE_B + " | (int)"));
        MUTATIONS_AND.put(Opcodes.IXOR, new InsnSubstitution(Opcodes.IOR, MESSAGE_A + " & " + MESSAGE_B + " | (int)"));

        MUTATIONS_AND.put(Opcodes.LAND, new InsnSubstitution(Opcodes.LOR, MESSAGE_A + " ^ " + MESSAGE_B + " | (int)"));
        MUTATIONS_AND.put(Opcodes.LXOR, new InsnSubstitution(Opcodes.LOR, MESSAGE_A + " ^ " + MESSAGE_B + " | (int)"));
    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {
        return MUTATIONS_AND;
    }

}

class OBBNORMethodVisitor extends AbstractInsnMutator {

    OBBNORMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);
    }

    private static final String MESSAGE_A = "Replaced ";
    private static final String MESSAGE_B = " with ";
    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_OR = new HashMap<Integer, ZeroOperandMutation>();

    static {
        MUTATIONS_OR.put(Opcodes.IOR, new InsnSubstitution(Opcodes.IAND, MESSAGE_A + " | " + MESSAGE_B + " & (int)"));
        MUTATIONS_OR.put(Opcodes.IXOR, new InsnSubstitution(Opcodes.IAND, MESSAGE_A + " ^ " + MESSAGE_B + " & (int)"));

        MUTATIONS_OR.put(Opcodes.LOR, new InsnSubstitution(Opcodes.LAND, MESSAGE_A + " | " + MESSAGE_B + " & (long"));
        MUTATIONS_OR.put(Opcodes.LXOR, new InsnSubstitution(Opcodes.LAND, MESSAGE_A + " ^ " + MESSAGE_B + " & (long"));
    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {
        return MUTATIONS_OR;
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
