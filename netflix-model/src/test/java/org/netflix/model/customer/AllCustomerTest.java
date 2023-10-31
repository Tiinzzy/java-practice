package org.netflix.model.customer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllCustomerTest {
    @Test
    void testCustomerDao() {
    CustomerDao newCustomer = new CustomerDao("Kamran", "1239754254", "kamran@email.com");
    newCustomer.saveToTable();
    assertEquals(newCustomer.getName(), "Kamran");
    assertEquals(newCustomer.getPhoneNo(), "1239754254");
    assertEquals(newCustomer.getEmail(), "kamran@email.com");

    CustomerDao searchCustomer = new CustomerDao(70);
    assertEquals(searchCustomer.getOid(), 70);

    List<CustomerDao> loadAllCustomers = CustomerDao.loadAll();
    assertFalse(loadAllCustomers.isEmpty());
    }

    @Test
    void testCustomerUpdate(){
        CustomerDao newCustomer = new CustomerDao("Tinzzy", "1239754254", "tinzzy@email.com");
        newCustomer.saveToTable();
        assertEquals(newCustomer.getName(), "Tinzzy");
        assertEquals(newCustomer.getPhoneNo(), "1239754254");
        assertEquals(newCustomer.getEmail(), "tinzzy@email.com");

        CustomerDao updateCustomer = new CustomerDao(newCustomer.getOid());
        updateCustomer.setEmail("tintin@email.com");
        updateCustomer.setName("Tintin");
        updateCustomer.setPhoneNo("5149871234");
        updateCustomer.saveToTable();
        updateCustomer.saveToTable();

        assertEquals(updateCustomer.getName(), "Tintin");
        assertEquals(updateCustomer.getPhoneNo(), "5149871234");
        assertEquals(updateCustomer.getEmail(), "tintin@email.com");
    }

    @Test
    void testDeleteCustomer(){
        CustomerDao newCustomer = new CustomerDao("Test1", "123456789", "test1@email.com");
        newCustomer.saveToTable();
        assertEquals(newCustomer.getName(), "Test1");
        assertEquals(newCustomer.getPhoneNo(), "123456789");
        assertEquals(newCustomer.getEmail(), "test1@email.com");

        long customerId = newCustomer.getOid();
        newCustomer.deleteACustomer(customerId);
        assertEquals(newCustomer.getOid(), customerId);
    }

}
