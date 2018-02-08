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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
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
    private final String serializedFilename = "methodParameterData.txt";

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
                final MutationIdentifier muID = this.context.registerMutation(factory, "Switched method invocation of " + name + " with invocation of " + mpn.getName());

                if (this.context.shouldMutate(muID)) {

                    super.visitMethodInsn(opcode, owner, mpn.getName(), desc, itf);
                    return;
                }
            }
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    private ArrayList<MethodParameterNode> parseMethod(String owner, String name, String desc) {

        ArrayList<MethodParameterNode> mpnClassData = new ArrayList(deserializeMethodParameters(this.serializedFilename));

        ArrayList<MethodParameterNode> matchedMethods = new ArrayList();

        for (MethodParameterNode mpn : mpnClassData) {
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

    private MethodParameterNode getParameterNode(ArrayList<MethodParameterNode> mpnData) {
        // Some algorithim to decide which MethodParameterNode to pull/return
        return mpnData.get(0);

    }

    private Collection<MethodParameterNode> deserializeMethodParameters(String serialFilename) {
        try {
            FileInputStream fileStream = new FileInputStream(serialFilename);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            ArrayList<MethodParameterNode> mpnData = (ArrayList) objectStream.readObject();

            objectStream.close();
            fileStream.close();

            return mpnData;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
