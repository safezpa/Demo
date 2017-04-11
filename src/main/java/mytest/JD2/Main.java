package mytest.JD2;

/**
 * Created by safe on 2017-04-07.
 */
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        char[] char1 = in.nextLine().toCharArray();
        char[] char2 = in.nextLine().toCharArray();
        in.close();

        //按位异或得到二进制序列
        int[] out = new int[n];
        for (int i = 0; i < n; i++) {
            out[i] = char1[i] ^ char2[i];
        }

        //转换成十进制
        double d = 0;
        for (int i = 0; i < n; i++) {
            long r=n-1-i;
            d = d + out[i] * Math.pow(2.0,r);
        }

        System.out.println((int)d);
    }
}

