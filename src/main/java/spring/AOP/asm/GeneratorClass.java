package spring.AOP.asm;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *
 * Comparison between Javassist & ASM:
 *
 • Javassist source level API is much easier to use than the actual bytecode manipulation in ASM
 • Javassist provides a higher level abstraction layer over complex bytecode level operations.

   Javassist source level API requires very less or no knowledge of actual bytecodes, so much
 easier & faster to implement.

 • Javassist uses reflection mechanism which makes it slower compared to
   ASM which uses Classworking techniques at runtime.

 • Overall ASM is much faster & gives better performance than Javassist.
 *
 */


/**
 *  ASM is an all purpose Java bytecode manipulation and analysis framework.
 *  It can be used to modify existing classes or dynamically generate classes,
 *  directly in binary form.
 *  Provided common transformations and analysis algorithms allow to
 *  easily assembling custom complex transformations and code analysis tools.
 *  • ASM offer similar functionality as other bytecode frameworks,
 *  but it is focused on simplicity of use and performance.
 *
 *
 * java字节码生成框架asm
 * ASM 是一个 Java 字节码操控框架。它能够以二进制形式修改已有类或者动态生成类。
 * ASM 从类文件中读入信息后,能够改变类行为,分析类信息,甚至能够根据用户要求生
 */

/**
 * 通过asm生成类的字节码
 * @author Administrator
 *
 */
public class GeneratorClass {

    public static void main(String[] args) throws IOException {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT+Opcodes.ACC_INTERFACE,
                "com/asm3/Comparable", null, "java/lang/Object", new String[]{"com/asm3/Mesurable"});
        //定义类的属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "LESS", "I", null, new Integer(-1)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "EQUAL", "I", null, new Integer(0)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "GREATER", "I", null, new Integer(1)).visitEnd();
        //定义类的方法
        cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd(); //使cw类已经完成
        //将cw转换成字节数组写到文件里面去
        byte[] data = cw.toByteArray();
        File file = new File("./Comparable.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
}
