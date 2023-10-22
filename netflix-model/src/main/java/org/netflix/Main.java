package org.netflix;

import org.netflix.model.customer.CustomerDao;
import org.netflix.model.genre.GenreDao;

import java.util.List;


public class Main {
    private void testGenre() {
        List<GenreDao> genres = GenreDao.loadAll();
        for (GenreDao gnr : genres) {
            System.out.println(gnr.getOid() + " - " + gnr.getDescription());
        }
        GenreDao g = new GenreDao(2);
        System.out.println(g.getDescription());
    }

    private void testCustomer() {
        List<CustomerDao> customers = CustomerDao.loadAll();
        for (CustomerDao cs : customers) {
            System.out.printf("Oid: %d, Name: %s, PhoneNo: %s, Email: %s%n", cs.getOid(), cs.getName(), cs.getPhoneNo(), cs.getEmail());
        }

        CustomerDao cs = new CustomerDao(3);
        System.out.printf("\n\nOid: %d, Name: %s, PhoneNo: %s, Email: %s%n", cs.getOid(), cs.getName(), cs.getPhoneNo(), cs.getEmail());
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.testGenre();

        System.out.println();

        m.testCustomer();
    }
}