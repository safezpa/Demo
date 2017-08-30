package spring.AOP.dpoxy;

import spring.AOP.Business;
import spring.AOP.IBusiness;
import spring.AOP.IBusiness2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


//本节将结合动态代理的源代码讲解其实现原理。
//        动态代理的核心其实就是代理对象的生成，
//        即Proxy.newProxyInstance(classLoader, proxyInterface, handler)。
//        让我们进入newProxyInstance方法观摩下，核心代码其实就三行。
/*
//获取代理类
Class cl = getProxyClass(loader, interfaces);
//获取带有InvocationHandler参数的构造方法
        Constructor cons = cl.getConstructor(constructorParams);
//把handler传入构造方法生成实例
        return (Object) cons.newInstance(new Object[] { h });
 */

// 其中getProxyClass(loader, interfaces)
// 方法用于获取代理类，它主要做了三件事情：
// 在当前类加载器的缓存里搜索是否有代理类，
// 没有则生成代理类并缓存在本地JVM里。
// 清单三：查找代理类。
/*
// 缓存的key使用接口名称生成的List
        Object key = Arrays.asList(interfaceNames);
synchronized (cache) {
        do {
        Object value = cache.get(key);
        // 缓存里保存了代理类的引用
        if (value instanceof Reference) {
        proxyClass = (Class) ((Reference) value).get();
        }
        if (proxyClass != null) {
// 代理类已经存在则返回
        return proxyClass;
        } else if (value == pendingGenerationMarker) {
        // 如果代理类正在产生，则等待
        try {
        cache.wait();
        } catch (InterruptedException e) {
        }
        continue;
        } else {
        //没有代理类，则标记代理准备生成
        cache.put(key, pendingGenerationMarker);
        break;
        }
        } while (true);
        }
*/

//代理类的生成主要是以下这两行代码。 清单四：生成并加载代理类
//生成代理类的字节码文件并保存到硬盘中(默认不保存到硬盘)
//proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces);
//使用类加载器将字节码加载到内存中
//proxyClass = defineClass0(loader, proxyName,proxyClassFile, 0, proxyClassFile.length);
//package sun.misc;
// ProxyGenerator.generateProxyClass();


public class DynamicProxyDemo {
    public static void main(String[] args) {
        //动态代理:
        // proxyInterface(代理接口)+handler(切入逻辑)（传入new Business()）---->（反射生成）proxyBusiness(代理类)


        //需要代理的接口，被代理类实现的多个接口都必须在这里定义
        Class[] proxyInterface = new Class[] { IBusiness.class, IBusiness2.class };
        //构建AOP的Advice，这里需要传入业务类的实例
        LogInvocationHandler handler = new LogInvocationHandler(new Business());
        //生成代理类的字节码加载器
        ClassLoader classLoader = DynamicProxyDemo.class.getClassLoader();
        //织入器，织入代码并生成代理类
        IBusiness2 proxyBusiness = (IBusiness2) Proxy.newProxyInstance(classLoader, proxyInterface, handler);
        //使用代理类的实例来调用方法。
        proxyBusiness.doSomeThing2();
        ((IBusiness) proxyBusiness).doSomeThing();
        proxyBusiness.doSomeThing2();
        //ProxyGenerator.generateProxyClass();
    }

    /**
     * 打印日志的切面
     */
    public static class LogInvocationHandler implements InvocationHandler {

        private Object target; //目标对象

        LogInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println();
            System.out.println("----------方法执行前----------");
            //执行原有逻辑
            Object rev = method.invoke(target, args);
            //执行织入的日志，你可以控制哪些方法执行切入逻辑
            if (method.getName().equals("doSomeThing2")) {
                System.out.println("记录doSomeThing2日志");
            }
            if (method.getName().equals("doSomeThing")) {
                System.out.println("记录doSomeThing日志");
            }
            System.out.println("----------方法执行后----------");
            return rev;
        }
    }


}
