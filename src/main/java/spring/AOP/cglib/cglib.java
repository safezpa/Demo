package spring.AOP.cglib;
// CGLIB:
// 类+切入逻辑---》（织入器织入并生成）子类

// 使用动态字节码生成技术实现AOP原理是在*运行期间*目标字节码加载后，
// 生成目标类的子类，将切面逻辑加入到子类中，
// 所以使用Cglib实现AOP不需要基于接口。

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import spring.AOP.Business;
import spring.AOP.IBusiness2;

import java.lang.reflect.Method;

public class cglib{

    public static void main(String[] args) {
        byteCodeGe();
    }

    public static void byteCodeGe() {
        //创建一个织入器
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(Business.class);
        //设置需要织入的逻辑
        enhancer.setCallback(new LogIntercept());
        //使用织入器创建子类
        IBusiness2 newBusiness = (IBusiness2) enhancer.create();
        newBusiness.doSomeThing2();
    }

    /**
     * 记录日志
     */
    public static class LogIntercept implements MethodInterceptor {

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println();
            System.out.println("----------方法执行前----------");
            //执行原有逻辑，注意这里是invokeSuper
            Object rev = proxy.invokeSuper(target, args);
            //执行织入的日志
            if (method.getName().equals("doSomeThing2")) {
                System.out.println("记录日志");
            }
            System.out.println("----------方法执行后----------");
            return rev;
        }
    }


}
