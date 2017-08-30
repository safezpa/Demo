package spring.AOP.jsvassist;

//3.3 自定义类加载器
//        如果我们实现了一个自定义类加载器，
// 在类加载到JVM之前直接修改某些类的方法，
// 并将切入逻辑织入到这个方法里，
// 然后将修改后的字节码文件交给虚拟机运行，那岂不是更直接。


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
            if (!"model$Business".equals(classname)) {
                return;
            }
            //通过获取类文件
            try {
                CtClass  cc = pool.get(classname);
                //获得指定方法名的方法
                CtMethod m = cc.getDeclaredMethod("doSomeThing");
                //在方法执行前插入代码
                m.insertBefore("{ System.out.println(\"记录日志\"); }");
                m.insertAfter("{ System.out.println(\"方法执行后记录日志\"); }");
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
