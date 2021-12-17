package org.itstep.task06;

import java.util.Arrays;

public class MainString {
    private char[] chars;
    private int length;

    public int length() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    public void  clean(){
        this.chars = new char[0];
    }
    public void  concat(MainString second){
        int length = this.chars.length+ second.length;
        char temp[] = new char[length];

        for (int i = 0 ;i < this.chars.length ; i++){
            //System.out.println("temp["+i+"]) = "+this.chars[i] );
            temp[i] = this.chars[i];
            //System.out.println(i);
        }
        for (int i = this.chars.length,n=0; i<length ; i++){
            temp[i] = second.chars[n];
           // System.out.println("temp["+i+"]) = "+second.chars[n] );
            n++;
        }
        this.chars = temp;
        this.length = length;
    }
    public int indexOf(char chr){
        for (int i =0 ; i<this.length;i++){
            if (chars[i]==chr) return i-1;
        }
        return -1;
    }
    public int indexOf(int chr){
        for (int i =0 ; i<this.length;i++){
            if (chars[i]==chr) return i-1;
        }
        return -1;
    }
    public MainString() {
        this.chars = new char[0];
        this.length = 0;
    }

    public MainString(char[] charSequence) {
        this.chars = charSequence;
        length = charSequence.length;
    }

    public MainString(char chars, int size) {
        this.length = size;
        this.chars = new char[size];
        for (int i=0; i< size;i++){
            this.chars[i] = chars;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(chars);
    }
}
