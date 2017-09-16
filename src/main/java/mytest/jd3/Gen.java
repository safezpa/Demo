package mytest.jd3;

import java.util.*;

public class Gen {
     static class Out{
        int maxlen;
        int count;
    }

    public  Out maxlen(String a) {
        Out out=new Out();
            char array[] = a.toCharArray();
            int n = a.length();
            int flagArr[] = new int[n];
            Stack<Integer> stack = new Stack<Integer>();
            for (int i = 0; i < n; i++) {
                if (array[i] == '(') {
                    stack.push(i);
                }else if (array[i] == ')' && !stack.isEmpty()){
                    int j = stack.pop();
                    flagArr[i] = 1;
                    flagArr[j] = 1;
                }
            }
            int maxLen = 0;
            int count = 0;
            int tempLen = 0;
            for (int i = 0; i < n; i++) {
                if (flagArr[i] == 1) {
                    tempLen ++;
                }else {
                    tempLen = 0;
                }
                if (tempLen > maxLen) {
                    maxLen = tempLen;
                    count = 1;
                }else if (tempLen == maxLen) {
                    count++;
                }
            }
            out.maxlen=maxLen;
            out.count=count;
            return  out;
        }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        in.close();
        int max=0;
        Gen gen=new Gen();
        Out out=new Out();
        List<String> result = generateParentheses(a.length()/2);
        for (String string : result) {
            System.out.println(string);
            if (a.equals(string)) System.out.println("ll");
                out= gen.maxlen(string);
            System.out.println(out.maxlen);
            if (out.maxlen>max) max=out.maxlen;
        }
        System.out.println(out.count);

    }

    public static List<String>generateParentheses(int pairs) {
        List<String> result = new ArrayList<String>();
        generate(pairs, pairs, "", result);
        return result;
    }

    public static void generate(int leftNum, int rightNum, String s,
                                List<String> result) {
        if (leftNum == 0 && rightNum == 0) {
            result.add(s);
        }
        if (leftNum > 0) {
            generate(leftNum - 1, rightNum, s + '(', result);
        }
        if (rightNum > 0 && leftNum < rightNum) {
            generate(leftNum, rightNum - 1, s + ')', result);
        }
    }

}
