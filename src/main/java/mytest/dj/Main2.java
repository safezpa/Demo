package mytest.dj;

/*** Created by safe on 2017-04-07.
 *
 *有一个水桶(高 H cm)，在离底部 h cm处破了一个洞，顶部有个入水口。入水口在水桶完好无损时，入水量是 x cm/s(即每秒水位上升xcm)。在水位高于h的情况下，破洞的出水量是 y cm/s(即每秒水位下降xcm)，水位等于或者小于h时不出水。请问在入水口加水的情况下 s 秒后的水位。
 *10 2 1 1 5
 *6
*/
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        float H  =(float)in.nextInt();
        float x  =(float)in.nextInt();
        float y  =(float)in.nextInt();
        float h  =(float)in.nextInt();
        float s  =(float)in.nextInt();
        float rs;
        float t;

        t=h/x;
        if(y>=x){
            if(s>=t){
                rs=h;
            }else{
                rs=s*x;
            }
        }else{
            if(s>=t){
                rs=(s-t)*(x-y)+h;

                if(rs>H){
                    rs=H;
                }

            }else{
                rs=s*x;
            }

        }
        int round = Math.round(rs);
        System.out.println(round);

    }
}

