package org.itstep.task07;

public class Complex {
    private double real;
    private double imaginary;

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
    public boolean equals(Complex second){
        if (this.imaginary==second.imaginary && this.real==second.real)return true ;
        return false ;
    }
    public Complex plus(Complex b){
        return new Complex(real + b.real, imaginary + b.imaginary);
    }
    public Complex minus(Complex b){
        return new Complex(real - b.real, imaginary - b.imaginary);
    }
    public Complex times(Complex b){
        return new Complex(real * b.real - imaginary * b.imaginary, real * b.imaginary + imaginary * b.real);
    }
    public Complex(){
        this.real = 0;
        this.imaginary = 0;
    }
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        if (this.real != 0 && this.imaginary > 0) return this.real + " + " + this.imaginary + "i";
        if (this.real != 0 && this.imaginary < 0) return this.real+ " - " + (-1 * this.imaginary) + "i";
        if (this.real != 0 && this.imaginary == 0) return this.real+"";
        if (this.imaginary != 0) return this.imaginary + "i";
        return "0.0";
    }
}
