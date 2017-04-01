package mytest.JVM;

public class Hello {
/*
    java启动参数共分为三类；
    其一是标准参数（-），所有的JVM实现都必须实现这些参数的功能，而且向后兼容；
    其二是非标准参数（-X），默认jvm实现这些参数的功能，但是并不保证所有jvm实现都满足，且不保证向后兼容；
    其三是非Stable参数（-XX），此类参数各个jvm实现会有所不同，将来可能会随时取消，需要慎重使用；
   */

/*
    verbose
    -verbose:class
    输出jvm载入类的相关信息，当jvm报告说找不到类或者类冲突时可此进行诊断。
            -verbose:gc
    输出每次GC的相关情况。
            -verbose:jni
    输出native方法调用的相关情况，一般用于诊断jni调用错误信息。
*/

    private static int count=0;
/*    public StreamAPI void recursion(){
        count++;
        recursion();
    }*/

    public static void recursion(int a,int b,int c){
        long l1=12;
        short sl=1;
        byte b1=1;
        String s="1";
        System.out.println("count="+count);
        count++;
        recursion(1,2,3);
    }
    public static void recursion(){
        System.out.println("count="+count);
        count++;
        recursion();
    }

    public static void main(String[] args) {
        try {
            recursion();
        } catch (Exception e) {
            System.out.println("deep of calling="+count);
            e.printStackTrace();
        }
    }
}



//        javap -classpath .  -c HelloWorld


/*        Compiled from "HelloWorld.java"
public class HelloWorld extends java.lang.Object{
    public HelloWorld();
    Code:
            0:   aload_0
   1:   invokespecial   #1; //Method java/lang/Object."<init>":()V
   4:   return

    public StreamAPI void main(java.lang.String[]);
    Code:
            0:   getstatic   #2; //Field java/lang/System.out:Ljava/io/PrintStream;
   3:   ldc #3; //String Hello World
   5:   invokevirtual   #4; //Method java/io/PrintStream.println:(Ljava/lang/String;)V
   8:   return
}
*/


/*上面的代码包含两个方法: 一个是编译器推断出来的默认的构造器；另外一个是main方法。

        接下来，每个方法都有一系列的指令。比如aload_0、invokespecial #1等等。可以在Java字节码指令集中查到每个指令的功能，例如aload_0用来从局部变量0中加载一个引用到堆栈，getstatic用来获取类的一个静态字段值。可以注意到，getstatic指令之后的“#2″指向的是运行期常量池。常量池是JVM运行时数据区之一。我们可以通过“javap -verbose”命令来查看常量池。

        另外, 每个指令从一个数字开始，比如0、1、4等等。在.class文件中，每个方法都有一个对应的字节码数组。这些数字对应于存储每个操作码及其参数的数组的下标。每个操作码都是1个字节长度，并且指令可以有0个或多个参数。这就是为什么这些数字不是连续的原因。

        现在，我们使用“javap -verbose”这个命令来进一步观察这个类。

        */
