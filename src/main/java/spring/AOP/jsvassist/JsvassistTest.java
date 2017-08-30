package spring.AOP.jsvassist;


import javassist.ClassPool;
import javassist.tools.reflect.Loader;

public class JsvassistTest{

    public static void main(String[] args) throws Throwable {
        //获取存放CtClass的容器ClassPool
        ClassPool cp = ClassPool.getDefault();
        //创建一个类加载器
        Loader cl = new Loader();
        //增加一个转换器
        cl.addTranslator(cp, new JavassistAopDemo.MyTranslator());
        //启动MyTranslator的main函数
        cl.run("spring.AOP.jsvassist.JavassistAopDemo$MyTranslator", args);
    }
}

