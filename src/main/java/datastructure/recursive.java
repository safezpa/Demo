package datastructure;

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

    public static int fib(int x){
        if(x==0){
            //base case
            return -1;
        }else if(x==1) {
            //make progress
            return 1;
        }else if (x==2){
            return 2;
        }else {
            return fib(x-1)+fib(x-2);
        }
    }


    public static int fib2(int x){
        if(x==0){
            //base case
            return -1;
        }else if(x==1) {
            //make progress
            return 1;
        }else if (x==2){
            return 2;
        }else {
            int[] out=new int[x+1];
            out[0]=0;out[1]=1;out[2]=2;
            for (int i = 3; i <=x ; i++) {
                out[i]=out[i-1]+out[i-2];
            }
            return out[x];
        }
    }
    public static void main(String[] args)    {
        //System.out.println(f(5));
        System.out.println(fib2(4));
    }
}
