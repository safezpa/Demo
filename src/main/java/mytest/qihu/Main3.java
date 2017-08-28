package mytest.qihu;

public class Main3 {
    public static void main(String[] args) {

/*        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int[] a=new int[n];
        for (int i = 0; i <n ; i++) {
            a[i]=in.nextInt();
        }

        int m=in.nextInt();
        int[] b=new int[m];
        for (int i = 0; i <m ; i++) {
            b[i]=in.nextInt();
        }
        in.close();*/
        int[] a={100,99,98,1,2, 3};
        int[] b={1, 2, 3, 4,5,40};
        System.out.println(swapToMinusDiff(a, b));
    }

    public static int  swapToMinusDiff(int[] a,int[] b){
        int sumA=sum(a);
        int sumB=sum(b);
        if(sumA==sumB) return 0;
        if(sumA<sumB){
            int[] temp=a;
            a=b;
            b=temp;
        }
        int curDiff=1;
        int oldDiff=Integer.MAX_VALUE;
        int pA=-1;
        int pB=-1;
        boolean shift=true;
        int len=a.length;
        while(shift && curDiff>0){
            shift=false;
            curDiff=sum(a)-sum(b);
            for(int i=0;i<len;i++){
                for(int j=0;j<b.length;j++){
                    int temp=a[i]-b[j];
                    int newDiff=Math.abs(curDiff-2*temp);
                    if(newDiff<curDiff && newDiff<oldDiff){
                        shift=true;
                        oldDiff=newDiff;
                        pA=i;
                        pB=j;
                    }
                }
            }
            if(shift){
                int temp=a[pA];
                a[pA]=b[pB];
                b[pB]=temp;
            }
        }
        return oldDiff;
    }
    public static int sum(int[] a){
        int sum=0;
        for(int each:a){
            sum+=each;
        }
        return sum;
    }


}



