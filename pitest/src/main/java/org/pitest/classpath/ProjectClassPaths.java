/*
 * Copyright 2011 Henry Coles
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
package org.pitest.classpath;

import org.pitest.classinfo.ClassName;
import org.pitest.functional.FCollection;

public class ProjectClassPaths {

    private final ClassPath classPath;
    private final ClassFilter classFilter;
    private final PathFilter pathFilter;

    public ProjectClassPaths(final ClassPath classPath,
            final ClassFilter classFilter, final PathFilter pathFilter) {
        this.classPath = classPath;
        this.classFilter = classFilter;
        this.pathFilter = pathFilter;
    }

    public Iterable<ClassName> code() {
        Iterable<ClassName> codeFilePaths = FCollection.filter(
                this.classPath.getComponent(this.pathFilter.getCodeFilter())
                .findClasses(this.classFilter.getCode()),
                this.classFilter.getCode()).map(ClassName.stringToClassName());

        // Seemingly outputs the all files which Pitest mutates
        System.out.println("");
        System.out.println("************************************************************");
        
        for (ClassName cn : codeFilePaths) {
            System.out.println("Detected filepath(s) to core project code:\t" + cn.asJavaName());
        }

        System.out.println("************************************************************");
        System.out.println("");

        return codeFilePaths;
    }

    public Iterable<ClassName> test() {

        Iterable<ClassName> fileClassNames = FCollection.filter(
                this.classPath.getComponent(this.pathFilter.getTestFilter())
                .findClasses(this.classFilter.getTest()),
                this.classFilter.getTest()).map(ClassName.stringToClassName());

        /* Seemingly output unique names for the various classes found within the project filepaths supplied to pit
         System.out.println("******************************");

         for (ClassName cn : fileClassNames) {
         System.out.println("(Unique) Class file found:\t" + cn.asJavaName());
         }

         System.out.println("******************************");
         */
        return fileClassNames;
    }

    public ClassPath getClassPath() {
        return this.classPath;
    }

    public ClassFilter getFilter() {
        return this.classFilter;
    }

}
