package mytest.didi;

import java.util.*;

public class Test3 {

    static class Sub {
        public int l;
        public int r;
        public Sub(int start, int end) {
            this.l = start;
            this.r = end;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < a.length; i++) {
                a[i] = in.nextInt();
            }
        System.out.println(sum(a));

    }
    public static int sum(int[] a) {

        List<Sub> subs = new ArrayList<>();
        int[][] ints = new int[a.length][a.length];
        for (int i = 0; i < ints.length; i++) {
            for (int j = i; j < ints.length; j++) {
                if (i == j) {
                    ints[i][j] = a[i];
                } else {
                    ints[i][j] = ints[i][j - 1] ^ a[j];
                }
                if (ints[i][j] == 0) {
                    subs.add(new Sub(i, j));
                }
            }
        }

        Collections.sort(subs, new Comparator<Sub>() {
            @Override
            public int compare(Sub o1, Sub o2) {
                if (o1.l < o2.l) {
                    return -1;
                } else if (o1.l == o2.l) {
                    if (o1.r < o2.r) {
                        return -1;
                    } else if (o1.r == o2.r) {
                        return 0;
                    } else {
                        return 1;
                    }

                } else {
                    return 1;
                }
            }
        });
        int max = 0; int num = 0;
        for (int i = 0; i < subs.size(); i++) {
            num = 0;  int left = -1, right = -1;
            for (int j = i; j < subs.size(); j++) {
                Sub sub = subs.get(j);
                if (sub.l != left && sub.l > right) {
                    num++; left = sub.l;right = sub.r;
                }
            }
            if (num > max)   max = num;
        }
       return max;
    }

}