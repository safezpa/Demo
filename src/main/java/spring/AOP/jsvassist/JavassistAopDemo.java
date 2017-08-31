package spring.AOP.jsvassist;

//3.3 自定义类加载器
//        如果我们实现了一个自定义类加载器，
// 在类加载到JVM之前直接修改某些类的方法，
// 并将切入逻辑织入到这个方法里，
// 然后将修改后的字节码文件交给虚拟机运行，那岂不是更直接。

/**
 * Javassist (Java Programming Assistant) makes Java bytecode manipulation simple.
 * It is a class library for editing bytecodes in Java;
 * it enables Java programs to define a new class at runtime and to modify a class
 * file when the JVM loads it.
 * Unlike other similar bytecode editors
 • Javassist provides two levels of API: source level and bytecode level.
 • Javassist lets you inspect, edit, and create Java binary classes.
 • Javassist also provides lower-level API for directly editing a class file.
 • Aspect Oriented Programming
 *
 *
 *
 * Javassist是一个编辑字节码的框架，可以让你很简单地操作字节码。
 * 它可以在运行期定义或修改Class。
 * 使用Javassist实现AOP的原理是在字节码加载前直接修改需要切入的方法
 * 这比使用cglib实现AOP更加高效，并且没有太多限制，实现原理如下图：
 */

import javassist.*;
import spring.AOP.Business;

public class JavassistAopDemo {

    public static class MyTranslator implements Translator {

        public void start(ClassPool pool) throws NotFoundException, CannotCompileException {
        }

        /* *
         * 类装载到JVM前进行代码织入
         */
        public void onLoad(ClassPool pool, String classname) {
/*            if (!"spring.AOP.Business".equals(classname)) {
                //System.out.println(classname);
                return;
            }*/
            //通过获取类文件
            try {
                CtClass  cc = pool.get(classname);
                //获得指定方法名的方法
                CtMethod m = cc.getDeclaredMethod("doSomeThing2");
                //在方法执行前插入代码
                m.insertBefore("{ System.out.println(\"方法执行前记录日志\"); }");
                m.insertAfter("{ System.out.println(\"方法执行后记录日志\"); }");
                m.insertAfter("{ System.out.println(); }");
            } catch (NotFoundException e) {
            } catch (CannotCompileException e) {
            }
        }

        public static void main(String[] args) {
            Business b = new Business();
            b.doSomeThing2();
            b.doSomeThing();
        }
    }


}
