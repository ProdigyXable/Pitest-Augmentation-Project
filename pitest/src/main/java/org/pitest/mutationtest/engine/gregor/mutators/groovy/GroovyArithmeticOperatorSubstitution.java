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

public enum GroovyArithmeticOperatorSubstitution implements MethodMutatorFactory {

    GADD, GSUB, GDIV, GMUL, GREM;

    @Override
    public MethodVisitor create(final MutationContext context,
            final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        if (name().equals("GADD")) {
            return new GADDMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GSUB")) {
            return new GSUBMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GMUL")) {
            return new GMULMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GDIV")) {
            return new GDIVMethodVisitor(this, methodInfo, context, methodVisitor);
        } else if (name().equals("GREM")) {
            return new GREMMethodVisitor(this, methodInfo, context, methodVisitor);
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
        return "GroovyArithmeticSubstitutionOperator" + ":" + name();
    }

}

class GADDMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GADDMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

//    @Override
//    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//        final MutationIdentifier muID = this.context.registerMutation(factory, "Proof of concept mutator:\t" + name);
//        if (this.context.shouldMutate(muID)) {
//            System.out.println("Hi :)");
//        }
//        super.visitMethodInsn(opcode, owner, name, desc, itf); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof String) {
            String str = (String) cst;

            if (str.equals("minus") || str.equals("multiply") || str.equals("div") || str.equals("mod")) {

                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Minus Substitution: Mutated arithmetic operator to +");

                if (this.context.shouldMutate(muID)) {
                    cst = "plus";
                }
            }
        }

        super.visitLdcInsn(cst);
    }

}

class GSUBMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GSUBMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof String) {
            String str = (String) cst;

            if (str.equals("plus") || str.equals("multiply") || str.equals("div") || str.equals("mod")) {

                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Minus Substitution: Mutated arithmetic operator to -");

                if (this.context.shouldMutate(muID)) {
                    cst = "minus";
                }
            }
        }

        super.visitLdcInsn(cst);
    }

}

class GMULMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GMULMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof String) {
            String str = (String) cst;

            if (str.equals("plus") || str.equals("minus") || str.equals("div") || str.equals("mod")) {

                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Multiply Substitution: Mutated arithmetic operator to *");

                if (this.context.shouldMutate(muID)) {
                    cst = "multiply";
                }
            }
        }

        super.visitLdcInsn(cst);
    }

}

class GDIVMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GDIVMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof String) {
            String str = (String) cst;

            if (str.equals("plus") || str.equals("multiply") || str.equals("minus") || str.equals("mod")) {

                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Division Substitution: Mutated arithmetic operator to /");

                if (this.context.shouldMutate(muID)) {
                    cst = "div";
                }
            }
        }

        super.visitLdcInsn(cst);
    }

}

class GREMMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    GREMMethodVisitor(final MethodMutatorFactory factory,
            final MethodInfo methodInfo, final MutationContext context, final MethodVisitor mv) {
        super(Opcodes.ASM6, mv);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitLdcInsn(Object cst) {
        if (cst instanceof String) {
            String str = (String) cst;

            if (str.equals("plus") || str.equals("multiply") || str.equals("div") || str.equals("minus")) {

                final MutationIdentifier muID = this.context.registerMutation(factory, "Groovy Modulus Substitution: Mutated arithmetic operator to %");

                if (this.context.shouldMutate(muID)) {
                    cst = "mod";
                }
            }
        }

        super.visitLdcInsn(cst);
    }

}
