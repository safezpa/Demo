package mytest.dj;

/**
 * Created by safe on 2017-04-07.
 */

import java.util.*;

public class Main {
    //方向
    int dirX[] = { 0, 0, -1, 1 };
    int dirY[] = { -1, 1, 0, 0 };

    //点
    static class Point{
        int x;
        int y;
        int step;
        Point(int x,int y,int step){
            this.x=x;
            this.y=y;
            this.step=step;
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Integer> list=new ArrayList();

        while (in.hasNextInt()) {
            int n = in.nextInt();
            int m = in.nextInt();
            boolean[][] a=new boolean[n][m];
            boolean[][] visit=new boolean[n][m];
            Point p0=new Point(in.nextInt(),in.nextInt(),0);
            Point p1=new Point(in.nextInt(),in.nextInt(),0);
            for (int i = 0; i <n ; i++) {
                for (int j = 0; j <m ; j++) {
                    int k=in.nextInt();
                    if (k==1){
                        a[i][j]=false;
                    }else if (k==2){
                        a[i][j]=false;
                        a[i-1][j]=false;
                        a[i+1][j]=false;
                        a[i][j-1]=false;
                        a[i][j+1]=false;
                    }else if (k==3){
                        a[i][j]=false;
                        a[i-1][j]=false;
                        a[i+1][j]=false;
                        a[i][j-1]=false;
                        a[i][j+1]=false;
                        a[i-2][j]=false;
                        a[i+2][j]=false;
                        a[i][j-2]=false;
                        a[i][j+3]=false;
                    }else {
                        a[i][j]=true;
                    }

                    visit[i][j] = false;
                }
            }
            Queue<Point> q = new LinkedList<>();
            q.offer(p0);
            boolean isOK=false;
            Main main=new Main();
            //main.bfsSearch(q,p1,a,visit,n,m,isOK);
            list.add(main.bfsSearch(q,p1,a,visit,n,m,isOK));
            System.out.println(a[1][1]);
        }
        in.close();
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
        }
    }

    int  bfsSearch(Queue<Point> q, Point end, boolean[][] map, boolean[][] visit, int n,int m,
                   boolean isOK) {
        int out=-1;
        while ( q.size()!=0 &&  !isOK  ) {
            Point t = q.poll();
            if (t.x == end.x && t.y == end.y) {
                isOK = true;
                out=t.step;
                return out;
            }
            for (int i = 0; i < 4; i++) {
                int nextX = t.x + dirX[i];
                int nextY = t.y + dirY[i];
                if (nextX < 0 || nextY < 0 || nextX >= n || nextY >= m || !map[nextX][nextY])
                    continue;
                if (!visit[nextX][nextY]) {
                    visit[nextX][nextY] = true;
                    q.offer(new Point(nextX, nextY, t.step + 1));
                }
            }
        }

        return out;
    }
}

