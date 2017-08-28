package mytest.didi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner  in = new Scanner(System.in);
        List<Integer> a=new ArrayList<>();
        while (in.hasNextInt()) {
            a.add(in.nextInt());
        }
        in.close();

        System.out.println(online(a));
    }

    public static int online(List a){
        int sum=(int)a.get(0);
        int thissum=0;
        for (int i = 0; i <a.size() ; i++) {
            thissum+=(int)a.get(i);
            if (thissum>sum)
                sum=thissum;
            else if (thissum<0)
            {
                thissum=0;
            }
        }

        return sum;
    }
}
