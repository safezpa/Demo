package spring.AOP.AspectJ_AOP;

import org.springframework.stereotype.Component;

/**
 *功能  人的实现类
 *作者 林炳文（ling20081005@126.com 博客：http://blog.csdn.net/evankaka）
 *时间 2015.4.27
 */
@Component
public class BabyPerson implements Person{

    @Override
    public void eatBreakfast() {
        System.out.println("小Baby正在吃早餐");
    }

    @Override
    public void eatLunch() {
        System.out.println("小Baby正在吃午餐");
    }

    @Override
    public void eatSupper() {
        System.out.println("小Baby正在吃晚餐");
    }

    @Override
    public String drink(String name) {
        return "小Baby在喝："+name;
    }

}
