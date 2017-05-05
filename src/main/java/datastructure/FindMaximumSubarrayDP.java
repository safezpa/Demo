package datastructure;


/**
 * First Part  Foundations
 * Chapter 4  Divide-and-Conquer
 * Page 75 Maximum Subarray with linear-time.
 *
 */

public class FindMaximumSubarrayDP {

    /**
     * 若已知A[1…j的最大子数组，基于如下性质将解扩展为A[1…j+1]的最大子数组：
     * 1、A[1…j+1]的最大子数组要么是A[1…j]的最大子数组
     * 2、要么是某个子数组A[i…j+1](1<=i<=j+1)。
     * 算法思路：让left为最大子数组的左端点，right为右端点
     * 若到x位置时sum<0，则到x为止的区间内不可能包含最大子数组，则应重置sum=0
     * 另i=x+1,重新查找
     *
     * **程序最后要注意调整left的值，经过查找之后，有可能left落在了right的右面（当max靠前时会出现这种情况）。
     *
     */
    static int[] findMaximumSubarrayDP(int[] A, int low, int high) {
        int sum = 0;
        int left = low;
        int right = low;
        int max = 0;

        // 不包含数组里全是负数的情况
        for (int i = low; i <= high; i++) {
            sum += A[i];
            //sum > max，更新max，更新right
            if (sum > max) {
                max = sum;
                right = i;
            }
            //sum < 0，重置sum，更新i
            else if (sum < 0) {
                sum = 0;
                left = i + 1;
            } else
                continue;
        }

        if (left > right)
            for (left = right; max - A[left] >= 0 && left > 0; left--)
                ;
        int[] result = {left, right, max};
        return result;
    }

    public static void main(String[] args){
        int[] A = {20, -3, -25, 13, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        int[] result = findMaximumSubarrayDP(A, 0, A.length - 1);
        System.out.println("最大子数组的位置为从 " + (result[0] + 1) + " 到 " + (result[1] + 1) + ",其和为 " + result[2]);
    }
}
