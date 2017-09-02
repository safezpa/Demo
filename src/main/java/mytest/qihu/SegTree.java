package mytest.qihu;


public class SegTree {


    public static void main(String[] args){
        int[] arr2={2, 5, 1, 4, 9, 3};
        System.out.println("================================");
     }

    private  int size;
    private  SegTreeNode[] segTreeNodes;
    public SegTree(int n) {
        size = n*4-1 ;
        segTreeNodes = new SegTreeNode[size]; // 抛弃掉0点 方便index的扩张 e.g 1的子节点为2， 3 2的子节点为3 4
        for (int i = 0; i <segTreeNodes.length ; i++) {
            segTreeNodes[i]=new SegTreeNode();
        }

    }


    /**
     *  构造求解区间最小值的线段树
     *  function 构造以v为根的子树
     *  if v所表示的区间内只有一个元素
     *  v区间的最小值就是这个元素, 构造过程结束
     *  end if
     *  把v所属的区间一分为二，用w和x两个节点表示。
     *  标记v的左儿子是w，右儿子是x
     *  分别构造以w和以x为根的子树（递归）
     *  v区间的最小值 <- min(w区间的最小值,x区间的最小值)
     *  end function
     */

    /**
     * 构建区间树
     * @param root      当前区间树根节点的下标
     * @param arr       用来构建区间树的数组
     * @param istart    数组的开始位置
     * @param iend      数组的结束位置
     */
    private void buildMin(int root, int[] arr, int istart, int iend){
        segTreeNodes[root].addMark=0;//设置延迟标记域
        if (istart==iend) {//叶子节点
            segTreeNodes[root].min = arr[istart];
        }
        else {
            int mid=(istart+iend)/2;
            buildMin(root*2+1,arr,istart,mid);//递归构造左子树
            buildMin(root*2+2,arr,mid+1,iend);//递归构造右子树
            //根据左右子树根节点的值，更新当前根节点的值
            segTreeNodes[root].min=Integer.min(segTreeNodes[root*2+1].min,
                    segTreeNodes[root*2+2].min);
        }
    }



    /**
     * 当前节点的标志域向孩子节点传递
     * @param root 当前区间树的根节点的下标
     */
    private void push_Down(int root){
        if (segTreeNodes[root].addMark!=0){
            //设置左右孩子节点的标志域，因为孩子节点可能被多次延迟标记又没有向下传递
            //所以是 “+=”
            segTreeNodes[2*root+1].addMark+=segTreeNodes[root].addMark;
            segTreeNodes[2*root+2].addMark+=segTreeNodes[root].addMark;
            //根据标志域设置孩子节点的值，此处是区间最小值，所以区间每个值加上后，区间最小值也要加
            segTreeNodes[2*root+1].min+=segTreeNodes[root].addMark;
            segTreeNodes[2*root+2].min+=segTreeNodes[root].addMark;
            //清除标志域
            segTreeNodes[root].addMark=0;
        }
    }


    /**
     *  区间查询min
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
    /**
     * 区间查询最小值
     * [cur_start,cur_end]    当前节点代表的区间
     * [qstart,qend]    待查询的区间
     * @param root      当前区间树的根节点下标
     * @param cur_start    当前节点代表的区间开始
     * @param cur_end      当前节点代表的区间结束
     * @param qstart    查询区间开始位置
     * @param qend      查询区间结束位置
     * @return
     */
    private int queryMin(int root, int cur_start, int cur_end, int qstart, int qend){

        // 当前节点代表的区间不在查询区间内
        if (cur_end<qstart || cur_start>qend) return Integer.MAX_VALUE;
        // 当前节点包含在要查询区间内
        if (qstart<=cur_start && qend>=cur_end) return segTreeNodes[root].min;
        push_Down(root);// 延迟标记向下传递
        // 分别从左右子树查询，返回两者查询结果较小者
        int mid=(cur_start+cur_end)/2;
        return Integer.min(queryMin(root*2+1,cur_start,mid,qstart,qend),
                queryMin(root*2+2,mid+1,cur_end,qstart,qend));
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
    /**
     *  功能：更新线段树中某个区间内叶子节点的值
     *  root：当前线段树的根节点下标
     *  [cur_start, cur_end]: 当前节点所表示的区间
     *  [ustart, uend]: 待更新的区间
     *  addVal: 更新的值（原来的值加上addVal）
     * @param root
     * @param cur_start
     * @param cur_end
     * @param ustart
     * @param uend
     * @param addVal
     */
    private void updateMin(int root,int cur_start, int cur_end, int ustart, int uend, int addVal){

        //更新区间和当前节点区间没有交集
        if (cur_end<ustart || cur_start>uend) return;
        //当前节点区间包含在更新区间内
        if (cur_start>=ustart && cur_end<=uend){
            segTreeNodes[root].addMark+=addVal;
            segTreeNodes[root].min+=addVal;
            return;
        }
        //延迟标记向下传递
        push_Down(root);
        //更新左右孩子节点
        int mid=(cur_start+cur_end)/2;
        updateMin(root*2+1,cur_start,mid,ustart,uend,addVal);
        updateMin(root*2+2,mid+1,cur_end,ustart,uend,addVal);
        //根据左右子树的值回溯更新当前节点的值
        segTreeNodes[root].min=Integer.min(segTreeNodes[root*2+1].min,segTreeNodes[root*2+2].min);
    }

    static class SegTreeNode{
        int min;
        int max;
        int sum;
        int count;
        int addMark;//延迟标记
    }
}


