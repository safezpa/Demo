package mytest.payPal;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by safe on 2017-04-13.
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Stack<Integer> s=new Stack<Integer>();
        String s1=in.nextLine();
        String s2=in.nextLine();
        in.close();
        String[] ss1= s1.split(" ");
        String[] ss2= s2.split(" ");
        int alive=ss1.length;

            for (int i = 0; i <ss1.length ; i++) {
                //fish size
                int fiz = Integer.parseInt(ss1[i]);
                //dir
                int dir = Integer.parseInt(ss2[i]);
                if (dir==1) s.push(fiz);
                else while (!s.isEmpty()) {
                    if (fiz>s.peek()){
                        s.pop();
                        alive--;
                    }
                    else {
                        alive--;
                        break;
                    }
                }
            }


            System.out.println(alive);


        }
    }





