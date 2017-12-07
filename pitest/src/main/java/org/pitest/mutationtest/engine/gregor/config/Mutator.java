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

import org.pitest.mutationtest.engine.gregor.mutators.augmented.ABSMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AODMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.AORMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.CRCRMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.OBBNMutator;
import org.pitest.mutationtest.engine.gregor.mutators.augmented.RORMutator;

import org.pitest.mutationtest.engine.gregor.mutators.experimental.NakedReceiverMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveIncrementsMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.RemoveSwitchMutator;
import org.pitest.mutationtest.engine.gregor.mutators.experimental.SwitchMutator;

public final class Mutator {

    private static final Map<String, Iterable<MethodMutatorFactory>> MUTATORS = new LinkedHashMap<String, Iterable<MethodMutatorFactory>>();

    static {

        add("ROR_IFEQ", new RORMutator(RORMutator.MutantType.IFEQ));
        add("ROR_IFGE", new RORMutator(RORMutator.MutantType.IFGE));
        add("ROR_IFGT", new RORMutator(RORMutator.MutantType.IFGT));
        add("ROR_IFLE", new RORMutator(RORMutator.MutantType.IFLE));
        add("ROR_IFLT", new RORMutator(RORMutator.MutantType.IFLT));
        add("ROR_IFNE", new RORMutator(RORMutator.MutantType.IFNE));
      
        /*
         * OBBN Mutators which mutates logical operators
         */
        add("OBBN_OR", new OBBNMutator(OBBNMutator.MutantType.OR));
        add("OBBN_XOR", new OBBNMutator(OBBNMutator.MutantType.XOR));
        add("OBBN_AND", new OBBNMutator(OBBNMutator.MutantType.AND));
      
        /*
         * Group of CRCR Mutators - These mutators negate a constant,
         * replace a constant with one,
         * replace a constant with zero
         * increment a constant,
         * or decrement a constant
         */
        add("CRCR_NEGATE", new CRCRMutator(CRCRMutator.MutantType.NEGATE));
        add("CRCR_REPLACE_ONE", new CRCRMutator(CRCRMutator.MutantType.REPLACE_ONE));
        add("CRCR_REPLACE_ZERO", new CRCRMutator(CRCRMutator.MutantType.REPLACE_ZERO));
        add("CRCR_ADD_ONE", new CRCRMutator(CRCRMutator.MutantType.ADD));
        add("CRCR_SUB_ONE", new CRCRMutator(CRCRMutator.MutantType.SUB));

        /*
         * AOR mutators - These mutators swap the given operand with
         * all other operands of the same type
         */
        add("AOR_MUTATOR_IADD", new AORMutator(AORMutator.MutantType.IADD));
        add("AOR_MUTATOR_ISUB", new AORMutator(AORMutator.MutantType.ISUB));
        add("AOR_MUTATOR_IMUL", new AORMutator(AORMutator.MutantType.IMUL));
        add("AOR_MUTATOR_IDIV", new AORMutator(AORMutator.MutantType.IDIV));
        add("AOR_MUTATOR_IREM", new AORMutator(AORMutator.MutantType.IREM));

        add("AOR_MUTATOR_DADD", new AORMutator(AORMutator.MutantType.DADD));
        add("AOR_MUTATOR_DSUB", new AORMutator(AORMutator.MutantType.DSUB));
        add("AOR_MUTATOR_DMUL", new AORMutator(AORMutator.MutantType.DMUL));
        add("AOR_MUTATOR_DDIV", new AORMutator(AORMutator.MutantType.DDIV));
        add("AOR_MUTATOR_DREM", new AORMutator(AORMutator.MutantType.DREM));

        add("AOR_MUTATOR_FADD", new AORMutator(AORMutator.MutantType.DADD));
        add("AOR_MUTATOR_FSUB", new AORMutator(AORMutator.MutantType.FSUB));
        add("AOR_MUTATOR_FMUL", new AORMutator(AORMutator.MutantType.FMUL));
        add("AOR_MUTATOR_FDIV", new AORMutator(AORMutator.MutantType.FDIV));
        add("AOR_MUTATOR_FREM", new AORMutator(AORMutator.MutantType.FREM));

        add("AOR_MUTATOR_LADD", new AORMutator(AORMutator.MutantType.LADD));
        add("AOR_MUTATOR_LSUB", new AORMutator(AORMutator.MutantType.LSUB));
        add("AOR_MUTATOR_LMUL", new AORMutator(AORMutator.MutantType.LMUL));
        add("AOR_MUTATOR_LDIV", new AORMutator(AORMutator.MutantType.LDIV));
        add("AOR_MUTATOR_LREM", new AORMutator(AORMutator.MutantType.LREM));
      
         /**
         * Set of AOD Mutators
         */

        add("AOD_FIRST", new AODMutator(AODMutator.MutantType.FIRST));
        add("AOD_LAST", new AODMutator(AODMutator.MutantType.LAST));

         /**
         * Set of ABS Mutators. Intercepts LOAD instructions, STORE instructions
         * and negates the value before/after the instruction respectively
         */
        add("ABS_LOAD", new ABSMutator(ABSMutator.MutantType.LOAD));
        add("ABS_STORE", new ABSMutator(ABSMutator.MutantType.STORE));

        /**
         * Default mutator that inverts the negation of integer and floating
         * point numbers.
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
         * Removes conditional statements so that guarded statements always
         * execute The EQUAL version ignores LT,LE,GT,GE, which is the default
         * behavior, ORDER version mutates only those.
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
         * Experimental mutator that replaces method call with one of its
         * parameters of matching type
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
        

        // New groups added for mutators in the engine.gregor.mutators.augmented package
        addGroup("AOR_I", aorMutatorInteger());
        addGroup("AOR_D", aorMutatorDouble());
        addGroup("AOR_F", aorMutatorFloat());
        addGroup("AOR_L", aorMutatorLong());
        addGroup("AOR", aorMutator());

        addGroup("AOD", aod());
        addGroup("ABS", abs());
      
        addGroup("CRCR", crcr());
      
      addGroup("OBBN", obbn());
      addGroup("ROR", ror());
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
     * Default set of mutators - designed to provide balance between strength
     * and performance
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
        return group(new RORMutator(RORMutator.MutantType.IFEQ),
                new RORMutator(RORMutator.MutantType.IFGT),
                new RORMutator(RORMutator.MutantType.IFGE),
                new RORMutator(RORMutator.MutantType.IFLT),
                new RORMutator(RORMutator.MutantType.IFLE),
                new RORMutator(RORMutator.MutantType.IFNE));
    }
    
  
    public static Collection<MethodMutatorFactory> obbn() {
        return group(new OBBNMutator(OBBNMutator.MutantType.OR),
                new OBBNMutator(OBBNMutator.MutantType.AND),
                new OBBNMutator(OBBNMutator.MutantType.XOR));
    }
    public static Collection<MethodMutatorFactory> crcr() {
        return group(new CRCRMutator(CRCRMutator.MutantType.NEGATE),
                new CRCRMutator(CRCRMutator.MutantType.REPLACE_ONE),
                new CRCRMutator(CRCRMutator.MutantType.REPLACE_ZERO),
                new CRCRMutator(CRCRMutator.MutantType.ADD),
                new CRCRMutator(CRCRMutator.MutantType.SUB));
    }
  
    /**
     * Integer-based sub-mutators for the AOR parent mutator
     */
    public static Collection<MethodMutatorFactory> aorMutatorInteger() {
        return group(new AORMutator(AORMutator.MutantType.IADD),
                new AORMutator(AORMutator.MutantType.ISUB),
                new AORMutator(AORMutator.MutantType.IMUL),
                new AORMutator(AORMutator.MutantType.IDIV),
                new AORMutator(AORMutator.MutantType.IREM));
    }

    public static Collection<MethodMutatorFactory> aorMutatorDouble() {
        return group(new AORMutator(AORMutator.MutantType.DADD),
                new AORMutator(AORMutator.MutantType.DSUB),
                new AORMutator(AORMutator.MutantType.DMUL),
                new AORMutator(AORMutator.MutantType.DDIV),
                new AORMutator(AORMutator.MutantType.DREM));
    }

    public static Collection<MethodMutatorFactory> aorMutatorFloat() {
        return group(new AORMutator(AORMutator.MutantType.FADD),
                new AORMutator(AORMutator.MutantType.FSUB),
                new AORMutator(AORMutator.MutantType.FMUL),
                new AORMutator(AORMutator.MutantType.FDIV),
                new AORMutator(AORMutator.MutantType.FREM));
    }

    public static Collection<MethodMutatorFactory> aorMutatorLong() {
        return group(new AORMutator(AORMutator.MutantType.LADD),
                new AORMutator(AORMutator.MutantType.LSUB),
                new AORMutator(AORMutator.MutantType.LMUL),
                new AORMutator(AORMutator.MutantType.LDIV),
                new AORMutator(AORMutator.MutantType.LREM));
    }

    private static Collection<MethodMutatorFactory> aorMutator() {
        return combine(aorMutatorInteger(),
                combine(aorMutatorDouble(),
                        combine(aorMutatorFloat(),
                                aorMutatorLong())));
    }
      
    public static Collection<MethodMutatorFactory> aod() {
        return group(new AODMutator(AODMutator.MutantType.FIRST),
                new AODMutator(AODMutator.MutantType.LAST)); 
    }
  
    /*
     * Group for ABS Mutators
     */
    public static Collection<MethodMutatorFactory> abs() {
        return group(new ABSMutator(ABSMutator.MutantType.LOAD),
                new ABSMutator(ABSMutator.MutantType.STORE));
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
