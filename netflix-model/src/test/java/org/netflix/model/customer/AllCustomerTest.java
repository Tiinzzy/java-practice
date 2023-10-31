package org.netflix.model.customer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AllCustomerTest {
    @Test
    void testEpisodeDao() {
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

}
