package mytest.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by safe on 2017-05-05.
 */
//网格点
class Point
{
    //属性x
    int x;
    //属性y
    int y;
    //...

}

//网格
public class Net {

    public static void main(String[] args) throws IOException {
        Point p[][];
        //总数815行181列
        p = new Point[815][181];
        String classPath=Net.class.getResource("/").getFile();
        System.out.println(classPath);
        FileReader fin = new FileReader(classPath+"WGSX.csv");
        System.out.println(classPath+"WGSX.csv");

        BufferedReader bin = new BufferedReader( fin );
        String oneLine;
        for (int i = 0;  (i = bin.read())>0; i++) {
            oneLine = bin.readLine( );
            String[] st=oneLine.split(" ,");
            int c = Integer.parseInt(st[0]);
            int n = Integer.parseInt(st[1]);
            int x = Integer.parseInt(st[2]);
            int y = Integer.parseInt(st[3]);
            p[c][n].x=x;
            p[c][n].y=y;

            System.out.println(oneLine);
        }

    }
}