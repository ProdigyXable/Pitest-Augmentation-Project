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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import org.objectweb.asm.Type;

/**
 *
 *
 * @author Sam Benton's PC
 */
public class MethodParameterNode implements Serializable {

    private final String methodName;
    private final String methodDescriptor;
    private final String methodSignature;
    private final String ownerClass;
    
    public static final String SERIAL_FILEPATH = "pitProjectMethodData.txt";

    public MethodParameterNode(String name, String descriptor, String parentClass, String signature) {
        this.methodName = name;

        this.methodDescriptor = descriptor;
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
        return Type.getArgumentTypes(methodDescriptor);
    }

    public Type getReturnType() {
        return Type.getReturnType(methodDescriptor);
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
    
        public static boolean serializeMethodParameters(ArrayList<MethodParameterNode> serializeObject, String serialFilename) {

        try {
            FileOutputStream fileStream = new FileOutputStream(serialFilename);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(serializeObject);

            objectStream.close();
            fileStream.close();

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    
    public static Collection<MethodParameterNode> deserializeMethodParameters(String serialFilename) {
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
