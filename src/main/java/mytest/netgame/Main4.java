package mytest.netgame;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
public class Main4 {
    public static void main(String[] args) throws ParseException {
        long epoch = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime();
        System.out.println(epoch);
    }
}
