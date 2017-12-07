# Pitest-Augmentation-Project
Group Members - Samuel Benton, David Orozco, Indhu Pathmanathan

## Section 1 - Project Summary
This project adds an additional seven mutators to the [Pitest Mutation Testing Tool](https://github.com/hcoles/pitest)

## Section 2 - How to Execute on Maven Projects
1. Download source code
2. Build/Compile Pitest project<br />
  a. <i>At this point, the project should automatically copy the Pitest jar into the appropriate Maven folder. If so, skip step 3.</i>
3. Copy the Pitest jar (<b>target\pitest-1.2.5-SNAPSHOT.jar</b>) into the Maven Pitest repository folder (<b>C:\Users\\[User Account Name]\\.m2\repository\org\pitest\pitest\1.2.5-SNAPSHOT\\</b>)
4. Add Pitest to the build/plugin section in pom.xml file of the project<br />
a. See <b>Section 3 - Pitest Maven Code Snippet</b> for template code to copy/paste
5. View <i>Section 4 - New Pitest Mutators</i> for complete description of the available mutators
6. Execute Pitest's "mutationCoverage" Maven goal within the project in question
7. View Pitest's results (located in the project's <b>target\pit-report\\</b>)

## Section 3 - Pitest Mavean Code Snippet

```xml
<plugin>
  <groupId>org.pitest</groupId>
  <artifactId>pitest-maven</artifactId>
  <version>1.2.5-SNAPSHOT</version>
  <configuration>
      <mutators>
        <mutator>DEFAULTS</mutator>
        <mutator>AUGMENTED</mutator>
        <mutator>EXPERIMENTAL</mutator>
        <mutator>[other mutators as needed...]</mutator>
      </mutators>
  </configuration>
  </plugin>
```

## Section 4 - New Pitest Mutators
