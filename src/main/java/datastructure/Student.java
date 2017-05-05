package datastructure;

/**
 * Created by safe on 2017-04-07.
 */
public class Student{
    private String sname="张三";
    int sage=0;
}
class Bachelor extends Student{
    public String major;
    public static void main(String[] args){
        Student s = new Bachelor();
        //System.out.println(s.name);
    }
}
/*
public class Arraytest
{
    int a[] = new int[6];
    public static void main ( String arg[] ) {
       // System.out.println ( a[0] );
    }
}*/
