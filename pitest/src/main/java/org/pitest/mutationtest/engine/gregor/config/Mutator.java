/*
 * Copyright 2010 Henry Coles
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
package org.pitest.mutationtest.engine.gregor.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.pitest.functional.F;
import org.pitest.functional.FCollection;
import org.pitest.functional.prelude.Prelude;
import org.pitest.help.Help;
import org.pitest.help.PitHelpError;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;

import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorIADD;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorISUB;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorIMUL;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorIDIV;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorIREM;

import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorDADD;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorDSUB;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorDMUL;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorDDIV;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorDREM;

import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorFADD;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorFSUB;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorFMUL;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorFDIV;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorFREM;

import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorLADD;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorLSUB;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorLMUL;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorLDIV;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutatorLREM;

import org.pitest.mutationtest.engine.gregor.mutators.ArgumentPropagationMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConditionalsBoundaryMutator;
import org.pitest.mutationtest.engine.gregor.mutators.ConstructorCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.IncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InlineConstantMutator;
import org.pitest.mutationtest.engine.gregor.mutators.InvertNegsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.MathMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NegateConditionalsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.NonVoidMethodCallMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator;
import org.pitest.mutationtest.engine.gregor.mutators.RemoveConditionalMutator.Choice;
import org.pitest.mutationtest.engine.gregor.mutators.ReturnValsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.VoidMethodCallMutator;

import org.pitest.mutationtest.engine.gregor.mutators.augmented.RORMutatorIFEQ;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.RORMutatorIFGE;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.RORMutatorIFGT;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.RORMutatorIFLE;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.RORMutatorIFLT;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.RORMutatorIFNE;

import org.pitest.mutationtest.engine.gregor.mutators.augmented.OBBNORMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.OBBNXORMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.OBBNANDMutator;

=======
import org.pitest.mutationtest.engine.gregor.mutators.augmented.ABSIincMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.ABSLoadMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.ABSStoreMutator;

import org.pitest.mutationtest.engine.gregor.mutators.experimental.NakedReceiverMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.UOIReverseMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.UOIRemoveMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.UOIAddIncrementMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.UOIAddDecrementMutator;

public final class Mutator {

  private static final Map<String, Iterable<MethodMutatorFactory>> MUTATORS = new LinkedHashMap<String, Iterable<MethodMutatorFactory>>();

    // TODO Add a new line for each new mutator added
  static {

      add("ROR_IFEQ", RORMutatorIFEQ.ROR_IFEQ_MUTATOR);
      add("ROR_IFGE", RORMutatorIFGE.ROR_IFGE_MUTATOR);
      add("ROR_IFGT", RORMutatorIFGT.ROR_IFGT_MUTATOR);
      add("ROR_IFLE", RORMutatorIFLE.ROR_IFLE_MUTATOR);
      add("ROR_IFLT", RORMutatorIFLT.ROR_IFLT_MUTATOR);
      add("ROR_IFNE", RORMutatorIFNE.ROR_IFNE_MUTATOR);
      /*
      * New AOR mutators
      */
      add("AOR_MUTATOR_IADD", AORMutatorIADD.AOR_MUTATOR_IADD);
      add("AOR_MUTATOR_ISUB", AORMutatorISUB.AOR_MUTATOR_ISUB);
      add("AOR_MUTATOR_IMUL", AORMutatorIMUL.AOR_MUTATOR_IMUL);
      add("AOR_MUTATOR_IDIV", AORMutatorIDIV.AOR_MUTATOR_IDIV);
      add("AOR_MUTATOR_IREM", AORMutatorIREM.AOR_MUTATOR_IREM);
      
      add("AOR_MUTATOR_DADD", AORMutatorDADD.AOR_MUTATOR_DADD);
      add("AOR_MUTATOR_DSUB", AORMutatorDSUB.AOR_MUTATOR_DSUB);
      add("AOR_MUTATOR_DMUL", AORMutatorDMUL.AOR_MUTATOR_DMUL);
      add("AOR_MUTATOR_DDIV", AORMutatorDDIV.AOR_MUTATOR_DDIV);
      add("AOR_MUTATOR_DREM", AORMutatorDREM.AOR_MUTATOR_DREM);
      
      add("AOR_MUTATOR_FADD", AORMutatorFADD.AOR_MUTATOR_FADD);
      add("AOR_MUTATOR_FSUB", AORMutatorFSUB.AOR_MUTATOR_FSUB);
      add("AOR_MUTATOR_FMUL", AORMutatorFMUL.AOR_MUTATOR_FMUL);
      add("AOR_MUTATOR_FDIV", AORMutatorFDIV.AOR_MUTATOR_FDIV);
      add("AOR_MUTATOR_FREM", AORMutatorFREM.AOR_MUTATOR_FREM);
      
      add("AOR_MUTATOR_LADD", AORMutatorLADD.AOR_MUTATOR_LADD);
      add("AOR_MUTATOR_LSUB", AORMutatorLSUB.AOR_MUTATOR_LSUB);
      add("AOR_MUTATOR_LMUL", AORMutatorLMUL.AOR_MUTATOR_LMUL);
      add("AOR_MUTATOR_LDIV", AORMutatorLDIV.AOR_MUTATOR_LDIV);
      add("AOR_MUTATOR_LREM", AORMutatorLREM.AOR_MUTATOR_LREM);
    
      /*
      * UOI Mutators - Mutate ++ and -- unuary operators.
      */
      
      // TODO Add UOI mutators which add increments/decrements to variables without unary operators
      add("UOI_REVERSE", UOIReverseMutator.UOI_REVERSE_MUTATOR);
      add("UOI_REMOVE", UOIRemoveMutator.UOI_REMOVE_MUTATOR);
      add("UOI_ADD_INCREMENT", UOIAddIncrementMutator.UOI_ADD_INCREMENT_MUTATOR);
      add("UOI_ADD_DECREMENT", UOIAddDecrementMutator.UOI_ADD_DECREMENT_MUTATOR);

      
    /*
    * OBBN Mutators which mutates logical operators
    */
    
    add("OBBN_OR", OBBNORMutator.OBBN_OR_MUTATOR);
    add("OBBN_XOR", OBBNXORMutator.OBBN_XOR_MUTATOR);
    add("OBBN_AND", OBBNANDMutator.OBBN_AND_MUTATOR);
    
      /*
      * Set of ABS Mutators. Captures LOAD instructions, STORE instructions,
      * and IINC instructions (which would bypass both LOAD and STORE instructions)
      */
      add("ABS_LOAD", ABSLoadMutator.ABS_LOAD_MUTATOR);
      add("ABS_STORE", ABSStoreMutator.ABS_STORE_MUTATOR);
      add("ABS_IINC", ABSIincMutator.ABS_IINC_MUTATOR);

      
    /**
     * Default mutator that inverts the negation of integer and floating point
     * numbers.
     */
    add("INVERT_NEGS", InvertNegsMutator.INVERT_NEGS_MUTATOR);

    /**
     * Default mutator that mutates the return values of methods.
     */
    add("RETURN_VALS", ReturnValsMutator.RETURN_VALS_MUTATOR);

    /**
     * Optional mutator that mutates integer and floating point inline
     * constants.
     */
    add("INLINE_CONSTS", new InlineConstantMutator());

    /**
     * Default mutator that mutates binary arithmetic operations.
     */
    add("MATH", MathMutator.MATH_MUTATOR);

    /**
     * Default mutator that removes method calls to void methods.
     *
     */
    add("VOID_METHOD_CALLS", VoidMethodCallMutator.VOID_METHOD_CALL_MUTATOR);

    /**
     * Default mutator that negates conditionals.
     */
    add("NEGATE_CONDITIONALS",
        NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR);

    /**
     * Default mutator that replaces the relational operators with their
     * boundary counterpart.
     */
    add("CONDITIONALS_BOUNDARY",
        ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR);

    /**
     * Default mutator that mutates increments, decrements and assignment
     * increments and decrements of local variables.
     */
    add("INCREMENTS", IncrementsMutator.INCREMENTS_MUTATOR);

    /**
     * Optional mutator that removes local variable increments.
     */

    add("REMOVE_INCREMENTS", RemoveIncrementsMutator.REMOVE_INCREMENTS_MUTATOR);

    /**
     * Optional mutator that removes method calls to non void methods.
     */
    add("NON_VOID_METHOD_CALLS",
        NonVoidMethodCallMutator.NON_VOID_METHOD_CALL_MUTATOR);

    /**
     * Optional mutator that replaces constructor calls with null values.
     */
    add("CONSTRUCTOR_CALLS", ConstructorCallMutator.CONSTRUCTOR_CALL_MUTATOR);

    /**
     * Removes conditional statements so that guarded statements always execute
     * The EQUAL version ignores LT,LE,GT,GE, which is the default behavior,
     * ORDER version mutates only those.
     */

    add("REMOVE_CONDITIONALS_EQ_IF", new RemoveConditionalMutator(Choice.EQUAL,
        true));
    add("REMOVE_CONDITIONALS_EQ_ELSE", new RemoveConditionalMutator(
        Choice.EQUAL, false));
    add("REMOVE_CONDITIONALS_ORD_IF", new RemoveConditionalMutator(
        Choice.ORDER, true));
    add("REMOVE_CONDITIONALS_ORD_ELSE", new RemoveConditionalMutator(
        Choice.ORDER, false));
    addGroup("REMOVE_CONDITIONALS", RemoveConditionalMutator.makeMutators());

    /**
     * Experimental mutator that removed assignments to member variables.
     */
    add("EXPERIMENTAL_MEMBER_VARIABLE",
        new org.pitest.mutationtest.engine.gregor.mutators.experimental.MemberVariableMutator());

    /**
     * Experimental mutator that swaps labels in switch statements
     */
    add("EXPERIMENTAL_SWITCH",
        new org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator());

    /**
     * Experimental mutator that replaces method call with one of its parameters
     * of matching type
     */
    add("EXPERIMENTAL_ARGUMENT_PROPAGATION",
        ArgumentPropagationMutator.ARGUMENT_PROPAGATION_MUTATOR);

    /**
     * Experimental mutator that replaces method call with this
     */
    add("EXPERIMENTAL_NAKED_RECEIVER", NakedReceiverMutator.NAKED_RECEIVER);

    addGroup("REMOVE_SWITCH", RemoveSwitchMutator.makeMutators());
    addGroup("DEFAULTS", defaults());
    addGroup("STRONGER", stronger());
    addGroup("ALL", all());
    addGroup("ROR", ror());

    
    // New groups added for mutators in the engine.gregor.mutators.augmented package
    addGroup("AOR_I", aorMutatorInteger());
    addGroup("AOR_D", aorMutatorDouble());
    addGroup("AOR_F", aorMutatorFloat());
    addGroup("AOR_L", aorMutatorLong());
    addGroup("AOR", aorMutator());
    addGroup("UOI", uoi());    
    addGroup("OBBN", obbn());
    addGroup("ABS", abs());

  }

  public static Collection<MethodMutatorFactory> all() {
    return fromStrings(MUTATORS.keySet());
  }

  private static Collection<MethodMutatorFactory> stronger() {
    return combine(
        defaults(),
        group(new RemoveConditionalMutator(Choice.EQUAL, false),
            new SwitchMutator()));
  }

  private static Collection<MethodMutatorFactory> combine(
      Collection<MethodMutatorFactory> a, Collection<MethodMutatorFactory> b) {
    List<MethodMutatorFactory> l = new ArrayList<MethodMutatorFactory>(a);
    l.addAll(b);
    return l;
  }

  /**
   * Default set of mutators - designed to provide balance between strength and
   * performance
   */
  public static Collection<MethodMutatorFactory> defaults() {
    return group(InvertNegsMutator.INVERT_NEGS_MUTATOR,
        ReturnValsMutator.RETURN_VALS_MUTATOR, MathMutator.MATH_MUTATOR,
        VoidMethodCallMutator.VOID_METHOD_CALL_MUTATOR,
        NegateConditionalsMutator.NEGATE_CONDITIONALS_MUTATOR,
        ConditionalsBoundaryMutator.CONDITIONALS_BOUNDARY_MUTATOR,
        IncrementsMutator.INCREMENTS_MUTATOR);
  }

  public static Collection<MethodMutatorFactory> ror() {
    return group(RORMutatorIFEQ.ROR_IFEQ_MUTATOR,
            RORMutatorIFGE.ROR_IFGE_MUTATOR,
            RORMutatorIFGT.ROR_IFGT_MUTATOR,
            RORMutatorIFLE.ROR_IFLE_MUTATOR,
            RORMutatorIFLT.ROR_IFLT_MUTATOR,
            RORMutatorIFNE.ROR_IFNE_MUTATOR);

  /**
   * Integer-based sub-mutators for the AOR parent mutator
   */
  public static Collection<MethodMutatorFactory> aorMutatorInteger() {
    return group(AORMutatorIADD.AOR_MUTATOR_IADD, AORMutatorISUB.AOR_MUTATOR_ISUB,
            AORMutatorIMUL.AOR_MUTATOR_IMUL, AORMutatorIDIV.AOR_MUTATOR_IDIV, 
            AORMutatorIREM.AOR_MUTATOR_IREM);
  }
  
  public static Collection<MethodMutatorFactory> aorMutatorDouble() {
    return group(AORMutatorDADD.AOR_MUTATOR_DADD, AORMutatorDSUB.AOR_MUTATOR_DSUB,
            AORMutatorDMUL.AOR_MUTATOR_DMUL, AORMutatorDDIV.AOR_MUTATOR_DDIV, 
            AORMutatorDREM.AOR_MUTATOR_DREM);
  }
  
    public static Collection<MethodMutatorFactory> aorMutatorFloat() {
    return group(AORMutatorFADD.AOR_MUTATOR_FADD, AORMutatorFSUB.AOR_MUTATOR_FSUB,
            AORMutatorFMUL.AOR_MUTATOR_FMUL, AORMutatorFDIV.AOR_MUTATOR_FDIV, 
            AORMutatorFREM.AOR_MUTATOR_FREM);
    }
    
      public static Collection<MethodMutatorFactory> aorMutatorLong() {
    return group(AORMutatorLADD.AOR_MUTATOR_LADD, AORMutatorLSUB.AOR_MUTATOR_LSUB,
            AORMutatorLMUL.AOR_MUTATOR_LMUL, AORMutatorLDIV.AOR_MUTATOR_LDIV, 
            AORMutatorLREM.AOR_MUTATOR_LREM);
  }
      
    private static Collection<MethodMutatorFactory> aorMutator() {
    return combine(aorMutatorInteger(),
                combine(aorMutatorDouble(),
                        combine(aorMutatorFloat(),
                                aorMutatorLong())));
  }

  public static Collection<MethodMutatorFactory> uoi() {
    return group(UOIReverseMutator.UOI_REVERSE_MUTATOR, UOIRemoveMutator.UOI_REMOVE_MUTATOR,
            UOIAddIncrementMutator.UOI_ADD_INCREMENT_MUTATOR, UOIAddDecrementMutator.UOI_ADD_DECREMENT_MUTATOR);
  }

  public static Collection<MethodMutatorFactory> obbn() {
    return group(OBBNORMutator.OBBN_OR_MUTATOR,
            OBBNANDMutator.OBBN_AND_MUTATOR,
            OBBNXORMutator.OBBN_XOR_MUTATOR);

  /*
  * Group for ABS Mutators
  */
  public static Collection<MethodMutatorFactory> abs() {
    return group(ABSLoadMutator.ABS_LOAD_MUTATOR,
            ABSStoreMutator.ABS_STORE_MUTATOR,
            ABSIincMutator.ABS_IINC_MUTATOR);
  }

  private static Collection<MethodMutatorFactory> group(
      final MethodMutatorFactory... ms) {
    return Arrays.asList(ms);
  }

  public static Collection<MethodMutatorFactory> byName(final String name) {
    return FCollection.map(MUTATORS.get(name),
        Prelude.id(MethodMutatorFactory.class));
  }

  private static void add(final String key, final MethodMutatorFactory value) {
    MUTATORS.put(key, Collections.singleton(value));
  }

  private static void addGroup(final String key,
      final Iterable<MethodMutatorFactory> value) {
    MUTATORS.put(key, value);
  }

  public static Collection<MethodMutatorFactory> fromStrings(
      final Collection<String> names) {
    final Set<MethodMutatorFactory> unique = new TreeSet<MethodMutatorFactory>(
        compareId());

    FCollection.flatMapTo(names, fromString(), unique);
    return unique;
  }

  private static Comparator<? super MethodMutatorFactory> compareId() {
    return new Comparator<MethodMutatorFactory>() {
      @Override
      public int compare(final MethodMutatorFactory o1,
          final MethodMutatorFactory o2) {
        return o1.getGloballyUniqueId().compareTo(o2.getGloballyUniqueId());
      }
    };
  }

  private static F<String, Iterable<MethodMutatorFactory>> fromString() {
    return new F<String, Iterable<MethodMutatorFactory>>() {
      @Override
      public Iterable<MethodMutatorFactory> apply(final String a) {
        Iterable<MethodMutatorFactory> i = MUTATORS.get(a);
        if (i == null) {
          throw new PitHelpError(Help.UNKNOWN_MUTATOR, a);
        }
        return i;
      }
    };
  }
}
