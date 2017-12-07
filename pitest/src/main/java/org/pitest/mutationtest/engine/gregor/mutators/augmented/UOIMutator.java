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

public class UOIMutator implements MethodMutatorFactory {

    public enum MutantType {

        INCREMENT, DECREMENT, REMOVE, REVERSE;
    }

    private final MutantType mutatorType;

    public UOIMutator(MutantType mt) {
        this.mutatorType = mt;
    }

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        if (this.mutatorType == UOIMutator.MutantType.INCREMENT) {
            return new UOIAddIncrementMutatorMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == UOIMutator.MutantType.DECREMENT) {
            return new UOIAddDecrementMutatorMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == UOIMutator.MutantType.REVERSE) {
            return new UOIReverseMutatorMethodVisitor(this, context, methodVisitor);
        } else if (this.mutatorType == UOIMutator.MutantType.REMOVE) {
            return new UOIRemoveMutatorMethodVisitor(this, context, methodVisitor);
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
        return "UOI Mutator - " + this.mutatorType.name();
    }
}

// This operator works exclusively on function local variables, not object variables
class UOIAddIncrementMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

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

// This operator works exclusively on function local variables, not object variables
class UOIAddDecrementMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    UOIAddDecrementMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitIincInsn(final int var, final int increment) {

        final MutationIdentifier newId = this.context.registerMutation(this.factory, "UOI Mutator: Added unary decrement "
                + increment + " -> " + (increment - 1) + " to local variable");

        if (this.context.shouldMutate(newId)) {
            this.mv.visitIincInsn(var, increment - 1);
        } else {
            this.mv.visitIincInsn(var, increment);
        }
    }

}

// This operator works exclusively on function local variables, not object variables
class UOIRemoveMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    UOIRemoveMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitIincInsn(final int var, final int increment) {
        final MutationIdentifier newId = this.context.registerMutation(this.factory,
                "UOI Mutator: Removed unary increment of local variable");

        if (this.context.shouldMutate(newId)) {
            super.mv.visitIincInsn(var, 0);
        } else {
            super.mv.visitIincInsn(var, increment);
        }
    }

}

// This operator works exclusively on function local variables, not object variables
class UOIReverseMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    UOIReverseMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitIincInsn(final int var, final int increment) {
        final MutationIdentifier newId = this.context.registerMutation(this.factory,
                "UOI Mutator: Reversed increment of local variable");

        if (this.context.shouldMutate(newId)) {
            this.mv.visitIincInsn(var, -increment);
        } else {
            this.mv.visitIincInsn(var, increment);
        }
    }

}
