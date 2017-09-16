package mytest.netgame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 *
 *
 5
 02/12/2016 00:00:00 10
 02/18/2016 12:00:00 45
 02/18/2016 23:59:59 8
 02/19/2016 08:00:15 20
 02/22/2016 13:00:00 31
 4
 01/01/2014 00:00:00 01/01/2017 00:00:00
 11/11/2016 11:11:11 02/14/2017 00:05:20
 02/12/2016 00:00:00 02/18/2016 23:00:00
 02/18/2016 12:00:00 02/18/2016 12:00:00
 */
public class Main5 {
    public static void main(String[] args) throws ParseException {
        Scanner in = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss");
        int n=Integer.parseInt(in.nextLine());
        for (int i = 0; i <n ; i++) {
            String time=in.nextLine();
            String[] chars=time.split(" ");
            String str =chars[0]+" "+chars[1];
            int sum=Integer.parseInt(chars[2]);
            long millionSeconds = sdf.parse(str).getTime();//毫秒
            System.out.println(millionSeconds +" "+sum);
        }
        int m=Integer.parseInt(in.nextLine());
        int[] out=new int[m];
        for (int i = 0; i <m ; i++) {
            String time=in.nextLine();
            String[] chars=time.split(" ");
            String str1 =chars[0]+" "+chars[1];
            String str2 =chars[2]+" "+chars[3];
            System.out.println(str1);
            System.out.println(str2);
            long l = sdf.parse(str1).getTime();//毫秒
            long r = sdf.parse(str2).getTime();//毫秒
            //System.out.println(str1+"----"+str2);
            System.out.println(l+"----"+r);

        }
        in.close();
    }
}
