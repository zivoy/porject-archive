package com.zivoy.module3;

public class Main {
    public static void main(String[] args) {
        BracketConfirmer brackets = new BracketConfirmer();
        System.out.println(brackets.confirm("as{asd"));
        System.out.println(brackets.confirm("{[(])}"));
        System.out.println(brackets.confirm("asdas(asdcsc)wd"));
        System.out.println(brackets.confirm("as()"));
    }
}
