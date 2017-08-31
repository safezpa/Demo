package spring.AOP.instrumentation;

/**
 * 使用 Instrumentation，
 * 开发者可以构建一个独立于应用程序的代理程序（Agent），
 * 用来监测和协助运行在 JVM 上的程序，甚至能够替换和修改某些类的定义。
 * 有了这样的功能，开发者就可以实现更为灵活的运行时虚拟机监控和Java 类操作了，
 * 这样的特性实际上提供了一种虚拟机级别支持的 AOP 实现方式，
 * 使得开发者无需对 JDK 做任何升级和改动，就可以实现某些 AOP 的功能了。
 *
 * 可以用来监控JVM
 * com.sun.tools.attach
 * java -javaagent:TestInstrument1.jar -cp TestInstrument1.jar TestMainInJar
 *
 * 在manifest中设置Premain-Class和Agent-Class
 *
 * premain()
 * public static void premain(String agentArgs, Instrumentation inst);  [1]
 * public static void premain(String agentArgs); [2]
 *
 * agentmain()
 * public static void agentmain (String agentArgs, Instrumentation inst); [1]
 * public static void agentmain (String agentArgs);[2]
 *
 *
 */

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyClassFileTransformer implements ClassFileTransformer {

    public static void premain(String options, Instrumentation ins) {
        //注册我自己的字节码转换器
        ins.addTransformer(new MyClassFileTransformer());
    }

    /**
     * 字节码加载到虚拟机前会进入这个方法
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        System.out.println(className);
        //如果加载Business类才拦截
        if (!"model/Business".equals(className)) {
            return null;
        }

        //javassist的包名是用点分割的，需要转换下
        if (className.indexOf("/") != -1) {
            className = className.replaceAll("/", ".");
        }
        try {
            //通过包名获取类文件
            CtClass cc = ClassPool.getDefault().get(className);
            //获得指定方法名的方法
            CtMethod m = cc.getDeclaredMethod("doSomeThing");
            //在方法执行前插入代码
            m.insertBefore("{ System.out.println(\"记录日志\"); }");
            return cc.toBytecode();
        } catch (NotFoundException e) {
        } catch (CannotCompileException e) {
        } catch (IOException e) {
            //忽略异常处理
        }
        return null;
    }
}


