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
import java.util.Collection;
import java.util.Vector;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.classinfo.ClassInfoVisitor;
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

    private Collection<MethodParameterNode> parseMethod(String owner, String name, String desc) {
        Collection<MethodParameterNode> matchedMethods = new Vector<MethodParameterNode>();

        System.out.println("--- Method invocation detected:\t [" + name + "]");
        System.out.println("--- Method found?:\t" + ClassInfoVisitor.parameterNodes.contains(new MethodParameterNode(name, desc, owner)));
        System.out.println("");

        for (MethodParameterNode mpn : ClassInfoVisitor.parameterNodes) {
            if (mpn != null) {
                // Used to find other stored methods with the same descriptor as the original method
                if (mpn.getDescriptor().equals(desc)) {

                    // Used to prevent detection of itself
                    if (!mpn.getName().equals(name)) {

                        // Used in case you want to limit replaced methods to those within the same class
                        if (mpn.getOwnerClass().equals(owner)) {
                            matchedMethods.add(mpn);
                        }
                    }
                }
            }
        }
        return matchedMethods;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (!this.methodInfo.isConstructor()) {
            ArrayList<MethodParameterNode> matchedMethods = new ArrayList(parseMethod(owner, name, desc));

            MethodParameterNode mpn = getParameterNode(matchedMethods);

            if (mpn != null) {
                final MutationIdentifier muID = this.context.registerMutation(factory, "");
                
                if (this.context.shouldMutate(muID)) {

                    super.visitMethodInsn(opcode, owner, mpn.getName(), desc, itf);
                    return;
                }
            }
        }

        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    private MethodParameterNode getParameterNode(final ArrayList<MethodParameterNode> matchedMethods) {

        if (matchedMethods.isEmpty()) {
            return null;
        } else {

            // Some algorithim to decide which method to pull
            return matchedMethods.get(0);

        }
    }
}
