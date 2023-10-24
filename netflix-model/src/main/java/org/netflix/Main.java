package org.netflix;

import org.netflix.model.customer.CustomerDao;
import org.netflix.model.genre.GenreDao;
import org.netflix.model.movies.MovieDao;

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

    private void testMovie() {
        List<MovieDao> movies = MovieDao.loadAll();
        for (MovieDao md : movies) {
            System.out.printf("Oid: %d, Title: %s, Release Date: %s, Rating: %s%n", md.getOid(), md.getMovieTitle(), md.getReleaseDate(), md.getRating());
        }

        MovieDao md = new MovieDao(5);
        System.out.printf("Oid: %d, Title: %s, Release Date: %s, Rating: %s%n", md.getOid(), md.getMovieTitle(), md.getReleaseDate(), md.getRating());
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.testGenre();

        System.out.println();

        m.testCustomer();

        System.out.println();

        m.testMovie();
   }
}