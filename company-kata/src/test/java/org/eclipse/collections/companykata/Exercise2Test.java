/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.companykata;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class Exercise2Test extends CompanyDomainForKata
{
    /**
     * Set up a {@link Predicate} that tests to see if a {@link Customer}'s city is "London"
     */
    private static final Predicate<Customer> CUSTOMER_FROM_LONDON = customer -> "London".equals(customer.getCity());

    @Test
    public void customerFromLondonPredicate()
    {
        final String predicateClass = CUSTOMER_FROM_LONDON.getClass().getSimpleName();
        Assert.assertTrue(
                "Solution should use Predicates.attributeEquals() or a lambda but used " + predicateClass,
                "AttributePredicate".equals(predicateClass) || predicateClass.startsWith("Exercise2Test$$Lambda"));

        final Customer customerFromLondon = new Customer("test customer", "London");

        Assert.assertEquals(
                "Implement Customer.TO_CITY",
                "London",
                Customer.TO_CITY.valueOf(customerFromLondon));

        Assert.assertTrue(
                "CUSTOMER_FROM_LONDON should accept Customers where city is London",
                CUSTOMER_FROM_LONDON.accept(customerFromLondon));
    }

    /**
     * Do any customers come from London? Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void doAnyCustomersLiveInLondon()
    {
        final boolean anyCustomersFromLondon
            = this.company.getCustomers().anySatisfy(CUSTOMER_FROM_LONDON);
        Assert.assertTrue(anyCustomersFromLondon);
    }

    /**
     * Do all customers come from London? Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void doAllCustomersLiveInLondon()
    {
        final boolean allCustomersFromLondon = this.company.getCustomers().allSatisfy(CUSTOMER_FROM_LONDON);
        Assert.assertFalse(allCustomersFromLondon);
    }

    /**
     * How many customers come from London? Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void howManyCustomersLiveInLondon()
    {
        final int numberOfCustomerFromLondon = this.company.getCustomers().count(CUSTOMER_FROM_LONDON);
        Assert.assertEquals("Should be 2 London customers", 2, numberOfCustomerFromLondon);
    }

    /**
     * Which customers come from London? Get a collection of those which do. Use the Predicate {@link
     * #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getLondonCustomers()
    {
        final MutableList<Customer> customersFromLondon = this.company.getCustomers().select(CUSTOMER_FROM_LONDON);
        Verify.assertSize("Should be 2 London customers", 2, customersFromLondon);
    }

    /**
     * Which customers do not come from London? Get a collection of those which don't. Use the Predicate {@link
     * #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getCustomersWhoDontLiveInLondon()
    {
        final MutableList<Customer> customersNotFromLondon = this.company.getCustomers().reject(CUSTOMER_FROM_LONDON);
        Verify.assertSize("customers not from London", 1, customersNotFromLondon);
    }

    /**
     * Which customers come from London? Which customers do not come from London? Get a collection of each in a single pass.
     * Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getCustomersWhoDoAndDoNotLiveInLondon()
    {
        final PartitionMutableList<Customer> partition = this.company.getCustomers().partition(CUSTOMER_FROM_LONDON);
        final MutableList<Customer> customersFromLondon = partition.getSelected();
        final MutableList<Customer> customersNotFromLondon = partition.getRejected();
        Verify.assertSize("Should be 2 London customers", 2, customersFromLondon);
        Verify.assertSize("customers not from London", 1, customersNotFromLondon);
    }

    /**
     * Implement {@link Company#getCustomerNamed(String)} and get this test to pass.
     */
    @Test
    public void findMary()
    {
        final Customer mary = this.company.getCustomerNamed("Mary");
        Assert.assertEquals("customer's name should be Mary", "Mary", mary.getName());
    }

    /**
     * Implement {@link Company#getCustomerNamed(String)} and get this test to pass.
     */
    @Test
    public void findPete()
    {
        //customers.detect(customer -> name.equals(customer.getName()));
        final Customer pete = this.company.getCustomerNamed("Pete");
        Assert.assertNull(
                "Should be null as there is no customer called Pete",
                pete);
    }
}
