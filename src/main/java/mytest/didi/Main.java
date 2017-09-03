package mytest.didi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by safe on 2017-04-13.
 */


public class Main {


    //网格点
    static class Point
    {
        //属性x
        int x;
        //属性y
        int y;
        //...
        int value;

    }

    static Stack<Point> s=new Stack<>();

    /**
     *
     * @param imageData 图像点对象数组
     * @param i 当前点x
     * @param j 当前点y
     * @param row 图像高
     * @param col 图像宽
     * @param k 图像分割数
     */
    static void fourNeighborhoodLabels(Point[][] imageData,int i,int j,int row,int col,int k){
        if (imageData[i][j].value==1){
            imageData[i][j].value=k;

            //up
            if (i-1>=0) {
                if (imageData[i-1][j].value == 1) {
                    imageData[i][j].value=k;
                    s.push(imageData[i-1][j]);
                }
            }

            //right
            if (j+1<=col-1) {
                if (imageData[i][j+1].value == 1) {
                    imageData[i][j].value=k;
                    s.push(imageData[i][j+1]);
                }
            }

            //down
            if (i+1<=row-1) {
                if (imageData[i+1][j].value == 1) {
                    imageData[i][j].value=k;
                    s.push(imageData[i+1][j]);
                }
            }

            //left
            if (j-1>=0) {
                if (imageData[i][j-1].value == 1) {
                    imageData[i][j].value=k;
                    s.push(imageData[i][j-1]);
                }
            }

        }
    }
    //seed-Filling 算法
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int row=4;
        int col=6;
        int[][] imageData = new int[][]{
                {1,0,0,1,1,1},
                {1,1,0,1,1,0},
                {1,0,1,0,0,0},
                {1,0,0,1,0,0}
        };
        Point[][] p=new Point[row][col];
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <col ; j++) {
                p[i][j]=new Point();
                p[i][j].x=i;
                p[i][j].y=j;
                p[i][j].value=imageData[i][j];
                //System.out.println(i+" "+j+" "+p[i][j].value);
            }

        }

        Point temp=new Point();
        int k=1;
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j <col ; j++) {
                if (p[i][j].value==1) k++;
                //广度优先搜索
                fourNeighborhoodLabels(p,i,j,row,col,k);
                while (!s.isEmpty()){
                    temp=s.pop();
                    fourNeighborhoodLabels(p,temp.x,temp.y,row,col,k);
                }
                System.out.print(p[i][j].value+" ");
            }
            System.out.print("\n");
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("2017-09-03");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }




}









