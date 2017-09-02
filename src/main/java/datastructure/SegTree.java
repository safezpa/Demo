package datastructure;

import java.util.LinkedList;
import java.util.Queue;

public class SegTree {
    private static class SegNode{
        int sum;
        int max;
        int min;
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
    private static int size;
    public  SegTree(int n){
        size=4*n-1;

    }
    public static void main(String[] args){
        int[] arr0=new int[44];
        int[] arr2={2, 5, 1, 4, 9, 3};

        System.out.println("================================");
        SegTree segTree=new SegTree(44);
        SegNode root=new SegNode(0,44);
        segTree.build(root,arr0);
        System.out.println("============buildUp=============");
        levelTravel(root);
        for (int i = 0; i <arr2.length ; i++) {
            segTree.update(root,arr2[i],arr2[i], 1);
            levelTravel(root);
            System.out.println("=============Query==比"+arr2[i]+"大的================");
            System.out.println(segTree.query(root,arr2[i]+1,44));
        }

/*        segTree.update(root,0,5, 1);
        System.out.println("===========afterUpdate=============");
        levelTravel(root);
        System.out.println("=============Query==0-5================");
        System.out.println(segTree.query(root,0,5));
        System.out.println("=============afterQuery===========");
        levelTravel(root);
        System.out.println("=============Query==3-5================");
        System.out.println(segTree.query(root,3,5));
        System.out.println("=============afterQuery===========");
        levelTravel(root);
        System.out.println("=============Query==3-4================");
        System.out.println(segTree.query(root,3,4));
        System.out.println("=============afterQuery===========");
        levelTravel(root);
        System.out.println(segTree.query(root,4,4));*/
    }



    private static void pushUp(SegNode p) {
        if (p.rCh==null || p.lCh==null) return;
        p.sum = p.lCh.sum+p.rCh.sum;
        p.max = Integer.max(p.lCh.max,p.rCh.max);
        p.min = Integer.min(p.lCh.min,p.lCh.min);
    }
    private static void pushDown(SegNode p) {
        if (p.rCh==null || p.lCh==null) return;
        if (p.addMark>0) {
            p.rCh.addMark+=p.addMark;
            p.lCh.addMark+=p.addMark;
            p.rCh.sum+=(p.rCh.end-p.rCh.start+1)*p.addMark;
            p.lCh.sum+=(p.lCh.end-p.lCh.start+1)*p.addMark;
            p.rCh.max+=p.addMark;
            p.lCh.max+=p.addMark;
            p.rCh.min+=p.addMark;
            p.lCh.min+=p.addMark;
            p.addMark=0;
        }

    }
    /**
     *  构造求解区间sum的线段树
     *  function 构造以v为根的子树
     *  if v所表示的区间内只有一个元素
     *  v区间的最小值就是这个元素, 构造过程结束
     *  end if
     *  把v所属的区间一分为二，用w和x两个节点表示。
     *  标记v的左儿子是w，右儿子是x
     *  分别构造以w和以x为根的子树（递归）
     *  v区间的sum <- sum(w区间的sum,x区间的sum)
     *  end function
     */
    private void build(SegNode root,  int istart, int iend){
        if (root==null) root=new SegNode(istart,iend);
        root.addMark=0;//设置延迟标记域
        if (istart==iend)//叶子节点
            root.sum=root.max=root.min=0;//arr[istart];
        else {
            int mid=(istart+iend)/2;
            if (root.lCh==null) root.lCh=new SegNode(istart,mid);
            build(root.lCh,istart,mid);//递归构造左子树
            if (root.rCh==null) root.rCh=new SegNode(mid+1,iend);
            build(root.rCh,mid+1,iend);//递归构造右子树
            //根据左右子树根节点的值，更新当前根节点的值
            pushUp(root);
        }
    }
    private void build(SegNode root, int[] arr2) {
        build(root,0,44);//arr2.length-1);
    }
    /**
     * 区间更新
     * node 为线段树的结点类型，其中Left 和Right 分别表示区间左右端点
     * // Lch 和Rch 分别表示指向左右孩子的指针
     * void update(node *p, int a, int b){               // 当前考察结点为p，修改区间为(a,b]
     *         if (a <= p->Left && p->Right <= b){        // 如果当前结点的区间包含在修改区间内
     *              ...... // 修改当前结点的信息，并标上标记
     *              return;
     *         }
     *         Push_Down(p); // 把当前结点的标记向下传递
     *         int mid = (p->Left + p->Right) // 计算左右子结点的分隔点
     *         if (a < mid) update(p->Lch, a, b); // 和左孩子有交集，考察左子结点
     *         if (b > mid) update(p->Rch, a, b); // 和右孩子有交集，考察右子结点
     *         Update(p); // 维护当前结点的信息（因为其子结点的信息可能有更改）
     * }
     */
    private void update(SegNode p,int a, int b, int addVal){
        //当前节点区间包含在更新区间内
        if (a<=p.start && p.end<=b){
            p.addMark+=addVal;
            p.sum+=(p.end-p.start+1)*addVal;
            p.max+=addVal;
            p.min+=addVal;
            return;
        }
        //延迟标记向下传递
        pushDown(p);
        //更新左右孩子节点
        int mid=(p.start+p.end)/2;
        if (a<mid && p.lCh!=null)
        update(p.lCh,a,b,addVal);
        if (b>mid && p.rCh!=null)
        update(p.rCh,a,b,addVal);
        //根据左右子树的值回溯更新当前节点的值
        pushUp(p);
    }
    /**
     *  区间查询
     *  // node 为区间树的结点类型，其中Left 和Right 分别表示区间左右端点
     *  // Lch 和Rch 分别表示指向左右孩子的指针
     *  void Query(node *p, int a, int b){ // 当前考察结点为p，查询区间为(a,b]
     *
     *      // 如果当前结点的区间包含在查询区间内
     *      if (a <= p->Left && p->Right <= b){
     *          ...... // 更新结果
     *          return;
     *      }
     *
     *      Push_Down(p);                     // 延迟标记向下传递
     *      int mid = (p->Left + p->Right)/2; // 计算左右子结点的分隔点
     *      if (a < mid) Query(p->Lch, a, b); // 和左孩子有交集，考察左子结点
     *      if (b > mid) Query(p->Rch, a, b); // 和右孩子有交集，考察右子结点
     *
     *  }
     *
     */

    private int query(SegNode p,int a, int b){

        //当前节点区间包含在更新区间内
        if (a<=p.start && p.end<=b){
            return p.sum;
            //return p.max;
            //return p.min;
        }
        //延迟标记向下传递
        pushDown(p);
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
    public static void levelTravel(SegNode root){
        if(root==null)return;
        Queue<SegNode> q=new LinkedList<SegNode>();
        q.add(root);
        while(!q.isEmpty()){
            SegNode temp =  q.poll();
            System.out.print(temp.start+" "+temp.sum+" "+temp.end+" "+temp.addMark+"--- ");
            if(temp.rCh!=null) q.add(temp.rCh);
            if(temp.lCh!=null) q.add(temp.lCh);
        }
        System.out.println("\n");
    }


}
