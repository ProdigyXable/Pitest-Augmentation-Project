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

public enum ABSStoreMutator implements MethodMutatorFactory {

    ABS_STORE_MUTATOR;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new ABSStoreMethodVisitor(this, context, methodVisitor);
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

class ABSStoreMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;
    private final String messageA = "ABS Mutator: Variable value negated immediately before store [";

    ABSStoreMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;

    }
    
    @Override
    public void visitVarInsn(final int opcode, final int var) {
        String mutatorMessage = "";
        int newOpCode = 0;

        if (opcode == Opcodes.ISTORE) {
            mutatorMessage = var + "] (integer)";
            newOpCode = Opcodes.INEG;

        } else if (opcode == Opcodes.FSTORE) {
            mutatorMessage = var + "] (float)";
            newOpCode = Opcodes.FNEG;

        } else if (opcode == Opcodes.DSTORE) {
            mutatorMessage = var + "] (double)";
            newOpCode = Opcodes.DNEG;

        } else if (opcode == Opcodes.LSTORE) {
            mutatorMessage = var + "] (long)";
            newOpCode = Opcodes.LNEG;
        }

        if (newOpCode != 0) {
            final MutationIdentifier muID = this.context.registerMutation(this.factory, this.messageA + mutatorMessage);

            if (this.context.shouldMutate(muID)) {
                // Performs the appropriate negation command on the top of the stack *BEFORE* the variable is stored
                super.visitInsn(newOpCode);
            }
        }

        // Let the super visitVarInsn execute as originally intended
        super.visitVarInsn(opcode, var);
    }
    
}
