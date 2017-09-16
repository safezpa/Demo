package mytest.netgame;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
        int n=Integer.parseInt(in.nextLine());
        Time[] a=new Time[n];
        for (int i = 0; i <n ; i++) {
            String time=in.nextLine();
            String[] chars=time.split(":");
            a[i]=new Time();
            a[i].hh=chars[0];
            a[i].mm=chars[1];
            a[i].ss=chars[2];
        }
        in.close();
        for (int i = 0; i <n ; i++) {
            int hh=Integer.parseInt(a[i].hh);
            if (hh>23) a[i].hh="0" + hh % 10;
            int mm=Integer.parseInt(a[i].mm);
            if (mm>59) a[i].mm="0" + mm % 10;
            int ss=Integer.parseInt(a[i].ss);
            if (ss>59) a[i].ss="0" + ss % 10;
            System.out.println(a[i]);
        }
    }
    static class Time{
        String hh;
        String mm;
        String ss;

        @Override
        public String toString() {
            return hh+":"+mm+":"+ss;
        }
    }

}



