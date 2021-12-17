package org.itstep.task04;

public class Fraction {
    private int numerator;
    private int denominator;

    public int getDenominator() {

        return denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    @Override
    public String toString() {
        return numerator +
                "/" + denominator;
    }
    public Fraction (){
        this.denominator = 1;
        this.numerator =1 ;
    }
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public Fraction addition(Fraction second){
        Fraction temp = new Fraction();
        temp.denominator = nok(this.denominator, second.denominator);
        temp.numerator =
                this.numerator * ( temp.denominator / this.denominator) +
                        second.numerator * ( temp.denominator / second.denominator);
        if (temp.denominator == temp.numerator)
        {
            temp.denominator =1;
            temp.numerator=1;
        }
        return temp;
    }

    public Fraction subtraction(Fraction second){
        Fraction temp = new Fraction();
        temp.denominator = nok(this.denominator, second.denominator);
        temp.numerator =
                this.numerator * ( temp.denominator / this.denominator) -
                        second.numerator * ( temp.denominator / second.denominator);
        if (temp.denominator == temp.numerator)
        {
            temp.denominator =1;
            temp.numerator=1;
        }
        return temp;
    }

    public Fraction multiplication(Fraction second){
        Fraction temp = new Fraction();
        temp.denominator = this.denominator* second.denominator;
        temp.numerator =  this.numerator*second.numerator;
        return temp;
    }
    public Fraction division(Fraction second){
        Fraction temp = new Fraction();
        temp.denominator = this.denominator* second.numerator;
        temp.numerator =  this.numerator*second.denominator;
        int simplify = simplify(temp.denominator,  temp.numerator);
        temp.numerator /=simplify;
        temp.denominator /=simplify;
        return temp;
    }
    // helper function
    private int nok(int a, int b) {
        return a * b / nod(a, b);
    }
    private int nod(int a, int b) {
        if (b == 0) {
            return a;
        }
        return nod(b, a % b);
    }
    public int simplify(int a, int b) {
        long limit = Math.min(a, b);
        int temp = 1;
        for (int i = 2; i <= limit; i++) {
            if (a % i == 0 && b % i == 0) {
                temp = i;
            }
        }
       return temp;
    }
}
