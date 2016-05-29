/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.petkata;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class Exercise2Test extends PetDomainForKata
{
    @Test
    public void doAnyPeopleHaveCats()
    {
        //replace null with a predicate which checks for PetType.CAT
        final Predicate<Person> predicate = person -> person.hasPet(PetType.CAT);
        Assert.assertTrue(this.people.anySatisfy(predicate));
    }

    @Test
    public void doAllPeopleHavePets()
    {
        final Predicate<Person> predicate = person -> person.isPetPerson();
        //replace with something that checks if all people have pets
        final boolean result = people.allSatisfy(predicate);
        Assert.assertFalse(result);
    }

    @Test
    public void howManyPeopleHaveCats()
    {
        final int count = people.count(person -> person.hasPet(PetType.CAT));
        Assert.assertEquals(2, count);
    }

    @Test
    public void findMarySmith()
    {
        final Person result = people.detect(
                person -> "Mary".equals(person.getFirstName()) && "Smith".equals(person.getLastName()));
        Assert.assertEquals("Mary", result.getFirstName());
        Assert.assertEquals("Smith", result.getLastName());
    }

    @Test
    public void getPeopleWithPets()
    {
        final MutableList<Person> petPeople = this.people.reject(person -> person.getPets().isEmpty()); //replace with only the pet owners
        Verify.assertSize(7, petPeople);
    }

    @Test
    public void getAllPetsOfAllPeople()
    {
        final Function<Person, Iterable<PetType>> function = person -> person.getPetTypes();
        // ???
        final MutableSet<PetType> petTypes = this.people.flatCollect(function).toSet();
        Assert.assertEquals(
                Sets.mutable.with(PetType.CAT, PetType.DOG, PetType.TURTLE, PetType.HAMSTER, PetType.BIRD, PetType.SNAKE),
                petTypes);
    }

    @Test
    public void getFirstNamesOfAllPeople()
    {
        final MutableList<String> firstNames = people.collect(person -> person.getFirstName());
        Assert.assertEquals(
                Lists.mutable.with("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John"),
                firstNames);
    }

    @Test
    public void doAnyPeopleHaveCatsRefactor()
    {
        final boolean peopleHaveCatsLambda = this.people.anySatisfy(person -> person.hasPet(PetType.CAT));
        Assert.assertTrue(peopleHaveCatsLambda);

        //use method reference, NOT lambdas, to solve the problem below
        final boolean peopleHaveCatsMethodRef = this.people.anySatisfyWith(Person::hasPet, PetType.CAT);
        Assert.assertTrue(peopleHaveCatsMethodRef);
    }

    @Test
    public void doAllPeopleHaveCatsRefactor()
    {
        final boolean peopleHaveCatsLambda = this.people.allSatisfy(person -> person.hasPet(PetType.CAT));
        Assert.assertFalse(peopleHaveCatsLambda);

        //use method reference, NOT lambdas, to solve the problem below
        final boolean peopleHaveCatsMethodRef = this.people.allSatisfyWith(Person::hasPet, PetType.CAT);
        Assert.assertFalse(peopleHaveCatsMethodRef);
    }

    @Test
    public void getPeopleWithCatsRefator()
    {
        //use method reference, NOT lambdas, to solve the problem below
        final MutableList<Person> peopleWithCatsMethodRef
            = this.people.selectWith(Person::hasPet, PetType.CAT);
        Verify.assertSize(2, peopleWithCatsMethodRef);
    }

    @Test
    public void getPeopleWithoutCatsRefactor()
    {
        //use method reference, NOT lambdas, to solve the problem below
        final MutableList<Person> peopleWithoutCatsMethodRef
            = this.people.rejectWith(Person::hasPet, PetType.CAT);
        Verify.assertSize(6, peopleWithoutCatsMethodRef);
    }
}
