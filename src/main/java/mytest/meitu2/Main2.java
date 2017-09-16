package mytest.meitu2;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=in.nextInt();
        }
        in.close();
        Set<Integer> integerSet=new HashSet<>();
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n ; j++) {
                if (a[i]==a[j]) continue;
                int tem=Integer.parseInt(""+a[i]+""+a[j]);
                if (tem % 7==0) integerSet.add(tem);
            }

        }
        System.out.println(integerSet.size());
    }
}
