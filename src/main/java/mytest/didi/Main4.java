package mytest.didi;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main4 {
    public static void main(String[] args)   {
        Scanner  in = new Scanner(System.in);
        List<Integer> a=new ArrayList<>();
        while (in.hasNextInt()) {
            a.add(in.nextInt());
        }
        in.close();
        int k=a.get(a.size()-1);

        int[] nums=new int[a.size()-1];
        for (int i = 0; i <a.size()-1 ; i++) {
            nums[i]=a.get(i);
        }

        PriorityQueue<Integer> q = new PriorityQueue<Integer>(k);
        for (int i : nums) {
            q.offer(i);
            if (q.size() > k) {
                q.poll();
            }
        }
        System.out.println(q.peek());


    }



}



