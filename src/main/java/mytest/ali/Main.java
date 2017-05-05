package mytest.ali;

/**
 * Created by safe on 2017-04-26.
 *
  2 3 4 * ^ 5 +
 其中运算符^从栈顶弹出一个整数，增加1之后压栈；
 运算符+和*从栈顶弹出两个整数，分别做相加和相乘运算后压栈。
 如果遇到运算符的时候，栈内没有足够的整数，称为下溢出，返回-1；
 把整数压栈的时候，如果栈没有足够的空间，称为上溢出，返回-2；
 *
 *
 *
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    enum OptEm
    {
        add("+"),//加号

        multip("*"),//乘号

        selfadd("^");//自增
        private  String v;
        public String getVal()
        {
            return v;
        }
        OptEm(String s)
        {
            v=s;
        }
    }



    public static void main(String[] args) {

        ArrayList<Integer> inputs = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        if(line != null && !line.isEmpty()) {
            int res = resolve(line.trim());
            System.out.println(String.valueOf(res));
        }
    }

    // write your code here
    public static int resolve(String exp) {
        String[] input = exp.split(" ");
        int result=calculate(input);
        return result;
    }

    public static int calculate(String[] exputArr)
    {
        int result=0;
        int op1=0;
        int op2=0;
        LinkedList<String> list=new LinkedList<String>();

        for(String ch:exputArr)
        {

            if(ch.equals(OptEm.add.getVal())||
                    ch.equals(OptEm.multip.getVal()))
            {
                //如果遇到运算符的时候，栈内没有足够的整数，称为下溢出，返回-1；
                if (list.size()<2) return -1;
                op2=Integer.valueOf(list.pop());
                op1=Integer.valueOf(list.pop());
                list.push(String.valueOf(CalculateExpression(op1,op2,ch)));//计算值，再入栈
            }else if (ch.equals(OptEm.selfadd.getVal()) ){

                //如果遇到运算符的时候，栈内没有足够的整数，称为下溢出，返回-1；
                if (list.size()<1) return -1;
                op1=Integer.valueOf(list.pop());

                list.push(String.valueOf(op1+1));// 其中运算符^从栈顶弹出一个整数，增加1之后压栈；
            }
            else
            {
                //把整数压栈的时候，如果栈没有足够的空间，称为上溢出，返回-2；
                if (list.size()>=16) return -2;
                list.push(ch);//数字直接进栈
            }
        }
        //如果整个计算过程中没有发生溢出，在整个表达式求解完成后，返回栈顶的整数。
        result=Integer.valueOf(list.pop());
        return result;
    }

    // 运算符+和*从栈顶弹出两个整数，分别做相加和相乘运算后压栈。
    public static int CalculateExpression(Integer op1, Integer op2, String op)
    {
        int result=0;

        if(op.equals(OptEm.add.getVal()))
        {
            result=op1+op2;
        }

        else if(op.equals(OptEm.multip.getVal()))
        {
            result=op1*op2;
        }


        return result;
    }

}
