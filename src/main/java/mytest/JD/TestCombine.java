package mytest.JD;

/**
 * Created by safe on 2017-04-07.
 */

public class TestCombine {
    public static boolean isSum(int a[], int b[], int n, int s) {
        int ret = 0;
        for (int i = 0; i < n; i++) {
            ret += a[b[i]];
        }
        return s == ret;
    }

    // a：初始数组
// n：a包含的元素个数
// m：选出元素的个数
// b：存放结果的数组
// bn：数组b的元素个数，等于m
    public static void combine(int a[], int n, int m, int b[], int bn, int s) {
        for (int i = n; i >= m; i--) {
            b[m - 1] = i - 1;
            System.out.println("数据为m"+m+"数据b[m - 1]"+b[m - 1]);
            if (m > 1) { // 一共要选m个数，故m = 1时才结束递归
                combine(a, i - 1, m - 1, b, bn, s);

            } else {
//              for (int j = bn - 1; j >= 0; j--) {
//                  System.out.println("判断之前数据为" + a[b[j]]);
//
//              }
//
                if (isSum(a, b, bn, s)) {
                    for (int j = bn - 1; j >= 0; j--) {
                        System.out.println("数据为" + a[b[j]]);

                    }
                    System.out.println("数据为空");
                }
            }

        }
    }

    // 从包含n个元素的数组a中任选m个数，使其各为s，输出所有的组合
    public static void combine(int a[], int n, int m, int s) {
        int b[] = new int[m];
        combine(a, n, m, b, m, s);
    }

    public static void main(String[] args) {

        int[] asjy = {9483, 9886, 9543, 9750, 8645, 8958, 8725, 8629, 9770, 9756, 8890, 8943, 8639, 9365, 8190,
                9468};
        combine(asjy, 16, 4, new int[4], 4, 46437);
        combine(asjy, args.length, 5, new int[5], 5, 46437);


    }

}
