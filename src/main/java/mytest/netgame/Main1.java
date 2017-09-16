package mytest.netgame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 *
 * 5
 * 02/12/2016 00:00:00 10
 * 02/18/2016 12:00:00 45
 * 02/18/2016 23:59:59 8
 * 02/19/2016 08:00:15 20
 * 02/22/2016 13:00:00 31
 * 4
 * 01/01/2014 00:00:00 01/01/2017 00:00:00
 * 11/11/2016 11:11:11 02/14/2017 00:05:20
 * 02/12/2016 00:00:00 02/18/2016 23:00:00
 * 02/18/2016 12:00:00 02/18/2016 12:00:00
 */
public class Main1 {
    public static void main(String[] args) throws ParseException {
        Main1 main=new Main1();
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss");
        Scanner in = new Scanner(System.in);
        int n=Integer.parseInt(in.nextLine());
        SegNode[] a=new SegNode[n];
        for (int i = 0; i <n ; i++) {
            String time=in.nextLine();
            String[] chars=time.split(" ");
            String str =chars[0]+" "+chars[1];
            int sum=Integer.parseInt(chars[2]);
            long millionSeconds = sdf.parse(str).getTime();//毫秒
            a[i]=new SegNode(i,i,millionSeconds,millionSeconds,sum);
        }

        SegNode root=new SegNode(0,a.length-1,0,0,0);
        main.build(root,a);
        int m=Integer.parseInt(in.nextLine());
        int[] out=new int[m];
        for (int i = 0; i <m ; i++) {
            String time=in.nextLine();
            String[] chars=time.split(" ");
            String str1 =chars[0]+" "+chars[1];
            String str2 =chars[2]+" "+chars[3];
            long l = sdf.parse(str1).getTime();//毫秒
            long r = sdf.parse(str2).getTime();//毫秒
            out[i]=main.query(root,l,r);

        }
        in.close();
        for (int i = 0; i <m ; i++) {
            System.out.println(out[i]);
        }


    }


    private static class SegNode{
        int sum;
        long start;
        long end;
        int istart;
        int iend;
        SegNode lCh;
        SegNode rCh;

        public SegNode(int istart, int iend,long start,long end,int sum) {
            this.istart=istart;
            this.iend=iend;
            this.end=end;
            this.start=start;
            this.sum=sum;

        }
    }
    private void build(SegNode root, SegNode[] arr2, int istart, int iend){
        if (root==null) root=new SegNode(istart,iend,arr2[istart].start,arr2[iend].end,
                arr2[iend].sum+arr2[istart].sum);
        if (istart==iend) {
            root.sum =  arr2[istart].sum;
        }
        else {
            int mid=(istart+iend)/2;
            if (root.lCh==null) root.lCh=new SegNode(istart,iend,arr2[istart].start,arr2[iend].end,
                    arr2[iend].sum+arr2[istart].sum);
            build(root.lCh,arr2,istart,mid);
            if (root.rCh==null) root.rCh=new SegNode(istart,iend,arr2[istart].start,arr2[iend].end,
                    arr2[iend].sum+arr2[istart].sum);
            build(root.rCh,arr2,mid+1,iend);
            pushUp(root);
        }
    }
    private void build(SegNode root, SegNode[] arr2) {
        build(root,arr2,0,arr2.length-1);
    }

    private static void pushUp(SegNode p) {
        if (p.rCh==null || p.lCh==null) return;
        p.sum = p.lCh.sum+p.rCh.sum;
        p.start=p.lCh.start;
        p.end=p.rCh.end;
        p.istart=p.lCh.istart;
        p.iend=p.rCh.iend;
    }

    private int query(SegNode p,long a, long b){
        if (a<=p.start && p.end<=b){
            return p.sum;
        }

        long mid=(p.start+p.end)/2;
        int sum=0;
        if (a<=mid && p.lCh!=null)
            sum+=query(p.lCh,a,b);
        if (b>mid && p.rCh!=null)
            sum+=query(p.rCh,a,b);
        return sum;
    }



}



