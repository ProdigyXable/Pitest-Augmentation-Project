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

public enum CRCRMutatorNegate implements MethodMutatorFactory {

    CRCR_MUTATOR_NEGATE;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new CRCRMutatorNegateMethodVisitor(this, context, methodVisitor);
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

class CRCRMutatorNegateMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    CRCRMutatorNegateMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        super.visitLdcInsn(cst);
        if (cst instanceof Number) {

            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Negated value of constant. " + cst.getClass().getTypeName());

            if (this.context.shouldMutate(muID)) {
                if (cst instanceof Integer) {
                    super.visitInsn(Opcodes.INEG);
                } else if (cst instanceof Float) {
                    super.visitInsn(Opcodes.FNEG);
                } else if (cst instanceof Double) {
                    super.visitInsn(Opcodes.DNEG);
                } else if (cst instanceof Long) {
                    super.visitInsn(Opcodes.LNEG);
                }
            }
        }
    }

    @Override
    public void visitInsn(int opcode) {

        super.visitInsn(opcode);
        if ((opcode == Opcodes.ICONST_0) || (opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1) || (opcode == Opcodes.LCONST_0) || (opcode == Opcodes.LCONST_1) || (opcode == Opcodes.FCONST_0) || (opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2) || (opcode == Opcodes.DCONST_0) || (opcode == Opcodes.DCONST_1)) {

            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Negated value of common constant (visitInsn)");

            if (this.context.shouldMutate(muID)) {
                if ((opcode == Opcodes.ICONST_0) || (opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1)) {
                    super.visitInsn(Opcodes.INEG);
                } else if ((opcode == Opcodes.LCONST_0) || (opcode == Opcodes.LCONST_1)) {
                    super.visitInsn(Opcodes.LNEG);
                } else if ((opcode == Opcodes.FCONST_0) || (opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2)) {
                    super.visitInsn(Opcodes.FNEG);
                } else if ((opcode == Opcodes.DCONST_0) || (opcode == Opcodes.DCONST_1)) {
                    super.visitInsn(Opcodes.DNEG);
                }
            }
        }
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {

        super.visitIntInsn(opcode, operand);

        if ((opcode == Opcodes.BIPUSH) || (opcode == Opcodes.SIPUSH)) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Negated value of custom constant");

            if (this.context.shouldMutate(muID)) {
                super.visitInsn(Opcodes.INEG);
            }
        }
    }

}

