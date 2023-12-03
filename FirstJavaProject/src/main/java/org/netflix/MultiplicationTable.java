package org.netflix;

public class MultiplicationTable {
    private int num = 0;

    public MultiplicationTable(int num) {
        this.num = num;
    }

    public void multiplyTable() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%d * %d = %d", this.num, i, this.num * i).println();
        }
    }
}
