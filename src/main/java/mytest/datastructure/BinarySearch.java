package mytest.datastructure;

/**
 * Created by safe on 2017/2/28.
 */
public class BinarySearch {
/*二分查找
* 1.要查找的数组有序
* 2.可比较
* 3.输入为要查找的数组，要查找的关键字，输出在数组中的位置，若未找到则返回-1
*
* */
public static final int NOT_FOUND=-1;
public static <AnyType extends Comparable<? super AnyType>> int binarySearch(AnyType [] a, AnyType x){
    int low=0,high=a.length-1;
    while (low<=high){
        int mid=low+(high-low)/2;
        if (x.compareTo(a[mid])<0)
              high=mid-1;
        else if (x.compareTo(a[mid])>0)
            low=mid+1;
        else
            return mid;//找到了

    }

    return NOT_FOUND;

}

    public static void main(String[] args) {
        int SIZE=8;
        Integer [] a=new Integer[SIZE];
        for (int i = 0; i <SIZE ; i++) {
            a[i]=i<<1;
            System.out.println(a[i]);
        }
        System.out.println("----------------------------");
        for (int i = 0; i <SIZE*2 ; i++) {

            System.out.println(i+"在"+binarySearch(a,i));
        }

    }



}
