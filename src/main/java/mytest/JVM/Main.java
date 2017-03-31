package mytest.JVM;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by safe on 2017-03-30.
 */
public class Main {

    public static List<String> readWords(BufferedReader in ) throws IOException
    {
        String oneLine;
        List<String> lst = new ArrayList<>( );

        while( ( oneLine = in.readLine( ) ) != null )
            lst.add( oneLine );

        return lst;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        int[] a=new int[num];
        for (int i = 0; i <num ; i++) {
            a[i]=in.nextInt();
        }
/*//找乖点
        int p0=0,p1=1,p2=2;
        for (int i = 1; i <num ; i++) {
            if (a[i]>a[p0]){
                p0++;
                if (a[p0+1]<a[p0])
                    break;

            }
        }*/

        int max=num-1;
        int min=0;
        Random random = new Random();
        Random random1 = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        int s1 = random1.nextInt(max)%(max-min+1) + min;
        while (s==s1)
            s1 = random1.nextInt(max)%(max-min+1) + min+1;
        System.out.println(s+" "+s1);

    }
}
