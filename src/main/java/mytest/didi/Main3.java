package mytest.didi;

import java.util.*;

public class Main3 {
    public static void main(String[] args)   {
        Scanner  in = new Scanner(System.in);
        List<Integer> a=new ArrayList<>();
        while (in.hasNextInt()) {
            a.add(in.nextInt());
        }
        in.close();

        int[] nums=new int[a.size()-1];
        for (int i = 0; i <a.size()-1 ; i++) {
            nums[i]=a.get(i);
        }

        Arrays.sort(nums);
        System.out.println(nums[nums.length - a.get(a.size()-1)]);



    }



}



