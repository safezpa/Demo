package mytest.sougou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(in.readLine());
        double[] a=new double[n];
        a[0]=Double.parseDouble(in.readLine());
        double max=0.0;
        double min=360.0;
        for (int i = 1; i <n ; i++) {
           a[i]=Double.parseDouble(in.readLine());
           double temp=a[i]-a[0];
           double temp2=Math.abs(temp-180.0);
           if (temp2<min){
               min=temp2;
               max=temp;
           }
        }
        in.close();
        if (max>180.0) max=360.0-max;
        System.out.print(String.format("%.8f", max));
        System.out.println();

    }


}



