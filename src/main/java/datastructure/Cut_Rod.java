package datastructure;
// Introduction to algorithms p207 Dynamic Programing
/**
 * 钢条切割 p={0,1,5,8,9,10,17,17,20,24,30}
 * r(n)=max(p(i),r(1)+r(n-1),r(2)+r(n-2),...r(i)+r(n-i),.....,r(n-1)+r(1))
 *
 * 第一段不切割
 * r(n)=max(p(i)+r(n-i))
 *
 */
public class Cut_Rod {
    private static int cut_rod(int[] p,int n){
        if (n==0) return 0;
        int q=0;
        for (int i = 1; i <=n ; i++) {
            q=Integer.max(q,p[i]+cut_rod(p,n-i));
        }
        return q;
    }

    public static void main(String[] args) {
        int[] p={0,1,5,8,9,10,17,17,20,24,30};
        System.out.println(cut_rod(p,6));
        System.out.println(memoized_cut_rod(p,6));
        System.out.println(bottom_up_cut_rod(p,6));
        Out o=extended_bottom_up_cut_rod(p,10);
        for (int i = 0; i <11 ; i++) {
            System.out.println(i+" "+o.r[i]+" "+o.s[i]);
        }
        int n=10;
        while (n>0){
            System.out.println(o.s[n]);
            n=n-o.s[n];
        }

    }

    /**
     * top_down
     *
     * 带备忘录的递归 r(i)
     * @param p 价格数组
     * @param n 总共几米
     * @return
     */
    private static int memoized_cut_rod(int[] p,int n){
        int[] r=new int[n+1];
        for (int i = 0; i <n+1 ; i++) {
            r[i]=-1;
        }
        return memoized_cut_rod_aux(p,n,r);
    }

    private static int memoized_cut_rod_aux(int[] p, int n, int[] r) {
        int q=0;
        if (r[n]>=0) return r[n];
        if (n==0) q=0;
        else {
            q=-1;
            for (int i = 1; i <=n ; i++) {
                q=Integer.max(q,p[i]+memoized_cut_rod_aux(p,n-i,r));
            }
        }
        r[n]=q;
        return q;
    }


    /**
     * 自底向上计算
     * @param p
     * @param n
     * @return
     */
    private static int bottom_up_cut_rod(int[] p, int n){
        int[] r=new int[n+1];
        for (int i = 0; i <n+1 ; i++) {
            r[i]=-1;
        }
        r[0]=0;

        for (int i = 1; i <=n ; i++) {
            int q=-1;
            for (int j = 1; j <=i ; j++) {
                q=Integer.max(q,p[j]+r[i-j]);
                r[i]=q;
            }

        }

        return r[n];
    }


    private static Out extended_bottom_up_cut_rod(int[] p,int n){
        Out o=new Out(n);
        o.r[0]=0;
        for (int i = 1; i <=n ; i++) {
           int q=-1;
            for (int j = 1; j <=i ; j++) {
               if (q<p[j]+o.r[i-j]){
                   q=p[j]+o.r[i-j];
                   o.s[i]=j;
               }
            }
            o.r[i]=q;
        }

        return o;
    }

    private static class Out {
        public int[] r;
        public int[] s;
        Out(int n){
            r=new int[n+1];
            s=new int[n+1];
        }
    }
}
