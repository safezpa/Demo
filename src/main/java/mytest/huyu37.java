package mytest;

public class huyu37 {
    private String a="abcd";

    public void printA(){
        System.out.println(a);

    }

    public void printB(){
        String b=null;
        System.out.println(b+"B");
    }

    public static void main(String[] args) {
        String a=new String("abcd");
        String b=a;
        a=new String("abcde")
        ;

        System.out.println();
/*        huyu37 hy=new huyu37();
        hy.printB();*/
    }
}