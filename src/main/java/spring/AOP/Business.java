package spring.AOP;

public class Business implements IBusiness2,IBusiness {

    @Override
    public boolean doSomeThing() {
        System.out.println("执行业务逻辑");
        return true;
    }

    @Override
    public void doSomeThing2() {
        System.out.println("执行业务逻辑2");
    }

}

