package mytest.datastructure;

/**
 * Created by safe on 2017/2/28.
 */
public class recursive {
    public static int f(int x){
        if(x==0){
            //base case
            return 1;
        }else {
            //make progress
            return x*f(x-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(f(5));
    }
}
