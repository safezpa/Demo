package mytest.jd3;

import java.util.Scanner;
import java.util.Stack;

//(((())))
public class Main {
    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
        String s=in.nextLine();
        in.close();
        int cnt=0,ans=1;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                cnt++;
            } else {
                ans *= cnt;
                cnt--;
            }
        }
        System.out.println(ans);

    }
    public boolean check(String s, int n) {
        Stack stack=new Stack();
        int i=0;
        while(i<n){
            if(s.charAt(i)=='('){
                stack.push(s.charAt(i));
            }
            if(s.charAt(i)==')'&& !stack.isEmpty()){
                stack.pop();
            }
            i++;
            if(i<n-1 && s.charAt(i)==')' && stack.isEmpty()){
                return false;
            }
        }
        return stack.isEmpty();
    }

}



