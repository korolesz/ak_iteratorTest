package Main;

public class Fibonacci {
    public static void main (String[] args){
        int a = 0;
        int b= 1;
        System.out.println("0. " + a);
        System.out.println("1. "+ b);
        for (int i =0; i <43; i++){
            int c = a + b;
            System.out.println((i+2) +". "+ c);
            a = b;
            b = c;
        }
    }
}

