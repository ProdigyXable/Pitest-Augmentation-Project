# Pitest-Augmentation-Project
Group Members - Samuel Benton, David Orozco, Indhu Pathmanathan

## Section 1 - Project Summary
This project adds an additional seven mutators to the [Pitest Mutation Testing Tool](https://github.com/hcoles/pitest)

## Section 2 - How to Execute on Maven Projects
1. Download source code
2. If not already built, build entire Pitest-master project
3. Build/Compile Pitest project<br />
  a. <i>At this point, the project should automatically copy the Pitest jar into the appropriate Maven folder. If so, skip step 3.</i>
4. Copy the Pitest jar (<b>target\pitest-1.2.5-SNAPSHOT.jar</b>) into the Maven Pitest repository folder (<b>C:\Users\\[User Account Name]\\.m2\repository\org\pitest\pitest\1.2.5-SNAPSHOT\\</b>)
5. Add Pitest to the build/plugin section in pom.xml file of the project<br />
a. See <b>Section 3 - Pitest Maven Code Snippet</b> for template code to copy/paste
6. View <i>Section 4 - New Pitest Mutators</i> for complete description of the available mutators
7. Execute Pitest's "mutationCoverage" Maven goal within the project in question
8. View Pitest's results (located in the project's <b>target\pit-report\\</b>)

## Section 3 - Pitest Mavan Code Snippet

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
        <mutator>[other mutators_code as needed...]</mutator>
      </mutators>
  </configuration>
  </plugin>
```

## Section 4 - New Pitest Mutators (Mutator_Code)

* AUGMENTED - Adds all newly implemented mutators at once
--------------------------------------------------------
* ABS - Adds ABS Mutator
* AOD - Adds AOD Mutator
* AOR - Adds AOR Mutator
* CRCR - Adds CRCR Mutator
* OBBN - Adds OBBN Mutator
* ROR - Adds ROR Mutator
* UOI - Adds UOI Mutator
