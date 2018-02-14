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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.pitest.classpath.MethodParameterNode;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

/**
 *
 * @author Samuel Benton
 */
public enum ReplaceMethodMutator implements MethodMutatorFactory {

    /**
     * Creates a mutator which changes one method invocation to another method
     * invocation within the same class with a different name, same descriptor,
     * and same return type as the original method invocation
     */
    SUBSTITUTE_METHOD_DESCRIPTOR,
    
    /**
     * Creates a mutator which changes one method invocation to another method
     * invocation within the same class with the same name, different
     * descriptor, and same return type as the original method invocation
     */
    SUBSTITUTE_METHOD_NAME;

    @Override
    public MethodVisitor create(final MutationContext context, final MethodInfo methodInfo, final MethodVisitor methodVisitor) {

        if (this.name().equals("SUBSTITUTE_METHOD_DESCRIPTOR")) {
            return new MethodDescriptorSubstituteMutatorMethodVisitor(this, context, methodVisitor, methodInfo);
        } else if (this.name().equals("SUBSTITUTE_METHOD_NAME")) {
            return new MethodNameSubstituteMutatorMethodVisitor(this, context, methodVisitor, methodInfo);
        } else {
            return null;
        }
    }

    @Override
    public String getGloballyUniqueId() {
        return this.getClass().getName() + ":" + this.name();
    }

    @Override
    public String getName() {
        return "Replace Method Mutator: " + this.name();
    }
}

class MethodDescriptorSubstituteMutatorMethodVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;
    private final MethodInfo methodInfo;

    MethodDescriptorSubstituteMutatorMethodVisitor(final MethodMutatorFactory factory,
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

                            // Ensures the return types are the same and thus compatible
                            if (mpn.getReturnType().equals(Type.getReturnType(desc))) {

                                // This methodParameterNode is 'compatible' with the current method. (Same descriptor, different name, same class)
                                // Add this method details to a central pool for usage later
                                matchedMethods.add(mpn);
                            }
                        }
                    }
                }
            }
        }
        return matchedMethods;
    }

    /**
     * Function used to decide which MethodParameterNode to use when replacing a
     * method invocation
     *
     * @param mpnData - A collection of (compatible) MethodParameterNodes for
     * the method invocation replacement
     * @return A MethodParameterNode containing information needed to
     * successfully replace a method invocation
     */
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

class MethodNameSubstituteMutatorMethodVisitor extends LocalVariablesSorter {

    private final MethodMutatorFactory factory;
    private final MutationContext context;
    private final MethodInfo methodInfo;

    MethodNameSubstituteMutatorMethodVisitor(final MethodMutatorFactory factory,
            final MutationContext context, final MethodVisitor delegateMethodVisitor, final MethodInfo methodInfo) {
        super(Opcodes.ASM6, methodInfo.getAccess(), methodInfo.getMethodDescriptor(), delegateMethodVisitor);
        this.factory = factory;
        this.context = context;
        this.methodInfo = methodInfo;

    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

        if (!this.methodInfo.isConstructor()) {

            MethodParameterNode mpn = getParameterNode(parseMethod(owner, name, desc));
            if (mpn != null) {

                try {

                    final MutationIdentifier muID = this.context.registerMutation(factory, "Switched method invocation of " + name + " with invocation of " + mpn.toString());

                    if (this.context.shouldMutate(muID)) {

                        // Allocate space for new method invocation parameters
                        correctMethodParameterValues(desc, mpn.getDescriptor());

                        // Reconstruct the appropriate parameter stack for the new method invocaiton
                        reconstructStack(mpn.getDescriptor());

                        super.visitMethodInsn(opcode, mpn.getOwnerClass(), mpn.getName(), mpn.getDescriptor(), itf);
                        return;

                    }

                } catch (Exception ex) {
                    Logger.getLogger(MethodNameSubstituteMutatorMethodVisitor.class.getName()).log(Level.SEVERE, null, ex);
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
                // Filters out methods with different names
                if (mpn.getName().equals(name)) {

                    // Filters out methods whose descriptor is the same as the original method invocation descriptor
                    if (!mpn.getDescriptor().equals(desc)) {

                        // Filters out methods outside the original method invocation class
                        if (mpn.getOwnerClass().equals(owner)) {

                            // Ensures the return types are the same for the new method invocation
                            if (mpn.getReturnType().equals(Type.getReturnType(desc))) {
                                // This methodParameterNode is 'compatible' with the current method. (Same name, different descriptor, same class)
                                // Add this method details to a central pool for usage later
                                matchedMethods.add(mpn);
                            }
                        }
                    }
                }
            }
        }
        return matchedMethods;
    }

    /**
     * Function used to decide which MethodParameterNode to use when replacing a
     * method invocation
     *
     * @param mpnData - A collection of (compatible) MethodParameterNodes for
     * the method invocation replacement
     * @return A MethodParameterNode containing information needed to
     * successfully replace a method invocation
     */
    private MethodParameterNode getParameterNode(ArrayList<MethodParameterNode> mpnData) {

        if (mpnData.isEmpty()) {

            // If mpnData is empty, return nothing (null) to prevent the mutator from occuring    
            return null;

        } else {

            // Some algorithm to decide which MethodParameterNode to pull/return
            return mpnData.get(0);
        }

    }

    /**
     * For a new method invocation, translates the original method parameters
     * into local variables, inserting default values when the nth parameter of
     * original method invocation and the new invocation do not match
     *
     * @param originalDescriptor - Descriptor of the original method invocation
     * @param newDescriptor - Descriptor of the new method invocation
     * @throws Exception
     */
    private void correctMethodParameterValues(String originalDescriptor, String newDescriptor) throws Exception {
        Type[] originalParameters = Type.getArgumentTypes(originalDescriptor);
        Type[] newParameters = Type.getArgumentTypes(newDescriptor);

        if (originalParameters.length > newParameters.length) { // If the new method invocation has less parameters than the original method invocation

            for (int index = originalParameters.length - 1; index >= 0; index -= 1) {

                int newLocalVariableIndex = this.nextLocal;

                if (index >= newParameters.length) {

                    this.popTopStackValue(originalParameters[index]);

                } else {
                    // this.newLocal(newParameters[index]);
                    processParameters(originalParameters, newParameters, index, newLocalVariableIndex);

                }

            }
        } else if (originalParameters.length < newParameters.length) { // If the new method invocation has more parameters than the original method invocation

            for (int index = newParameters.length - 1; index >= 0; index--) {

                int newLocalVariableIndex = this.nextLocal;
                // this.newLocal(newParameters[index]);

                if (index > originalParameters.length - 1) {

                    // Insert default value for new parameter Type to local variable
                    this.insertTypeDefaultValue(newParameters[index], newLocalVariableIndex);

                } else {
                    processParameters(originalParameters, newParameters, index, newLocalVariableIndex);
                }
            }

        } else { // If the new method invocation has the same number of parameters as the original method invocation

            for (int index = originalParameters.length - 1; index >= 0; index--) {

                int newLocalVariableIndex = this.nextLocal;

                // this.newLocal(newParameters[index]);
                processParameters(originalParameters, newParameters, index, newLocalVariableIndex);

            }
        }
    }

    private void processParameters(Type[] originalParameters, Type[] newParameters, int index, int newLocalVariableIndex) throws Exception {
        if (originalParameters[index].equals(newParameters[index])) {

            // Transfer value at top of stack to local variable (stack value automatically popped)
            this.storeTopStackValue(newParameters[index], newLocalVariableIndex);

        } else {
            // Insert default value for new parameter Type to local variable
            this.insertTypeDefaultValue(newParameters[index], newLocalVariableIndex);

            // Pop value already existing on the stack
            this.popTopStackValue(originalParameters[index]);

        }
    }

    /**
     * Adds a byte instruction to pop the topmost stack value
     *
     * @param The given Type value resting on top of the stack
     */
    private void popTopStackValue(Type t) {
        if (t.equals(Type.LONG_TYPE) || t.equals(Type.DOUBLE_TYPE)) {
            super.visitInsn(Opcodes.POP2);
        } else {
            super.visitInsn(Opcodes.POP);
        }
    }

    /**
     * Function used to insert a default value onto the stack. Calls a function
     * {@link #storeTopStackValue(org.objectweb.asm.Type, int)} which stores the
     * stack's topmost value into a local variable
     *
     * @param type - Used to determine the default value inserted onto the stack
     * @param localVariableIndex - The index of the local variable which will
     * store the default value
     * @throws Exception
     * @see #storeTopStackValue(org.objectweb.asm.Type, int)
     */
    private void insertTypeDefaultValue(Type type, int localVariableIndex) throws Exception {
        if (type.equals(Type.INT_TYPE) || type.equals(Type.BYTE_TYPE) || type.equals(Type.SHORT_TYPE) || type.equals(Type.BOOLEAN_TYPE)) {
            super.visitInsn(Opcodes.ICONST_0);
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            super.visitInsn(Opcodes.DCONST_0);
        } else if (type.equals(Type.FLOAT_TYPE)) {
            super.visitInsn(Opcodes.FCONST_0);
        } else if (type.equals(Type.LONG_TYPE)) {
            super.visitInsn(Opcodes.LCONST_0);
        } else if (type.equals(Type.VOID_TYPE)) {
            throw new Exception("VOID TYPE DETECTED");
        } else {
            super.visitInsn(Opcodes.ACONST_NULL);
        }

        this.storeTopStackValue(type, localVariableIndex);
    }

    /**
     * Stores the stack's topmost value into a local variable
     *
     * @param type - The type corresponding to the stack's topmost value
     * @param localVariableIndex - The index of the local variable which will
     * store the topmost stack value
     * @throws Exception
     */
    private void storeTopStackValue(Type type, int localVariableIndex) throws Exception {
        if (type.equals(Type.INT_TYPE) || type.equals(Type.BYTE_TYPE) || type.equals(Type.SHORT_TYPE) || type.equals(Type.BOOLEAN_TYPE)) {
            super.visitVarInsn(Opcodes.ISTORE, localVariableIndex);
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            super.visitVarInsn(Opcodes.DSTORE, localVariableIndex);
        } else if (type.equals(Type.FLOAT_TYPE)) {
            super.visitVarInsn(Opcodes.FSTORE, localVariableIndex);
        } else if (type.equals(Type.LONG_TYPE)) {
            super.visitVarInsn(Opcodes.LSTORE, localVariableIndex);
        } else if (type.equals(Type.VOID_TYPE)) {
            throw new Exception("VOID TYPE DETECTED");
        } else {
            super.visitVarInsn(Opcodes.ASTORE, localVariableIndex);
        }
    }

    /**
     * Loads a value from a local variable onto the stack
     *
     * @param type - Type of variable which should be loaded onto the stack
     * @param localVariableIndex - Index of the local variable
     * @throws Exception
     */
    private void loadLocalVariableToStack(Type type, int localVariableIndex) throws Exception {
        if (type.equals(Type.INT_TYPE) || type.equals(Type.BYTE_TYPE) || type.equals(Type.SHORT_TYPE) || type.equals(Type.BOOLEAN_TYPE)) {
            super.visitVarInsn(Opcodes.ILOAD, localVariableIndex);
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            super.visitVarInsn(Opcodes.DLOAD, localVariableIndex);
        } else if (type.equals(Type.FLOAT_TYPE)) {
            super.visitVarInsn(Opcodes.FLOAD, localVariableIndex);
        } else if (type.equals(Type.LONG_TYPE)) {
            super.visitVarInsn(Opcodes.LLOAD, localVariableIndex);
        } else if (type.equals(Type.VOID_TYPE)) {
            throw new Exception("VOID TYPE DETECTED");
        } else {
            super.visitVarInsn(Opcodes.ALOAD, localVariableIndex);
        }
    }

    /**
     * Reconstructs the proper stack for the new method invocation by loading
     * the method's ending local variables onto the stack
     *
     * @param descriptor - Valid method descriptor used to generate the
     * parameter's of a given method
     * @throws Exception
     */
    private void reconstructStack(String descriptor) throws Exception {
        Type[] newParameters = Type.getArgumentTypes(descriptor);

        if (newParameters.length > 0) {
            int currentVariableIndex = this.nextLocal - getTypeSize(newParameters[newParameters.length - 1]);

            for (Type newParameter : newParameters) {
                loadLocalVariableToStack(newParameter, currentVariableIndex);
                currentVariableIndex -= getTypeSize(newParameter);
            }
        }
    }

    /**
     *
     * @param type
     * @return the size of the Type within the JVM
     */
    private int getTypeSize(Type type) {
        if (type.equals(Type.LONG_TYPE) || type.equals(Type.DOUBLE)) {
            return 2;
        } else if (type.equals(Type.VOID_TYPE)) {
            return 0;
        } else {
            return 1;
        }
    }
}
