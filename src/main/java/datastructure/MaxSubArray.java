package datastructure;

public class MaxSubArray {

  private static int maxSumRec(int[] a,int left,int right){
      if (left==right) //base case
          if (a[left]>0)
              return a[left];
      else
          return 0;

      int center=(left+right)/2;
      int maxLeftSum=maxSumRec(a,left,center);
      int maxRightSum=maxSumRec(a,center+1,right);
      int maxCrossSum=maxCrossSum(a,left,center,right);

        return  Math.max(Math.max(maxCrossSum,maxLeftSum),Math.max(maxCrossSum,maxRightSum));

  }

    private static int maxCrossSum(int[] a, int left, int center, int right) {
      int left_sum=0;
      int sum=0;
        for (int i = center; i >=left ; i--) {
            sum+=a[i];
            if (sum>left_sum) left_sum=sum;
        }

        int right_sum=0;
         sum=0;
        for (int i = center+1; i <=right ; i++) {
            sum+=a[i];
            if (sum>right_sum) right_sum=sum;
        }
        return left_sum+right_sum;
    }

    public static void main(String[] args){
        int[] A = {-23,17,-7,11,-2,1,-34};
        int result = maxSumRec(A, 0, A.length - 1);
        System.out.println( result);
        System.out.println(online(A));
    }

    public static int online(int[] a){
        int sum=a[0];
        int thissum=0;
        for (int i = 0; i <a.length ; i++) {
            thissum+=a[i];
            if (thissum>sum)
                sum=thissum;
            else if (thissum<0)
            {
                thissum=0;
            }
        }

        return sum;
    }
}
