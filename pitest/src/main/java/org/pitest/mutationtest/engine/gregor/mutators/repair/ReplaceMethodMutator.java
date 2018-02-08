/*
 * Copyright 2018 org.pitest.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.mutators.repair;

import java.util.ArrayList;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.classpath.MethodParameterNode;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

/**
 *
 * @author Samuel Benton
 */
public class ReplaceMethodMutator implements MethodMutatorFactory {

    @Override
    public MethodVisitor create(final MutationContext context, final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new ReplaceMethodMutatorMethodVisitor(this, context, methodVisitor, methodInfo);
    }

    @Override
    public String getGloballyUniqueId() {
        return this.getClass().getName();
    }

    @Override
    public String getName() {
        return "Replace_Method Mutator";
    }
}

class ReplaceMethodMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;
    private final MethodInfo methodInfo;

    ReplaceMethodMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor, final MethodInfo methodInfo) {
        super(Opcodes.ASM6, delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
        this.methodInfo = methodInfo;

    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if (!this.methodInfo.isConstructor()) {

            MethodParameterNode mpn = getParameterNode(parseMethod(owner, name, desc));

            if (mpn != null) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "Switched method invocation of " + name + " with invocation of " + mpn.toString());

                if (this.context.shouldMutate(muID)) {

                    // Change details of the method invocation
                    super.visitMethodInsn(opcode, mpn.getOwnerClass(), mpn.getName(), mpn.getDescriptor(), itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    private ArrayList<MethodParameterNode> parseMethod(String owner, String name, String desc) {

        // Pulls data which should be saved locally to the project
        ArrayList<MethodParameterNode> mpnClassData = new ArrayList(MethodParameterNode.deserializeMethodParameters(MethodParameterNode.SERIAL_FILEPATH));

        // Method's return object
        ArrayList<MethodParameterNode> matchedMethods = new ArrayList();

        for (MethodParameterNode mpn : mpnClassData) {
            if (mpn != null) {
                // Filters out methods with different descriptors
                if (mpn.getDescriptor().equals(desc)) {

                    // Filters out methods whose name is the same as the original method invocation name
                    if (!mpn.getName().equals(name)) {

                        // Filters out methods outside the original method invocation class
                        if (mpn.getOwnerClass().equals(owner)) {

                            // This methodParameterNode is 'compatible' with the current method.
                            // Add this method details to a central pool for usage later
                            matchedMethods.add(mpn);
                        }
                    }
                }
            }
        }

        System.out.println("Compatible method invocation replacements for this method" + owner + " -> " + name + ":\t" + matchedMethods.size());
        System.out.println("");

        return matchedMethods;
    }

    private MethodParameterNode getParameterNode(ArrayList<MethodParameterNode> mpnData) {

        if (mpnData.isEmpty()) {

            // If mpnData is empty, return nothing (null) to prevent the mutator from occuring    
            return null;

        } else {

            // Some algorithm to decide which MethodParameterNode to pull/return
            return mpnData.get(0);
        }

    }
}
