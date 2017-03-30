package mytest.datastructure;

import java.util.ArrayList;

/**
 * Created by safe on 2017/3/8.
 *
 *
 *
 *
 */
public class Find {

        public boolean Find0(int target, int [][] array) {
            boolean flag=false;
            int low=0;
            int m=array.length-1;
            int n=array[0].length-1;
            int high=n;
            while (low<=high){
                int mid=(high-low)>>1;
                if (array[mid][mid*n/m]<target){
                low=mid+1;
                }else if (array[mid][mid*n/m]>target){
                high=mid-1;
                }


                flag=true;
            }

            return  flag;
        }

    public boolean Find1(int target, int [][] array) {



        int m=array.length;
        int n=array[0].length-1;
        for( int i=0;i<m;i++){
            int low=0;
            int high=n;

            while(low<=high){

                int mid=low+(high-low)/2;
                if(array[i][mid]>target)
                    low=mid+1;
                    else if(array[i][mid]<target)
                    high=mid-1;
                else
                    return true;

            }

        }
        return false;
    }



    /*
    * 1.记录总共有多少空格
    * 2.扩展StringBuffer 元长度加%20长度
    * 3.从后向前搜索并移动字符
    *
    *
    * */
        public String replaceSpace(StringBuffer str) {
            int spNum=0;
            int strLen=str.length();
            for (int i=0;i<strLen;i++){
                if (str.charAt(i)==' ')
                    spNum++;
            }
            int strLenNew=strLen+2*spNum;
            str.setLength(strLenNew);
            int p0=strLen-1;//原来的数组指针
            int p1=strLenNew-1;//新扩展的数组指针

            while (p0>=0 && p1>=0) {
                if (str.charAt(p0)==' '){
                    p0--;
                    str.setCharAt(p1--,'0');
                    str.setCharAt(p1--,'2');
                    str.setCharAt(p1--,'%');
                }else {
                    str.setCharAt(p1--,str.charAt(p0--));
                }
            }

            return str.toString();
        }


    public ArrayList<Integer> printListFromTailToHead(ListNode l) {
        ArrayList<Integer> a=new ArrayList<>();
        ListNode p=null;
        ListNode q=p;

        while (l!=null) {
            q=l.next;
            l.next=p;
            p=l;
            l=q;
        }
        while (p!=null) {
            a.add(p.val);
            p=p.next;
        }


        return a;
    }




    ArrayList<Integer> output = new ArrayList<>();
public ArrayList<Integer> printListFromTailToHead0(ListNode l) {
    //ArrayList<Integer> output = new ArrayList<>();
while (l!=null){
    //output.add(0,l.val);
    printListFromTailToHead0(l.next);
    output.add(l.val);
    //l=l.next;
}
    return output;
}
    public static void main(String[] args){

        //int[][] a={{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        //int[][] b=new int[0][0];

        //System.out.println(b.length);
        Find f=new Find();
        //System.out.println(f.Find0(6,b));
        //StringBuffer sb=new StringBuffer();
        //sb.append("How are you  ");
        //System.out.println(sb.length());
        //System.out.println(f.replaceSpace(sb));
        ListNode ln=new ListNode(0);
        ListNode pl=ln;
        for (int i = 1; i <10 ; i++) {
            ListNode p=new ListNode(i);
            pl.next=p;
            pl=pl.next;
        }

        ListNode p0=ln;

        for (int i = 0; i <10 ; i++) {
          System.out.println(  p0.val);
            p0=p0.next;
        }

       System.out.println( f.printListFromTailToHead0(ln));
    }
}
