package org.netflix.data_structures;

public class Salary implements Comparable<Salary> {

    private int salary;

    public Salary(int salary) {
        this.setSalary(salary);
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Salary o) {
        return -Integer.compare(this.salary, o.getSalary());
    }

    public String toString() {
        return String.valueOf(this.salary);
    }
}
