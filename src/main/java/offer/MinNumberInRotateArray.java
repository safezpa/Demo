package offer;

/**
 * Created by safe on 2017-07-06.
 */
public class MinNumberInRotateArray {

    public static int minNumberInRotateArray(int [] array) {
        if (array==null || array.length<=0) return -1;
        int index1=0;
        int index2=array.length-1;
        int indexMid=index1;

        while (array[index1] >= array[index2]){
            if (index2-index1 == 1){
                indexMid = index2;
                break;
            }

            indexMid = (index1+index2)/2;

            if (array[index1]==array[indexMid] && array[indexMid]==array[index2])
                return minInOrder(array,index1,index2);

            if (array[indexMid] >= array[index1]){
                index1 = indexMid;
            }else if(array[indexMid] <= array[index2]) {
                index2 = indexMid;
            }

        }
        return array[indexMid];
    }

    private static int minInOrder(int[] array, int index1, int index2) {
        int result=array[index1];
        for (int i=index1+1; i<=index2;i++){
            if (array[i]<result)
                result=array[i];
        }
        return result;
    }



    public static void main(String[] args) {
        int[] a={1,1,1,1,0,1};
        int[] b={1,0,1,1,1,1};
        System.out.println(minNumberInRotateArray(b));
    }
}
