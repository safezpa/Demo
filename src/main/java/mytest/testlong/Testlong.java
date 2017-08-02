package mytest.testlong;

import java.lang.management.MemoryType;
import java.text.NumberFormat;

/**
 * Created by safe on 2017-05-06.
 */
public class Testlong {

    public static void main(String[] args) {
        long c=1000*1000*1000*1000*2L;
        NumberFormat nf=NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        System.out.println(""+nf.format(c));
        System.out.println(c);
        long a= (long) Math.pow(2,63);
        long b=a-1;

        System.out.println(""+nf.format(a));
        System.out.println(""+nf.format(b));
        System.out.println();

        for (MemoryType memoryType : MemoryType.values())
            System.out.println(memoryType);

    }
}
