package mytest.ctrip3;

import java.util.Arrays;

/**
 * Created by safe on 2017-04-11.
 *
 * 八数码问题
 * 八数码问题也称为九宫问题。在3×3的棋盘，摆有八个棋子，
 * 每个棋子上标有1至8的某一数字，不同棋子上标的数字不相同。
 * 棋盘上还有一个空格，与空格相邻的棋子可以移到空格中。
 *
 * 要求解决的问题是：给出一个初始状态和一个目标状态，
 * 找出一种从初始转变成目标状态的移动棋子步数最少的移动步骤。
 * 所谓问题的一个状态就是棋子在棋盘上的一种摆法。棋子移动后，状态就会发生改变。
 * 解八数码问题实际上就是找出从初始状态到达目标状态所经过的一系列中间过渡状态。
 *
 *
 * 平时，我们所使用的搜索算法大多数都是广搜（BFS）和深搜（DFS），
 * 其实他们都是盲目搜索，相对来说搜索的状态空间比较大，效率比较低。
 * 而启发式搜索则相对的智能一些，它能对所有当前待扩展状态进行评估，
 * 选出一个最好的状态、最容易出解的状态进行搜索。
 * 这样，我们就可以避免扩展大量的无效状态，从而提高搜索效率
 */


public class Main {
    public static int MAXSTATE = 1000000;

    static class state{//状态类，这个类的每个对象存储一种状态
        int[] state = new int[9];
        public state(int[] state) {
            this.state = state;
        }
    }

    static state[] st = new state[MAXSTATE];//状态数组
    static state goal;//目标状态
    static int[] dist = new int[MAXSTATE];//距离数组

    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};


    static int front = 1,rear=2;

    //
    static int bfs(){
        init_lookup_table();
        while(front < rear){
            state s = st[front];
            if(Arrays.equals(s.state,goal.state)){//如果当前状态跟目标状态相同
                return front;//返回当前指针
            }

            int z;
            for(z=0;z<9;z++){if(0==s.state[z]){break;}}//找出当前状态0的位置
            int x = z/3;
            int y=z%3; //获取行列号(0-2)

            for(int d=0;d<4;d++){
                System.out.println("进入方位循环");
                int newx = x + dx[d];//移动目标的横坐标
                int newy = y + dy[d];//移动目标的纵坐标
                int newz = newx*3 + newy;//移动目标在下一状态的位置
                if(newx>=0 && newx<3 && newy>=0 && newy<3){//如果移动合法
                    System.out.println("移动合法"+newx+"||"+newy);
                    int[] temp = Arrays.copyOf(s.state, 9);
                    st[rear] = new state(temp);
                    st[rear].state[newz] = s.state[z];
                    st[rear].state[z] = s.state[newz];
                    dist[rear] = dist[front]+1;
                    System.out.println("在第"+dist[rear]+"层");
                    if(try_to_insert(st[rear])){
                        rear++;
                    }
                }else{
                    System.out.println("移动不合法"+newx+"||"+newy);
                }
            }
            System.out.println(front+"--------------------------");
            front++;
        }
        return 0;
    }

    /*
    static boolean try_to_insert(state t){
        for(int i=1;i<rear;i++){
            if(Arrays.equals(t.state, st[i].state)){
                return false;
            }
        }
        return true;
    }
    */
    static boolean[] vis = new boolean[362880];
    static int[] fact = new int[9];


    static boolean try_to_insert(state t){
        int code = 0;
        for(int i=0;i<9;i++){
            int cnt=0;
            for(int j=i+1;j<9;j++){
                if(t.state[j]<t.state[i]) cnt++;
            }
            code += fact[8-i] * cnt;
        }
        if(vis[code]){
            return false;
        }else{
            vis[code] = true;
            return true;
        }
    }


    static void init_lookup_table(){
        fact[0] = 1;
        for(int i=1;i<9;i++) fact[i] = fact[i-1]*i;
    }

    public static void main(String[] args) {
        dist[1] = 0;
        st[1] = new state(new int[]{2,6,4,1,3,7,0,5,8});
        goal = new state(new int[]{8,1,5,7,3,6,4,0,2});
        System.out.println("移动了"+dist[bfs()]+"步");
    }
}
