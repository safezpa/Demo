package mytest.JD;

/**
 * Created by safe on 2017-04-07.
 */
public class Select {
    static char[] an = null;
    static char[] temp = null;
    static final int N = 3;

    public static void main(String[] args) {
        an = new char[]{'1','2','3','4','5'};
        temp = new char[N];
        combine1(an.length, N);
        //combine2(an.length, N);
    }
    private static void combine1(int n, int r) {
        if(r == 0){
            for(int i=0; i<N; i++){
                System.out.print(temp[i]);
            }
            System.out.println();
        }else if(n<r){
            return;
        }else{
            combine1(n-1, r);//不选当前数字，从剩下的数字中选r个
            temp[r-1] = an[n-1];
            combine1(n-1, r-1);//选择当前数字，从剩下的当中选r-1个
        }
    }
    private static void combine2(int n, int r){
        for(int i=r; i<=n; i++){//每次选一个数字作为最大值
            temp[r-1] = an[i-1];
            if(r>1){
                combine2(i-1, r-1);//从比最大值小的数字中再选r-1个
            }else{
                for(int j=0; j<N; j++){
                    System.out.print(temp[j]);
                }
                System.out.println();
            }
        }
    }
}
