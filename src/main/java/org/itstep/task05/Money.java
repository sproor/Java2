package org.itstep.task05;

import org.itstep.task04.Fraction;

public class Money {
    private long hryvnia;
    private byte kopecks;

    public long getHryvnia() {
        return hryvnia;
    }

    public byte getKopecks() {
        return kopecks;
    }

    public void setHryvnia(long hryvnia) {
        this.hryvnia = hryvnia;
    }

    public void setKopecks(byte kopecks) {
        this.kopecks = kopecks;
    }
    public Money(){
        this.hryvnia =0 ;
        this.kopecks = 0 ;
    }

    public Money(long hryvnia) {
        this.hryvnia = hryvnia;
    }

    public Money(long hryvnia, byte kopecks) {
        this.hryvnia = hryvnia;
        this.kopecks = kopecks;
    }

    @Override
    public String toString() {
        return hryvnia +","+kopecks;
    }

    public Money addition(Money second){
        Money temp = new Money();
        temp.kopecks = (byte) (this.kopecks+second.kopecks);
        temp.hryvnia = this.hryvnia+ second.hryvnia;
        return temp;
    }
    public Money subtraction(Money second){
        Money temp = new Money();
        temp.kopecks = (byte) (this.kopecks-second.kopecks);
        temp.hryvnia = this.hryvnia- second.hryvnia;
        return temp;
    }
    public Money division(double second){
        Money temp = new Money();
        temp.hryvnia = (long) (this.hryvnia/second);
        temp.kopecks = (byte) (this.kopecks/second);
        return temp;
    }
    public Money multiply(double second){
        Money temp = new Money();
        temp.hryvnia = (long) (this.hryvnia*second);
        temp.kopecks = (byte) (this.kopecks*second);
        return temp;
    }
    public boolean equals(Money second){
        if (this.kopecks == second.kopecks && this.hryvnia == second.hryvnia) return true;
        return false;
    }
}
