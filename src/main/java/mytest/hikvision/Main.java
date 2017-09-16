package mytest.hikvision;

import java.util.*;

public class Main{
    static int MAX_VERTEXNUM = 100;
    static int [] visited = new int[MAX_VERTEXNUM];
    public static void main(String [] args){
        Graph G = new Graph();
        creatGraph(G);
        output(G);
        for(int i=0;i<G.vertex_num;i++)
            visited[i]=0;
        System.out.println("DFS遍历的结果是：");
        dfs(G,0);//从G.node_list[0]开始DFS遍历
    }
    static void creatGraph(Graph G){
        Scanner in = new Scanner(System.in);
        System.out.println("请输入顶点数v和边数e,（v e）:");
        G.vertex_num = in.nextInt();
        G.edge_num= in.nextInt();
        System.out.println("请输入各顶点信息：");
        for(int i=0;i<G.vertex_num;i++){
            G.node_list[i] = new VertexNode();
            G.node_list[i].date = in.next();
            G.node_list[i].first_edge = null;  //很重要
        }
        System.out.println("请输入各边信息(以空格隔开)：");
        for(int i=0;i<G.edge_num;i++){
            EdgeNode p = new EdgeNode();
            String str1 = in.next();
            String str2 = in.next();
            int v1 = locateVex(G,str1);
            int v2 = locateVex(G,str2);
            p.vertex = v1;
            p.next = G.node_list[v2].first_edge;
            G.node_list[v2].first_edge = p;
            EdgeNode q = new EdgeNode();//如果是有向图，则不存在下面的代码（上面的代码的存储顺序可能有变化）
            q.vertex = v2;
            q.next = G.node_list[v1].first_edge;
            G.node_list[v1].first_edge = q;
        }
    }
    static int locateVex(Graph G,String s){
        for(int i=0;i<G.vertex_num;i++){
            if(G.node_list[i].date.equals(s))
                return i;
        }
        return -1;
    }
    static void output(Graph G){
        System.out.println("输出邻接表存储情况：");
        EdgeNode p = new EdgeNode();
        for(int i=0;i<G.vertex_num;i++){
            System.out.print(G.node_list[i].date);
            p = G.node_list[i].first_edge;
            while(p!=null){
                System.out.print("->"+G.node_list[p.vertex].date);
                p = p.next;
            }
            System.out.println();
        }
    }
    static void dfs(Graph G,int k){
        System.out.println(G.node_list[k].date);
        visited[k]=1;
        EdgeNode p = new EdgeNode();
        p = G.node_list[k].first_edge;
        while(p!=null){
            if(visited[p.vertex]!=1)
                dfs(G,p.vertex);
            p = p.next;
        }
    }
}
//顶点存储
class VertexNode{
    String date;
    EdgeNode first_edge = new EdgeNode();

}
//边存储
class EdgeNode{
    int vertex;
    EdgeNode next;
}
//图存储
class Graph{
    VertexNode [] node_list = new VertexNode[100];
    int vertex_num,edge_num;
}




