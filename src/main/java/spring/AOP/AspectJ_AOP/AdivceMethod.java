package spring.AOP.AspectJ_AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AdivceMethod {

    @Before("execution(* spring.AOP.AspectJ_AOP.BabyPerson.*(..))")
    // 匹配BabyPerson类所有的方法,注意*和com之间有个空格
    public void beforeEat() {
        System.out.println("-------------------这里是前置增强，吃饭之前先洗小手！--------------------");
    }

    @After("execution(* eatLunch(..))")
    // 匹配该工程下所有的eatLunch方法
    public void afterEat() {
        System.out.println("-------------------这里是后置增强，午饭吃完要睡午觉！--------------------");
    }

    @Around("execution(* spring.AOP.AspectJ_AOP.BabyPerson.eatSupper())")
    // 匹配该工程下BabyPerson的eatLunch方法
    public Object aroundEat(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("-------------------这里是环绕增强，吃晚饭前先玩一玩！-------------------");
        Object retVal = pjp.proceed();
        System.out.println("-------------------这里是环绕增强，晚饭吃完后要得睡觉了！-------------------");
        return retVal;
    }
    @AfterReturning(returning="rvt",pointcut="execution(* spring.AOP.AspectJ_AOP.BabyPerson.drink(..))")
    public void log(Object rvt) {
        System.out.println("-------------------这里是AfterReturning增强-------------------");
        System.out.println("获取小Baby正在喝的饮料"+rvt);
        System.out.println("记录每天喝的饮料容量");

    }

}
