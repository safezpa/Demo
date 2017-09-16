package mytest.didi;

import java.util.LinkedList;
import java.util.Queue;

public class SegTree {
    private static class SegNode{
        int sum;
        int max;
        int min;
        int xor;
        int xorSum0;
        int xorSum1;
        int addMark;//延迟标记
        int start;
        int end;
        SegNode lCh;
        SegNode rCh;

        public SegNode(int istart, int iend) {
            start=istart;
            end=iend;
        }
    }

    public static void main(String[] args){
        int[] arr0=new int[10];
        int[] arr2={1,1,1,1};
        System.out.println("================================");
        SegTree segTree=new SegTree();
        SegNode root=new SegNode(0,arr2.length-1);
        segTree.build(root,arr2);
        int out=0;
        System.out.println(levelTravel(root));
    }

    private int query(SegNode p,int a, int b){

        //当前节点区间包含在更新区间内
        if (a<=p.start && p.end<=b){
            return p.xorSum0;
            //return p.max;
            //return p.min;
        }
        //延迟标记向下传递

        //更新左右孩子节点
        int mid=(p.start+p.end)/2;
        int sum=0;
        int max=0;
        int min=Integer.MAX_VALUE;
        if (a<=mid && p.lCh!=null)
            sum+=query(p.lCh,a,b);
        if (b>mid && p.rCh!=null)
            sum+=query(p.rCh,a,b);
        //根据左右子树的值回溯更新当前节点的值
        return sum;
    }




    private void build(SegNode root, int[] arr2, int istart, int iend){
        if (root==null) root=new SegNode(istart,iend);
        if (istart==iend) {//叶子节点
            root.xor = arr2[istart];
            //if (root.xor==0) root.xorSum0=1;
            //if (root.xor==1) root.xorSum1=1;
        }
        else {
            int mid=(istart+iend)/2;
            if (root.lCh==null) root.lCh=new SegNode(istart,mid);
            build(root.lCh,arr2,istart,mid);//递归构造左子树
            if (root.rCh==null) root.rCh=new SegNode(mid+1,iend);
            build(root.rCh,arr2,mid+1,iend);//递归构造右子树
            //根据左右子树根节点的值，更新当前根节点的值
            //if (root.rCh==null || root.lCh==null) return;
            root.xor = root.lCh.xor ^ root.rCh.xor;
        }
    }
    private void build(SegNode root, int[] arr2) {
        build(root,arr2,0,arr2.length-1);
    }




    public static int levelTravel(SegNode root){
        if(root==null) return 0;
        int k=0;
        Queue<SegNode> q=new LinkedList<SegNode>();
        q.add(root);
        while(!q.isEmpty()){
            SegNode temp =  q.poll();
            //System.out.print(temp.start+" "+temp.xor+" "+temp.end+" "+temp.xorSum0+"--- ");
            if (temp.xor==0) k++;
            if(temp.rCh!=null) q.add(temp.rCh);
            if(temp.lCh!=null) q.add(temp.lCh);
        }
        return k;
    }


}


