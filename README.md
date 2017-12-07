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

## Section 3 - Pitest Maven Code Snippet

```xml
<plugin>
  <groupId>org.pitest</groupId>
  <artifactId>pitest-maven</artifactId>
  <version>1.2.5-SNAPSHOT</version>
  <configuration>
      <mutators>
        <mutator>DEFAULTS</mutator>
        <mutator>EXPERIMENTAL</mutator>
        <mutator>ABS_STORE</mutator>
        <mutator>AOR</mutator>
        <mutator>[other mutators_code as needed... (shown in section 4)]</mutator>
      </mutators>
  </configuration>
  </plugin>
```

## Section 4 - New Pitest Mutators (Mutator_Code)
* <b><i>AUGMENTED - Adds all newly implemented mutators at once</i></b>
--------------------------------------------------------
* <b>ABS - Adds the ABS mutators listed below</b>
  * ABS_STORE - Inverts a variable's value immediately before the variable is stored
  * ABS_LOAD - Inverts a variable's value immediately after the variable is loaded
* <b>AOD - Adds the AOD mutators listed below</b>
  * AOD_FIRST - Removes the second operator and the instruction in an ADD, SUB, MUL, DIV, and REM instruction
  * AOD_LAST - Removes the first operator and the instruction in an ADD, SUB, MUL, DIV, and REM instruction
* <b>AOR - Adds the AOR mutators listed below</b>
  * AOR_I - Adds all the below integer-based arithmetic mutators
    * AOR_IADD - Changes the + operator into the -, *, /, and % operators for integers
    * AOR_ISUB - Changes the - operator into the +, *, /, and % operators for integers
    * AOR_IMUL - Changes the * operator into the +, -, /, and % operators for integers
    * AOR_IDIV - Changes the / operator into the +, -, *, and % operators for integers
    * AOR_IREM - Changes the % operator into the +, -, *, and / operators for integers
  * AOR_D - Adds all the below double-based arithmetic mutators
    * AOR_DADD - Changes the + operator into the -, *, /, and % operators for doubles
    * AOR_DSUB - Changes the - operator into the +, *, /, and % operators for doubles
    * AOR_DMUL - Changes the * operator into the +, -, /, and % operators for doubles
    * AOR_DDIV - Changes the / operator into the +, -, *, and % operators for doubles
    * AOR_dREM - Changes the % operator into the +, -, *, and / operators for doubles
  * AOR_F - Adds all the below float-based arithmetic mutators
    * AOR_FADD - Changes the + operator into the -, *, /, and % operators for floats
    * AOR_FSUB - Changes the - operator into the +, *, /, and % operators for floats
    * AOR_FMUL - Changes the * operator into the +, -, /, and % operators for floats
    * AOR_FDIV - Changes the / operator into the +, -, *, and % operators for floats
    * AOR_FREM - Changes the % operator into the +, -, *, and / operators for floats
  * AOR_L - Adds all the below long-based arithmetic mutators
    * AOR_LADD - Changes the + operator into the -, *, /, and % operators for longs
    * AOR_LSUB - Changes the - operator into the +, *, /, and % operators for longs
    * AOR_LMUL - Changes the * operator into the +, -, /, and % operators for longs
    * AOR_LDIV - Changes the / operator into the +, -, *, and % operators for longs
    * AOR_LREM - Changes the % operator into the +, -, *, and / operators for longs
* <b>CRCR - Adds the CRCR mutators listed below</b>
  * CRCR_ADD_ONE - Increments a constant by one; x -> x + 1
  * CRCR_SUB_ONE - Decrements a constant by one; x -> x - 1
  * CRCR_NEGATE - Negates a constant; x -> -x
  * CRCR_REPLACE_ONE - Replaces a constant by one, if not already one; x -> 1
  * CRCR_REPLACE_ZERO - Replaces a constant by zero, if not already zero; x -> 0
* <b>OBBN - Adds the OBBN mutators listed below</b>
  * OBBN_OR - Replaces the | boolean operator with the & and ^ boolean operators
  * OBBN_XOR - Replaces the ^ boolean operator with the & and | boolean operators
  * OBBN_AND - Replaces the & boolean operator with the | and ^ boolean operators
* <b>ROR - Adds the ROR mutators listed below</b>
  * ROR_IFEQ - Replaces the == operator with the >=, >, <=. <, and != operators
  * ROR_IFGE - Replaces the >= operator with the ==, >, <=. <, and != operators
  * ROR_IFGT - Replaces the > operator with the >=, ==, <=. <, and != operators
  * ROR_IFLE - Replaces the <= operator with the >=, >, ==. <, and != operators
  * ROR_IFLT - Replaces the < operator with the >=, >, <=. ==, and != operators
  * ROR_IFNE - Replaces the != operator with the >=, >, <=. <, and == operators
* <b>UOI - Adds the UOI mutators listed below</b>
  * UOI_REVERSE - Reverses a variable's unary increment; ++x -> --x
  * UOI_REMOVE - Removes a variable's unary increment; ++x -> x
  * UOI_INCREMENT - Increases a variable's unary increment; ++x -> ++++x
  * UOI_DECREMENT - Decreases a variable's unary increment; ++++x -> ++x
