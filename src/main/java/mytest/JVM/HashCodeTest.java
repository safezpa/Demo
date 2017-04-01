package mytest.JVM;

/**
 * Created by safe on 2017-04-01.
 * hash设计要点：
 * 1.简单高效，
 * 2.尽可能减少冲突
 *
 * 1、s[0]*31^(n-1) + s[1]*31^(n-2) + … + s[n-1]  数学公式代表什么？
 * Why 为什么这么算？？
 * 1.1
 * i*31 可以转换成 i<<5-1 移位操作快
 *
 * 1.2
 * 素数 可以减少冲突是为什么？？
 */
public class HashCodeTest {
    public static void main(String[] args)
    {
        String s = "hashCode";
        StringBuilder sb = new StringBuilder(s);
        System.out.println("hashCode1: " + s.hashCode() + " " + sb.hashCode());

        String s1 = new String("hashCode");
        StringBuilder sb1 = new StringBuilder(s1);
        System.out.println("hashCode2: " + s1.hashCode() + " " + sb1.hashCode());

        // are they equals?
        System.out.println("s  s1 : " + s.equals(s1));
        System.out.println("sb sb1: " + sb.equals(sb1));
    }
}

