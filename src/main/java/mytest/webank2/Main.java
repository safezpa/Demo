package mytest.webank2;

import java.util.Scanner;

/**
 * Created by safe on 2017-04-13.
 */
 class Point
{


    int x;
    int y;

}


public class Main {

    public static void main(String[] args) {
        Point a[];
        a=new Point[16];
        int[] x=new int[9];
        int[] y=new int[9];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i <8; i++) {
            a[i].x = in.nextInt();
            a[i].y = in.nextInt();
        }
        in.close();
        int p,q,e,f;
        for (int i = 0; i < 8; i++) {
            int k=1;
            x[0]=a[0].x;
            y[0]=a[0].y;
            for(int j=1; j<8; j++)
            {
                x[k]=a[i].x;
                y[k++]=a[i].y;
            }


            int flag=0;
            if(judge(x[0],y[0],x[1],y[1],x[2],y[2],x[3],y[3])||judge(x[0],y[0],x[2],y[2],x[1],y[1],x[3],y[3])||judge(x[0],y[0],x[2],y[2],x[3],y[3],x[1],y[1]))
                flag=1;
            if(flag==1)
                System.out.println("YES\n");
            else
                System.out.println("NO\n");
        }
 }








    static boolean angle(int x,int y,int m,int n,int p,int q)
    {
        if((p-x)*(m-x)+(q-y)*(n-y)==0)
        {
            return true;
        }
        return false;
    }
    static boolean judge(int a,int b,int x,int y,int p,int q,int m,int n)
    {
        if(angle(a,b,x,y,m,n)==false
                ||angle(x,y,a,b,p,q)==false
                ||angle(m,n,a,b,p,q)==false
                ||angle(p,q,x,y,m,n)==false
                )
        {
            return false;
        }
        return true;
    }

}





