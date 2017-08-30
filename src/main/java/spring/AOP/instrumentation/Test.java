package spring.AOP.instrumentation;

import spring.AOP.Business;

public class Test {
    public static void main(String[] args) {
        new Business().doSomeThing();
        new Business().doSomeThing2();
    }

}
