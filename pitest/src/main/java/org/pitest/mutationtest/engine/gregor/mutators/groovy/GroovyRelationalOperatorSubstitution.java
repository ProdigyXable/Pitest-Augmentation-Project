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
package org.pitest.mutationtest.engine.gregor.mutators.groovy;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

public enum GroovyRelationalOperatorSubstitution implements MethodMutatorFactory {

    GGreaterThan, GLessThan, GEqual, GGreaterThanEqual, GLessThanEqual, GNotEqual;

    public static final String RELATIONAL_OPERATOR_PARENT_CLASS = "org/codehaus/groovy/runtime/ScriptBytecodeAdapter";

    public static final String GT_METHOD_NAME = "compareGreaterThan";
    public static final String LT_METHOD_NAME = "compareLessThan";
    public static final String EQ_METHOD_NAME = "compareEqual";

    public static final String GTE_METHOD_NAME = "compareGreaterThanEqual";
    public static final String LTE_METHOD_NAME = "compareLessThanEqual";
    public static final String NE_METHOD_NAME = "compareNotEqual";

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        if (name().equals("GGreaterThan")) {
            return new GGTMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GLessThan")) {
            return new GLTMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GEqual")) {
            return new GEQMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GGreaterThanEqual")) {
            return new GGTEMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GLessThanEqual")) {
            return new GLTEMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GNotEqual")) {
            return new GNEMethodVisitor(this, methodInfo, context, methodVisitor);
        } else {
            return null;
        }
    }

    @Override
    public String getGloballyUniqueId() {
        return this.getClass().getName() + ":" + name();
    }

    @Override
    public String getName() {
        return "GroovyRelationalSubstitutionOperator" + ":" + name();
    }

}

class GGTMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GGTMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (owner.equals(GroovyRelationalOperatorSubstitution.RELATIONAL_OPERATOR_PARENT_CLASS)) {
            if (name.equals(GroovyRelationalOperatorSubstitution.EQ_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.NE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.LT_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.LTE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.GTE_METHOD_NAME)) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Relational Operator: Mutated relational operator to > ");

                if (this.context.shouldMutate(muID)) {
                    super.visitMethodInsn(opcode, owner, GroovyRelationalOperatorSubstitution.GT_METHOD_NAME, desc, itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

}

class GGTEMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GGTEMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (owner.equals(GroovyRelationalOperatorSubstitution.RELATIONAL_OPERATOR_PARENT_CLASS)) {
            if (name.equals(GroovyRelationalOperatorSubstitution.EQ_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.NE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.LT_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.LTE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.GT_METHOD_NAME)) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Relational Operator: Mutated relational operator to >= ");

                if (this.context.shouldMutate(muID)) {
                    super.visitMethodInsn(opcode, owner, GroovyRelationalOperatorSubstitution.GTE_METHOD_NAME, desc, itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}

class GLTMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GLTMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (owner.equals(GroovyRelationalOperatorSubstitution.RELATIONAL_OPERATOR_PARENT_CLASS)) {
            if (name.equals(GroovyRelationalOperatorSubstitution.EQ_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.NE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.GT_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.LTE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.GTE_METHOD_NAME)) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Relational Operator: Mutated relational operator to < ");

                if (this.context.shouldMutate(muID)) {
                    super.visitMethodInsn(opcode, owner, GroovyRelationalOperatorSubstitution.LT_METHOD_NAME, desc, itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}

class GLTEMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GLTEMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (owner.equals(GroovyRelationalOperatorSubstitution.RELATIONAL_OPERATOR_PARENT_CLASS)) {
            if (name.equals(GroovyRelationalOperatorSubstitution.EQ_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.NE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.LT_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.GT_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.GTE_METHOD_NAME)) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Relational Operator: Mutated relational operator to <= ");

                if (this.context.shouldMutate(muID)) {
                    super.visitMethodInsn(opcode, owner, GroovyRelationalOperatorSubstitution.LTE_METHOD_NAME, desc, itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}

class GEQMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GEQMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (owner.equals(GroovyRelationalOperatorSubstitution.RELATIONAL_OPERATOR_PARENT_CLASS)) {
            if (name.equals(GroovyRelationalOperatorSubstitution.GT_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.NE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.LT_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.LTE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.GTE_METHOD_NAME)) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Relational Operator: Mutated relational operator to == ");

                if (this.context.shouldMutate(muID)) {
                    super.visitMethodInsn(opcode, owner, GroovyRelationalOperatorSubstitution.EQ_METHOD_NAME, desc, itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}

class GNEMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GNEMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (owner.equals(GroovyRelationalOperatorSubstitution.RELATIONAL_OPERATOR_PARENT_CLASS)) {
            if (name.equals(GroovyRelationalOperatorSubstitution.EQ_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.GT_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.LT_METHOD_NAME) || name.equals(GroovyRelationalOperatorSubstitution.LTE_METHOD_NAME)
                    || name.equals(GroovyRelationalOperatorSubstitution.GTE_METHOD_NAME)) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Relational Operator: Mutated relational operator to != ");

                if (this.context.shouldMutate(muID)) {
                    super.visitMethodInsn(opcode, owner, GroovyRelationalOperatorSubstitution.NE_METHOD_NAME, desc, itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}
