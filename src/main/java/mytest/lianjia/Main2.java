package mytest.lianjia;

import java.util.*;

public class Main2 {
    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int m=in.nextInt();
        Set<Integer> ts=new TreeSet<>();
        for (int i = 0; i <n+m ; i++) {
           ts.add(in.nextInt());
        }
        in.close();

        Iterator it=ts.iterator();
        while(it.hasNext()){
            System.out.print(it.next());
            if (it.hasNext()) System.out.print(" ");
        }


    }



}



