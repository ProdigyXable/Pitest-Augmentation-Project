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

public enum CRCRMutatorSubOne implements MethodMutatorFactory {

    CRCR_MUTATOR_SUB_ONE;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new CRCRMutatorSubOneMethodVisitor(this, context, methodVisitor);
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

class CRCRMutatorSubOneMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    CRCRMutatorSubOneMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof Number) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Decremented value of constant. " + cst.getClass().getTypeName());

            if (this.context.shouldMutate(muID)) {
                if (cst instanceof Integer) {
                    super.visitLdcInsn(((Integer) cst) - 1);
                } else if (cst instanceof Float) {
                    super.visitLdcInsn(((Float) cst) - 1F);
                } else if (cst instanceof Double) {
                    super.visitLdcInsn(((Double) cst) - 1D);
                } else if (cst instanceof Long) {
                    super.visitLdcInsn(((Long) cst) - 1L);
                }
            } else {
                super.visitLdcInsn(cst);
            }
        } else {
            super.visitLdcInsn(cst);
        }

    }

    @Override
    public void visitInsn(int opcode) {

        super.visitInsn(opcode);
        if ((opcode == Opcodes.ICONST_0) || (opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1) || (opcode == Opcodes.LCONST_0) || (opcode == Opcodes.LCONST_1) || (opcode == Opcodes.FCONST_0) || (opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2) || (opcode == Opcodes.DCONST_0) || (opcode == Opcodes.DCONST_1)) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Decremented value of common constant (visitInsn)");

            if (this.context.shouldMutate(muID)) {
                if ((opcode == Opcodes.ICONST_0) || (opcode == Opcodes.ICONST_1) || (opcode == Opcodes.ICONST_2) || (opcode == Opcodes.ICONST_3) || (opcode == Opcodes.ICONST_4) || (opcode == Opcodes.ICONST_5) || (opcode == Opcodes.ICONST_M1)) {
                    super.visitInsn(Opcodes.ICONST_1);
                    super.visitInsn(Opcodes.ISUB);
                } else if ((opcode == Opcodes.LCONST_0) || (opcode == Opcodes.LCONST_1)) {
                    super.visitInsn(Opcodes.LCONST_1);
                    super.visitInsn(Opcodes.LSUB);
                } else if ((opcode == Opcodes.FCONST_0) || (opcode == Opcodes.FCONST_1) || (opcode == Opcodes.FCONST_2)) {
                    super.visitInsn(Opcodes.FCONST_1);
                    super.visitInsn(Opcodes.FSUB);
                } else if ((opcode == Opcodes.DCONST_0) || (opcode == Opcodes.DCONST_1)) {
                    super.visitInsn(Opcodes.DCONST_1);
                    super.visitInsn(Opcodes.DSUB);
                }
            } else {

            }
        }
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        if ((opcode == Opcodes.BIPUSH) || (opcode == Opcodes.SIPUSH)) {
            final MutationIdentifier muID = this.context.registerMutation(factory, "CRCR Mutator: Decremented value of custom constant");

            if (this.context.shouldMutate(muID)) {

                super.visitIntInsn(opcode, operand - 1);
            } else {
                super.visitIntInsn(opcode, operand);
            }
        } else {
            super.visitIntInsn(opcode, operand);
        }
    }
}
