package mytest.JD;

/**
 * Created by safe on 2017-04-08.
 */
public class Ann {
    public static int arr[] = new int[]{1,2,3,4,5,0};
    public static void main(String[] args) {
        perm(arr,0,arr.length-1);
    }
    private static void swap(int i1, int i2) {
        int temp = arr[i2];
        arr[i2] = arr[i1];
        arr[i1] = temp;
    }

    /**
     * 对arr数组中的begin~end进行全排列
     *
     * 比如：
     * 	arr = {1,2,3}
     *  第一步：执行 perm({1,2,3},0,2),begin=0,end=2;
     *  	j=0,因此执行perm({1,2,3},1,2),begin=1,end=2;
     *  		j=1,swap(arr,0,0)-->arr={1,2,3},  perm({1,2,3},2,2),begin=2,end=2;
     *   			因为begin==end，因此输出数组{1,2,3}
     *   		swap(arr,1,1) --> arr={1,2,3};
     *   		j=2,swap(arr,1,2)-->arr={1,3,2},  perm({1,3,2},2,2),begin=2,end=2;
     *   			因为begin==end，因此输出数组{1,3,2}
     *   		swap(arr,2,1) --> arr={1,2,3};
     *   	j=1,swap(arr,0,1) --> arr={2,1,3},	  perm({2,1,3},1,2),begin=1,end=2;
     *   		j=1,swap(arr,1,1)-->arr={2,1,3}   perm({2,1,3},2,2),begin=2,end=2;
     *   			因为begin==end，因此输出数组{2,1,3}
     *   		swap(arr,1,1)--> arr={2,1,3};
     *   		j=2,swap(arr,1,2)后 arr={2,3,1},并执行perm({2,3,1},2,2),begin=2,end=2;
     *   			因为begin==end，因此输出数组{2,3,1}
     *   		swap(arr,2,1) --> arr={2,1,3};
     *   	swap(arr,1,0)  --> arr={1,2,3}
     *   	j=2,swap(arr,2,0) --> arr={3,2,1},执行perm({3,2,1},1,2),begin=1,end=2;
     *   		j=1,swap(arr,1,1) --> arr={3,2,1} , perm({3,2,1},2,2),begin=2,end=2;
     *   			因为begin==end，因此输出数组{3,2,1}
     *   		swap(arr,1,1) --> arr={3,2,1};
     *   		j=2,swap(arr,2,1) --> arr={3,1,2},并执行perm({2,3,1},2,2),begin=2,end=2;
     *   			因为begin==end，因此输出数组{3,1,2}
     *   		swap(arr,2,1) --> arr={3,2,1};
     *   	swap(arr,0,2) --> arr={1,2,3}
     *
     * @param arr
     * @param begin
     * @param end
     */
    public static void perm(int arr[], int begin,int end) {
        if(end==begin){			//一到递归的出口就输出数组，此数组为全排列
            for(int i=0;i<=end;i++){
                System.out.print(arr[i]+" ");
            }
            System.out.println();
            return;
        }
        else{
            for(int j=begin;j<=end;j++){
                swap(begin,j);		//for循环将begin~end中的每个数放到begin位置中去
                perm(arr,begin+1,end);	//假设begin位置确定，那么对begin+1~end中的数继续递归
                swap(begin,j);		//换过去后再还原
            }
        }
    }

    public char[][] Calculate0(char[] items){
        int length = items.length;
        /**
         * length!阶乘个 factorial(length)]
         * result = new char[factorial(length)][]
         */
        char[][] result = new char[(length)][];//factorial(length)]

        int iteration = length - 1;
        int index = 0;
        //first item is inserted here
        result[index++] = items;
        while (iteration > 0){
            //we keep count of current result
            int resultCount = index;
            for (int i = 0; i < resultCount; i++)
            {
                int rotateLength = length - iteration;
                char[] curItem = result[i];
                if (rotateLength > 0)
                {
                    while (rotateLength > 0)
                    {
                        rotateRight0(curItem, iteration - 1);
                        //the rotated item now is new item we copy it into new item
                        char[] newItem = new char[length];
                       // curItem.CopyTo(newItem, 0);
                        result[index++] = newItem;
                        rotateLength--;
                    }
                    //This rotation causes that original item doesn't change
                    rotateRight0(curItem, iteration - 1);
                }
            }

            iteration--;
        }

        return result;
    }
    public void rotateRight0(char[] items, int startIndex)
    {
        int last = items.length - 1;
        char temp = items[last];
        int len = items.length;
        System.arraycopy(items, startIndex, items, startIndex + 1, len - (startIndex + 1));
        items[startIndex] = temp;
    }





    public char[] calculate1(char[] items)
    {
        int length = items.length;
        char[] result = new char[(length) * length];

        int iteration = length - 1;
        int index = 1;
        //first item is inserted here
        System.arraycopy(items,0, result,0, length);
        while (iteration > 0)
        {
            //we keep count of current result
            int resultCount = index;
            for (int i = 0; i < resultCount; i++)
            {
                int rotateLength = length - iteration;
                if (rotateLength > 0)
                {
                    int startIndex = i * length + (iteration - 1);
                    int lastIndex = (i + 1) * length - 1;
                    while (rotateLength > 0)
                    {
                        rotateRight(result, startIndex, lastIndex);
                        //the rotated item  is new item and we copy it into new item
                        System.arraycopy(result, i * length, result, index * length, length);
                        index++;
                        rotateLength--;
                    }
                    //This rotation causes that original item doesn't change
                    rotateRight(result, startIndex, lastIndex);
                }
            }

            iteration--;
        }

        return result;
    }
    public void rotateRight(char[] items, int startIndex, int lastIndex)
    {
        char temp = items[lastIndex];
        int len = lastIndex + 1;
        System.arraycopy(items, startIndex, items, startIndex + 1, len - (startIndex + 1));
        items[startIndex] = temp;

    }

}

