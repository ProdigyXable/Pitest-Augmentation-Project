/*
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
import org.pitest.mutationtest.engine.gregor.AbstractJumpMutator;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public class RORMutator implements MethodMutatorFactory {

    public enum MutantType {

        IFEQ, IFGE, IFGT, IFLE, IFLT, IFNE,
    }

    private final MutantType mutatorType;

    public RORMutator(MutantType mt) {
        this.mutatorType = mt;
    }

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        if (this.mutatorType == RORMutator.MutantType.IFEQ) {
            return new RORMutatorIFEQMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == RORMutator.MutantType.IFGE) {
            return new RORMutatorIFGEMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == RORMutator.MutantType.IFGT) {
            return new RORMutatorIFGTMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == RORMutator.MutantType.IFLE) {
            return new RORMutatorIFLEMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == RORMutator.MutantType.IFLT) {
            return new RORMutatorIFLTMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == RORMutator.MutantType.IFNE) {
            return new RORMutatorIFNEMethodVisitor(this, context, methodVisitor);
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
        return "ROR Mutator - " + this.mutatorType.name();
    }

}

class RORMutatorIFEQMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, AbstractJumpMutator.Substitution> MUTATIONS = new HashMap<Integer, AbstractJumpMutator.Substitution>();

    static {

        // Due to how Java bytecode works, this operands will *appear* flipped when used in else conditions.
        //Functionally, these operators work as intended
        MUTATIONS.put(Opcodes.IFLE, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR Mutator: Changed operator <= to =="));
        MUTATIONS.put(Opcodes.IFLT, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR Mutator: Changed operator < to =="));
        MUTATIONS.put(Opcodes.IFGE, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR Mutator: Changed operator >= to =="));
        MUTATIONS.put(Opcodes.IFGT, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR Mutator: Changed operator > to =="));
        MUTATIONS.put(Opcodes.IFNE, new AbstractJumpMutator.Substitution(Opcodes.IFEQ, "ROR Mutator: Changed operator != to =="));

        MUTATIONS.put(Opcodes.IF_ICMPGT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPEQ, "ROR Mutator: Changed operator > to =="));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPEQ, "ROR Mutator: Changed operator >= to =="));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPEQ, "ROR Mutator: Changed operator <= to =="));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPEQ, "ROR Mutator: Changed operator < to =="));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPEQ, "ROR Mutator: Changed operator != to =="));
    }

    RORMutatorIFEQMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, AbstractJumpMutator.Substitution> getMutations() {
        return MUTATIONS;
    }

}

class RORMutatorIFGEMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, AbstractJumpMutator.Substitution> MUTATIONS = new HashMap<Integer, AbstractJumpMutator.Substitution>();

    static {

        // Due to how Java bytecode works, this operands will *appear* flipped when used in else conditions.
        //Functionally, these operators work as intended
        MUTATIONS.put(Opcodes.IFLE, new AbstractJumpMutator.Substitution(Opcodes.IFGE, "ROR Mutator: Changed operator <= to >="));
        MUTATIONS.put(Opcodes.IFEQ, new AbstractJumpMutator.Substitution(Opcodes.IFGE, "ROR Mutator: Changed operator == to >="));
        MUTATIONS.put(Opcodes.IFLT, new AbstractJumpMutator.Substitution(Opcodes.IFGE, "ROR Mutator: Changed operator < to >="));
        MUTATIONS.put(Opcodes.IFGT, new AbstractJumpMutator.Substitution(Opcodes.IFGE, "ROR Mutator: Changed operator > to >="));
        MUTATIONS.put(Opcodes.IFNE, new AbstractJumpMutator.Substitution(Opcodes.IFGE, "ROR Mutator: Changed operator != to >="));

        MUTATIONS.put(Opcodes.IF_ICMPGT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGE, "ROR Mutator: Changed operator > to >="));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGE, "ROR Mutator: Changed operator < to >="));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGE, "ROR Mutator: Changed operator <= to >="));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGE, "ROR Mutator: Changed operator == to >="));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGE, "ROR Mutator: Changed operator != to >="));
    }

    RORMutatorIFGEMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, AbstractJumpMutator.Substitution> getMutations() {
        return MUTATIONS;
    }

}

class RORMutatorIFGTMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, AbstractJumpMutator.Substitution> MUTATIONS = new HashMap<Integer, AbstractJumpMutator.Substitution>();

    static {

        // Due to how Java bytecode works, this operands will *appear* flipped when used in else conditions.
        //Functionally, these operators work as intended
        MUTATIONS.put(Opcodes.IFLE, new AbstractJumpMutator.Substitution(Opcodes.IFGT, "ROR Mutator: Changed operator <= to >"));
        MUTATIONS.put(Opcodes.IFLT, new AbstractJumpMutator.Substitution(Opcodes.IFGT, "ROR Mutator: Changed operator < to >"));
        MUTATIONS.put(Opcodes.IFGE, new AbstractJumpMutator.Substitution(Opcodes.IFGT, "ROR Mutator: Changed operator >= to >"));
        MUTATIONS.put(Opcodes.IFEQ, new AbstractJumpMutator.Substitution(Opcodes.IFGT, "ROR Mutator: Changed operator == to >"));
        MUTATIONS.put(Opcodes.IFNE, new AbstractJumpMutator.Substitution(Opcodes.IFGT, "ROR Mutator: Changed operator != to >"));

        MUTATIONS.put(Opcodes.IF_ICMPEQ, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGT, "ROR Mutator: Changed operator == to >"));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGT, "ROR Mutator: Changed operator >= to >"));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGT, "ROR Mutator: Changed operator <= to >"));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGT, "ROR Mutator: Changed operator < to >"));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPGT, "ROR Mutator: Changed operator != to >"));

    }

    RORMutatorIFGTMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, AbstractJumpMutator.Substitution> getMutations() {
        return MUTATIONS;
    }

}

class RORMutatorIFLEMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, AbstractJumpMutator.Substitution> MUTATIONS = new HashMap<Integer, AbstractJumpMutator.Substitution>();

    static {

        // Due to how Java bytecode works, this operands will *appear* flipped when used in else conditions.
        //Functionally, these operators work as intended
        MUTATIONS.put(Opcodes.IFGE, new AbstractJumpMutator.Substitution(Opcodes.IFLE, "ROR Mutator: Changed operator >= to <="));
        MUTATIONS.put(Opcodes.IFEQ, new AbstractJumpMutator.Substitution(Opcodes.IFLE, "ROR Mutator: Changed operator == to <="));
        MUTATIONS.put(Opcodes.IFLT, new AbstractJumpMutator.Substitution(Opcodes.IFLE, "ROR Mutator: Changed operator < to <="));
        MUTATIONS.put(Opcodes.IFGT, new AbstractJumpMutator.Substitution(Opcodes.IFLE, "ROR Mutator: Changed operator > to <="));
        MUTATIONS.put(Opcodes.IFNE, new AbstractJumpMutator.Substitution(Opcodes.IFLE, "ROR Mutator: Changed operator != to <="));

        MUTATIONS.put(Opcodes.IF_ICMPGT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLE, "ROR Mutator: Changed operator > to <="));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLE, "ROR Mutator: Changed operator < to <="));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLE, "ROR Mutator: Changed operator >= to <="));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLE, "ROR Mutator: Changed operator == to <="));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLE, "ROR Mutator: Changed operator != to <="));
    }

    RORMutatorIFLEMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, AbstractJumpMutator.Substitution> getMutations() {
        return MUTATIONS;
    }

}

class RORMutatorIFLTMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, AbstractJumpMutator.Substitution> MUTATIONS = new HashMap<Integer, AbstractJumpMutator.Substitution>();

    static {

        // Due to how Java bytecode works, this operands will *appear* flipped when used in else conditions.
        //Functionally, these operators work as intended
        MUTATIONS.put(Opcodes.IFLE, new AbstractJumpMutator.Substitution(Opcodes.IFLT, "ROR Mutator: Changed operator <= to <"));
        MUTATIONS.put(Opcodes.IFEQ, new AbstractJumpMutator.Substitution(Opcodes.IFLT, "ROR Mutator: Changed operator == to <"));
        MUTATIONS.put(Opcodes.IFGE, new AbstractJumpMutator.Substitution(Opcodes.IFLT, "ROR Mutator: Changed operator >= to <"));
        MUTATIONS.put(Opcodes.IFGT, new AbstractJumpMutator.Substitution(Opcodes.IFLT, "ROR Mutator: Changed operator > to <"));
        MUTATIONS.put(Opcodes.IFNE, new AbstractJumpMutator.Substitution(Opcodes.IFLT, "ROR Mutator: Changed operator != to <"));

        MUTATIONS.put(Opcodes.IF_ICMPGT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLT, "ROR Mutator: Changed operator > to <"));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLT, "ROR Mutator: Changed operator >= to <"));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLT, "ROR Mutator: Changed operator <= to <"));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLT, "ROR Mutator: Changed operator == to <"));
        MUTATIONS.put(Opcodes.IF_ICMPNE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPLT, "ROR Mutator: Changed operator != to <"));
    }

    RORMutatorIFLTMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, AbstractJumpMutator.Substitution> getMutations() {
        return MUTATIONS;
    }

}

class RORMutatorIFNEMethodVisitor extends AbstractJumpMutator {

    private static final Map<Integer, AbstractJumpMutator.Substitution> MUTATIONS = new HashMap<Integer, AbstractJumpMutator.Substitution>();

    static {

        // Due to how Java bytecode works, this operands will *appear* flipped when used in else conditions.
        //Functionally, these operators work as intended
        MUTATIONS.put(Opcodes.IFGE, new AbstractJumpMutator.Substitution(Opcodes.IFNE, "ROR Mutator: Changed operator >= to !="));
        MUTATIONS.put(Opcodes.IFEQ, new AbstractJumpMutator.Substitution(Opcodes.IFNE, "ROR Mutator: Changed operator == to !="));
        MUTATIONS.put(Opcodes.IFLT, new AbstractJumpMutator.Substitution(Opcodes.IFNE, "ROR Mutator: Changed operator < to !="));
        MUTATIONS.put(Opcodes.IFGT, new AbstractJumpMutator.Substitution(Opcodes.IFNE, "ROR Mutator: Changed operator > to !="));
        MUTATIONS.put(Opcodes.IFLE, new AbstractJumpMutator.Substitution(Opcodes.IFNE, "ROR Mutator: Changed operator <= to !="));

        MUTATIONS.put(Opcodes.IF_ICMPGT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPNE, "ROR Mutator: Changed operator > to !="));
        MUTATIONS.put(Opcodes.IF_ICMPLT, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPNE, "ROR Mutator: Changed operator < to !="));
        MUTATIONS.put(Opcodes.IF_ICMPGE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPNE, "ROR Mutator: Changed operator >= to !="));
        MUTATIONS.put(Opcodes.IF_ICMPEQ, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPNE, "ROR Mutator: Changed operator == to !="));
        MUTATIONS.put(Opcodes.IF_ICMPLE, new AbstractJumpMutator.Substitution(Opcodes.IF_ICMPNE, "ROR Mutator: Changed operator <= to !="));
    }

    RORMutatorIFNEMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(factory, context, delegateMethodVisitor);
    }

    @Override
    protected Map<Integer, AbstractJumpMutator.Substitution> getMutations() {
        return MUTATIONS;
    }

}
