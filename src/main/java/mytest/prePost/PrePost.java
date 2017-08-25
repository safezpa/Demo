package mytest.prePost;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrePost{


    {
        System.out.println("block");
    }
    @PostConstruct
    private void postconstruct(){

        System.out.println("in postconstruct");
    }

    PrePost(){
        System.out.println("in construct");
    }

    @PreDestroy
    private void predestroy(){

        System.out.println("in predestroy");
    }


    public static void main(String[] args) {
        PrePost   pp=new PrePost();
        pp=null;
        System.out.println("main");
    }
}
