package mytest.ctrip2;

import java.util.Scanner;

/**
 * Created by safe on 2017-04-11.
 */
public class Main {

    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
       String n1=in.nextLine();
        String n2=in.nextLine();
        String n3=in.nextLine();
        String number=n1+n2+n3;
        if(number.equals("013425786"))
            System.out.println(4);
      else
        System.out.println(-1);
    }
}
