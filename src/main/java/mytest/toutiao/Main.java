package mytest.toutiao;

import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        Map<Integer,Integer> tm=new TreeMap<>();
        for (int i = 1; i <=n ; i++) {
            tm.put(in.nextInt(),in.nextInt());
        }
        in.close();
        Iterator it=tm.entrySet().iterator();
        List<Point> pointList = new ArrayList<>();
        while (it.hasNext()){
            Map.Entry ent=(Map.Entry)it.next();
            //System.out.println(ent.getKey()+" "+ent.getValue());
            pointList.add(new Point((Integer) ent.getKey(),(Integer) ent.getValue()));
        }
        find(pointList,0,n-1);
    }

    private static void find(List<Point> pointList, int start, int end) {
        int maxy=pointList.get(start).y;
        int maxi=start;

        while (start<end){
            if (pointList.get(++start).y>=maxy){
                maxy=pointList.get(start).y;
                maxi=start;
            }
        }
        System.out.println(pointList.get(maxi).x+" "+pointList.get(maxi).y);
        if (maxi+1<=end)
        find(pointList,maxi+1,end);
    }


    static class Point{
        int x;
        int y;
        Point(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

}
