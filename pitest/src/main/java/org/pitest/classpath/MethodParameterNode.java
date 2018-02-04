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
package org.pitest.classpath;

import org.objectweb.asm.Type;

/**
 * 
 *
 * @author Sam Benton's PC
 */
public class MethodParameterNode {

    private final String methodName;
    private final String methodDescriptor;
    private final Type methodReturnType;
    private final Type[] methodParameters;
    private final String methodSignature;
    private final String ownerClass;

    public MethodParameterNode(String name, String descriptor, String parentClass, String signature) {
        this.methodName = name;

        this.methodDescriptor = descriptor;
        this.methodReturnType = Type.getReturnType(methodDescriptor);
        this.methodParameters = Type.getArgumentTypes(methodDescriptor);
        this.ownerClass = parentClass;
        this.methodSignature = signature;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    
    public MethodParameterNode(String name, String descriptor, String parentClass) {
        this.methodName = name;

        this.methodDescriptor = descriptor;
        this.methodReturnType = Type.getReturnType(methodDescriptor);
        this.methodParameters = Type.getArgumentTypes(methodDescriptor);
        this.ownerClass = parentClass;
        this.methodSignature = "No Method Signature";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof MethodParameterNode) {
            MethodParameterNode mpn = (MethodParameterNode) obj;
            return (this.methodName.equals(mpn.methodName)) && (this.methodDescriptor.equals(mpn.methodDescriptor));
        } else {
            return false;
        }
    }

    public String getName() {
        return this.methodName;
    }

    public String getDescriptor() {
        return this.methodDescriptor;
    }

    public Type[] getParameters() {
        return this.methodParameters;
    }

    public Type getReturnType() {
        return this.methodReturnType;
    }

    public String getSignature() {
        return this.methodSignature;
    }

    public String getOwnerClass() {
        return this.ownerClass;
    }

    @Override
    public String toString() {
        return this.methodName + ":" + this.methodDescriptor;
    }
}
