package mytest.hikvision;


import java.util.Stack;

/**
 * 实现拓扑排序
 * @author win7
 *
 */
public class TopologicalSort {
    /**
     * 开始初始化入度为零的节点
     * @param graph
     * @param indegree
     * @param n
     */
    public static void findIndegree(int [][] graph,int [] indegree,int n){
        for(int i=0;i<n;i++){
            indegree[i]=0;
            for(int j=0;j<n;j++){
                if(graph[j][i]>0){
                    indegree[i]++;
                }
            }
        }
    }
    /**
     * 拓扑排序
     * 算法思想：
     * （1）在有向图中选择一个没有前驱的顶点且输出。
     * （2）从图中删除该顶点和所有已他为尾的弧。
     * 重复上面两，直至所有的顶点均输出，或者当图中不存在无前驱的顶点为止，这时候说明图中有环。
     * @param graph 矩阵表示的有向图
     * @param n     有向图中节点个数
     */
    public static void topologicalSort(int [] [] graph,int n){
        int [] indegree=new int[n];
        findIndegree(graph,indegree,n);
        Stack<Integer> s=new Stack<Integer>();
        for(int i=0;i<n;i++){
            if(indegree[i]==0){
                s.push(i);
            }
        }
        while(!s.empty()){
            int start= s.pop();
            System.out.println("输出一个0入度节点："+(start+1));
            for(int i=0;i<n;i++){
                if(graph[start][i]>0){
                    indegree[i]--;
                    if(indegree[i]==0){
                        s.push(i);
                    }
                }
            }
        }

    }

    public static void main(String [] args){
        int [][] graph={
                {-1,1,-1,-1},
                {-1,-1,1,1},
                {-1,-1,-1,-1},
                {-1,-1,-1,-1}

        };
        TopologicalSort.topologicalSort(graph, 4);

    }

}
