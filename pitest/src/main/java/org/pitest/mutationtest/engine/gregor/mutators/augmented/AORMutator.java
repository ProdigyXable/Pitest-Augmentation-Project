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
public class AORMutator implements MethodMutatorFactory {

    public enum MutantType {

        IADD, ISUB, IMUL, IDIV, IREM,
        DADD, DSUB, DMUL, DDIV, DREM,
        FADD, FSUB, FMUL, FDIV, FREM,
        LADD, LSUB, LMUL, LDIV, LREM,
    }

    private final MutantType mutatorType;

    public AORMutator(MutantType mt) {
        this.mutatorType = mt;
    }

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        if (this.mutatorType == AORMutator.MutantType.IADD) {
            return new AORIADDMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.DADD) {
            return new AORDADDMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.FADD) {
            return new AORFADDMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.LADD) {
            return new AORLADDMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.ISUB) {
            return new AORISUBMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.DSUB) {
            return new AORDSUBMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.FSUB) {
            return new AORFSUBMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.LSUB) {
            return new AORLSUBMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.IMUL) {
            return new AORIMULMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.DMUL) {
            return new AORDMULMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.FMUL) {
            return new AORFMULMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.LMUL) {
            return new AORLMULMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.IDIV) {
            return new AORIDIVMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.DDIV) {
            return new AORDDIVMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.FDIV) {
            return new AORFDIVMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.LDIV) {
            return new AORLDIVMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.IREM) {
            return new AORIREMMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.DREM) {
            return new AORDREMMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.FREM) {
            return new AORFREMMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (this.mutatorType == AORMutator.MutantType.LREM) {
            return new AORLREMMutatorMethodVisitor(this, methodInfo, context, methodVisitor);
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
        return "AOD Mutator - " + this.mutatorType.name();
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

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_IADD;

    }

}

class AORISUBMutatorMethodVisitor extends AbstractInsnMutator {

    AORISUBMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_ISUB = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_ISUB.put(Opcodes.IADD, new InsnSubstitution(Opcodes.ISUB, MESSAGE_A + '+' + MESSAGE_B + '-' + " (int)"));
        MUTATIONS_ISUB.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.ISUB, MESSAGE_A + '*' + MESSAGE_B + '-' + " (int)"));
        MUTATIONS_ISUB.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.ISUB, MESSAGE_A + '/' + MESSAGE_B + '-' + " (int)"));
        MUTATIONS_ISUB.put(Opcodes.IREM, new InsnSubstitution(Opcodes.ISUB, MESSAGE_A + '%' + MESSAGE_B + '-' + " (int)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_ISUB;

    }

}

class AORIMULMutatorMethodVisitor extends AbstractInsnMutator {

    AORIMULMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_IMUL = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_IMUL.put(Opcodes.IADD, new InsnSubstitution(Opcodes.IMUL, MESSAGE_A + '+' + MESSAGE_B + '*' + " (int)"));
        MUTATIONS_IMUL.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IMUL, MESSAGE_A + '-' + MESSAGE_B + '*' + " (int)"));
        MUTATIONS_IMUL.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.IMUL, MESSAGE_A + '/' + MESSAGE_B + '*' + " (int)"));
        MUTATIONS_IMUL.put(Opcodes.IREM, new InsnSubstitution(Opcodes.IMUL, MESSAGE_A + '%' + MESSAGE_B + '*' + " (int)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_IMUL;

    }

}

class AORIDIVMutatorMethodVisitor extends AbstractInsnMutator {

    AORIDIVMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_IDIV = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_IDIV.put(Opcodes.IADD, new InsnSubstitution(Opcodes.IDIV, MESSAGE_A + '+' + MESSAGE_B + '/' + " (int)"));
        MUTATIONS_IDIV.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IDIV, MESSAGE_A + '-' + MESSAGE_B + '/' + " (int)"));
        MUTATIONS_IDIV.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IDIV, MESSAGE_A + '*' + MESSAGE_B + '/' + " (int)"));
        MUTATIONS_IDIV.put(Opcodes.IREM, new InsnSubstitution(Opcodes.IDIV, MESSAGE_A + '%' + MESSAGE_B + '/' + " (int)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_IDIV;

    }

}

class AORIREMMutatorMethodVisitor extends AbstractInsnMutator {

    AORIREMMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_IREM = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_IREM.put(Opcodes.IADD, new InsnSubstitution(Opcodes.IREM, MESSAGE_A + '+' + MESSAGE_B + '%' + " (int)"));
        MUTATIONS_IREM.put(Opcodes.ISUB, new InsnSubstitution(Opcodes.IREM, MESSAGE_A + '-' + MESSAGE_B + '%' + " (int)"));
        MUTATIONS_IREM.put(Opcodes.IMUL, new InsnSubstitution(Opcodes.IREM, MESSAGE_A + '*' + MESSAGE_B + '%' + " (int)"));
        MUTATIONS_IREM.put(Opcodes.IDIV, new InsnSubstitution(Opcodes.IREM, MESSAGE_A + '/' + MESSAGE_B + '%' + " (int)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_IREM;

    }

}

class AORDADDMutatorMethodVisitor extends AbstractInsnMutator {

    AORDADDMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_DADD = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_DADD.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '-' + MESSAGE_B + '+' + " (double)"));
        MUTATIONS_DADD.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '*' + MESSAGE_B + '+' + " (double)"));
        MUTATIONS_DADD.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '/' + MESSAGE_B + '+' + " (double)"));
        MUTATIONS_DADD.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DADD, MESSAGE_A + '%' + MESSAGE_B + '+' + " (double)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_DADD;

    }

}

class AORDSUBMutatorMethodVisitor extends AbstractInsnMutator {

    AORDSUBMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_DSUB = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_DSUB.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '+' + MESSAGE_B + '-' + " (double)"));
        MUTATIONS_DSUB.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '*' + MESSAGE_B + '-' + " (double)"));
        MUTATIONS_DSUB.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '/' + MESSAGE_B + '-' + " (double)"));
        MUTATIONS_DSUB.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DSUB, MESSAGE_A + '%' + MESSAGE_B + '-' + " (double)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_DSUB;

    }

}

class AORDMULMutatorMethodVisitor extends AbstractInsnMutator {

    AORDMULMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_DMUL = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_DMUL.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '+' + MESSAGE_B + '*' + " (double)"));
        MUTATIONS_DMUL.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '-' + MESSAGE_B + '*' + " (double)"));
        MUTATIONS_DMUL.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '/' + MESSAGE_B + '*' + " (double)"));
        MUTATIONS_DMUL.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DMUL, MESSAGE_A + '%' + MESSAGE_B + '*' + " (double)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_DMUL;

    }

}

class AORDREMMutatorMethodVisitor extends AbstractInsnMutator {

    AORDREMMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_DREM = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_DREM.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '+' + MESSAGE_B + '%' + " (double)"));
        MUTATIONS_DREM.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '-' + MESSAGE_B + '%' + " (double)"));
        MUTATIONS_DREM.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '*' + MESSAGE_B + '%' + " (double)"));
        MUTATIONS_DREM.put(Opcodes.DDIV, new InsnSubstitution(Opcodes.DREM, MESSAGE_A + '/' + MESSAGE_B + '%' + " (double)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_DREM;

    }

}

class AORDDIVMutatorMethodVisitor extends AbstractInsnMutator {

    AORDDIVMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_DDIV = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_DDIV.put(Opcodes.DADD, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '+' + MESSAGE_B + '/' + " (double)"));
        MUTATIONS_DDIV.put(Opcodes.DSUB, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '-' + MESSAGE_B + '/' + " (double)"));
        MUTATIONS_DDIV.put(Opcodes.DMUL, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '*' + MESSAGE_B + '/' + " (double)"));
        MUTATIONS_DDIV.put(Opcodes.DREM, new InsnSubstitution(Opcodes.DDIV, MESSAGE_A + '%' + MESSAGE_B + '/' + " (double)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_DDIV;

    }
}

class AORFADDMutatorMethodVisitor extends AbstractInsnMutator {

    AORFADDMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_FADD = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_FADD.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '-' + MESSAGE_B + '+' + " (float)"));
        MUTATIONS_FADD.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '*' + MESSAGE_B + '+' + " (float)"));
        MUTATIONS_FADD.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '/' + MESSAGE_B + '+' + " (float)"));
        MUTATIONS_FADD.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FADD, MESSAGE_A + '%' + MESSAGE_B + '+' + " (float)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_FADD;

    }

}

class AORFSUBMutatorMethodVisitor extends AbstractInsnMutator {

    AORFSUBMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_FSUB = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_FSUB.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '+' + MESSAGE_B + '-' + " (float)"));
        MUTATIONS_FSUB.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '*' + MESSAGE_B + '-' + " (float)"));
        MUTATIONS_FSUB.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '/' + MESSAGE_B + '-' + " (float)"));
        MUTATIONS_FSUB.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FSUB, MESSAGE_A + '%' + MESSAGE_B + '-' + " (float)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_FSUB;

    }

}

class AORFMULMutatorMethodVisitor extends AbstractInsnMutator {

    AORFMULMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_FMUL = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_FMUL.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '+' + MESSAGE_B + '*' + " (float)"));
        MUTATIONS_FMUL.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '-' + MESSAGE_B + '*' + " (float)"));
        MUTATIONS_FMUL.put(Opcodes.FDIV, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '/' + MESSAGE_B + '*' + " (float)"));
        MUTATIONS_FMUL.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FMUL, MESSAGE_A + '%' + MESSAGE_B + '*' + " (float)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_FMUL;

    }

}

class AORFDIVMutatorMethodVisitor extends AbstractInsnMutator {

    AORFDIVMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_FDIV = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_FDIV.put(Opcodes.FADD, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '+' + MESSAGE_B + '/' + " (float)"));
        MUTATIONS_FDIV.put(Opcodes.FSUB, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '-' + MESSAGE_B + '/' + " (float)"));
        MUTATIONS_FDIV.put(Opcodes.FMUL, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '*' + MESSAGE_B + '/' + " (float)"));
        MUTATIONS_FDIV.put(Opcodes.FREM, new InsnSubstitution(Opcodes.FDIV, MESSAGE_A + '%' + MESSAGE_B + '/' + " (float)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_FDIV;

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

class AORLADDMutatorMethodVisitor extends AbstractInsnMutator {

    AORLADDMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_LADD = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_LADD.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LADD, MESSAGE_A + '-' + MESSAGE_B + '+' + " (long)"));
        MUTATIONS_LADD.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LADD, MESSAGE_A + '*' + MESSAGE_B + '+' + " (long)"));
        MUTATIONS_LADD.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LADD, MESSAGE_A + '/' + MESSAGE_B + '+' + " (long)"));
        MUTATIONS_LADD.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LADD, MESSAGE_A + '%' + MESSAGE_B + '+' + " (long)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_LADD;

    }

}

class AORLSUBMutatorMethodVisitor extends AbstractInsnMutator {

    AORLSUBMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_LSUB = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_LSUB.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LSUB, MESSAGE_A + '+' + MESSAGE_B + '-' + " (long)"));
        MUTATIONS_LSUB.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LSUB, MESSAGE_A + '*' + MESSAGE_B + '-' + " (long)"));
        MUTATIONS_LSUB.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LSUB, MESSAGE_A + '/' + MESSAGE_B + '-' + " (long)"));
        MUTATIONS_LSUB.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LSUB, MESSAGE_A + '%' + MESSAGE_B + '-' + " (long)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_LSUB;

    }

}

class AORLMULMutatorMethodVisitor extends AbstractInsnMutator {

    AORLMULMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_LMUL = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_LMUL.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LMUL, MESSAGE_A + '+' + MESSAGE_B + '*' + " (long)"));
        MUTATIONS_LMUL.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LMUL, MESSAGE_A + '-' + MESSAGE_B + '*' + " (long)"));
        MUTATIONS_LMUL.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LMUL, MESSAGE_A + '/' + MESSAGE_B + '*' + " (long)"));
        MUTATIONS_LMUL.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LMUL, MESSAGE_A + '%' + MESSAGE_B + '*' + " (long)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_LMUL;

    }

}

class AORLDIVMutatorMethodVisitor extends AbstractInsnMutator {

    AORLDIVMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_LDIV = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_LDIV.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LDIV, MESSAGE_A + '+' + MESSAGE_B + '/' + " (double)"));
        MUTATIONS_LDIV.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LDIV, MESSAGE_A + '-' + MESSAGE_B + '/' + " (double)"));
        MUTATIONS_LDIV.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LDIV, MESSAGE_A + '*' + MESSAGE_B + '/' + " (double)"));
        MUTATIONS_LDIV.put(Opcodes.LREM, new InsnSubstitution(Opcodes.LDIV, MESSAGE_A + '%' + MESSAGE_B + '/' + " (double)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_LDIV;

    }

}

class AORLREMMutatorMethodVisitor extends AbstractInsnMutator {

    AORLREMMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context,
            final MethodVisitor writer) {
        super(factory, methodInfo, context, writer);

    }

    private static final Map<Integer, ZeroOperandMutation> MUTATIONS_LREM = new HashMap<Integer, ZeroOperandMutation>();

    private static final String MESSAGE_A = "AOR Mutator: Replaced ";
    private static final String MESSAGE_B = " with ";

    static {

        MUTATIONS_LREM.put(Opcodes.LADD, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '+' + MESSAGE_B + '%' + " (long)"));
        MUTATIONS_LREM.put(Opcodes.LSUB, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '-' + MESSAGE_B + '%' + " (long)"));
        MUTATIONS_LREM.put(Opcodes.LMUL, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '*' + MESSAGE_B + '%' + " (long)"));
        MUTATIONS_LREM.put(Opcodes.LDIV, new InsnSubstitution(Opcodes.LREM, MESSAGE_A + '/' + MESSAGE_B + '%' + " (long)"));

    }

    @Override
    protected Map<Integer, ZeroOperandMutation> getMutations() {

        return MUTATIONS_LREM;

    }

}
