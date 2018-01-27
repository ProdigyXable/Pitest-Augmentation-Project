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
 * @author Sam Benton's PC
 */
public class MethodParameterNode {

    private final String methodName;
    private final String methodDescriptor;
    private final Type methodReturnType;
    private final Type[] methodParameters;
    private final String methodSignature;

    public MethodParameterNode(String name, String descriptor, String signature) {
        this.methodName = name;

        this.methodDescriptor = descriptor;
        this.methodReturnType = Type.getReturnType(methodDescriptor);
        this.methodParameters = Type.getArgumentTypes(methodDescriptor);

        this.methodSignature = signature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodParameterNode) {

            MethodParameterNode mpn = (MethodParameterNode) obj;

            if ((this.methodName.equals(mpn.methodName)) && (this.methodDescriptor.equals(mpn.methodDescriptor))) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
