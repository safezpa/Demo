package spring.AOP.AspectJ_AOP;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by safe on 2017-05-05.
 */

public class Test {

    public static void main(String[] args) {
        String path = "A:\\Project\\Demo\\src\\main\\resources\\beans.xml";
        ApplicationContext context = new FileSystemXmlApplicationContext
                (path);

        Person  person=(Person)context.getBean("babyPerson");

        person.eatBreakfast();
        System.out.println("===================================================");
        System.out.println();        System.out.println();
        person.eatLunch();
        System.out.println("===================================================");
        System.out.println();        System.out.println();
        person.eatSupper();
        System.out.println("===================================================");
        System.out.println();        System.out.println();
        person.drink("可乐");
        System.out.println("===================================================");



    }

}
