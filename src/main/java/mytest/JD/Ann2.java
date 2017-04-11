package mytest.JD;

/**
 * Created by safe on 2017-04-08.
 */
public class Ann2 {
    public static int arr[] = new int[]{0,0,0};
    public static void main(String[] args) {
        perm(3);
    }
    /**
     * 数组变化过程：
     * 3 0 0
     * 3 2 0
     * 3 2 1
     * 3 2 0
     * 3 0 0
     * 3 0 2
     * 3 1 2
     * 3 0 2
     * 3 0 0
     * 0 0 0
     * 0 3 0
     * 2 3 0
     * 2 3 1
     * 2 3 0
     * 0 3 0
     * 0 3 2
     * 1 3 2
     * 0 3 2
     * 0 3 0
     * 0 0 0
     * 0 0 3
     * 2 0 3
     * 2 1 3
     * 2 0 3
     * 0 0 3
     * 0 2 3
     * 1 2 3
     * 0 2 3
     * 0 0 3
     * 0 0 0
     * @param m
     */
    private static void perm(int m) {
        if(m==0){
            for(int i=0;i<arr.length;i++){
                System.out.print(arr[i]+" ");
            }
            System.out.println();
            return;
        }
        else{
            for(int i=0;i<arr.length;i++){
                if(arr[i]==0){


/*                    for(int j=0;j<arr.length;j++){
                        System.out.print(arr[j]+" ");
                    }
                    System.out.println();*/

                    arr[i] = m;
                    perm(m-1);
                    arr[i] = 0;
                }
            }
        }
    }
}
