package mytest.ali;

/**
 *
 天猫国际每天都会卖出很多跨境商品，
 用户每次下单可能购买多个商品，
 购买总数小于10件，由于海关规定，
 每一个进入海关的箱子里面的商品总额不能超过2000元（否则不能清关）
 所以当用户下单总金额超过2000，必须使用多个箱子分开包装运输；
 现在为了节约运输成本，希望在满足海关的要求下，能够使用尽可能少的箱子。

 注：

 每个商品都有自己的单价，有特定的长宽高，所有商品都是长方体

 商品可以横放、竖放、侧放，但不用考虑斜放，但是长宽高各项总和必须都要小于等于箱子的长宽高

 假定目前天猫国际使用同一种规格的箱子

 boxLong,boxWidth,boxHigh

 （箱子长，箱子宽，箱子高）



 某用户下单买了如下商品

 n（商品件数）

 item1Price,item1Long,item1With,item1High

 item2Price,item2Long,item2With,item2High

 item3Price,item3Long,item3With,item3High

 item4Price,item4Long,item4With,item4High

 ...

 （商品价格，商品长，商品宽，商品高）

 (所有输入类型均为int型正整数)



 请你算出需要使用最小的箱子数量，可以将这些商品顺利得清关送到消费者手中，如果无解，输出-1

 *
 *
 *
 *
 */


import java.util.Scanner;
public class Main2 {
    private static final int CUSTOMS_LIMIT_MONEY_PER_BOX =2000 ;
/** 请完成下面这个process函数，实现题目要求的功能 **/
    /** 当然，你也可以不按照这个模板来作答，完全按照自己的想法来 ^-^  **/

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        Model boxTemplate = new Model();
        boxTemplate.price = CUSTOMS_LIMIT_MONEY_PER_BOX;

        while (scanner.hasNext()){
            boxTemplate.length = scanner.nextInt();
            boxTemplate.width = scanner.nextInt();
            boxTemplate.height = scanner.nextInt();

            int itemNum = scanner.nextInt();
            Model[] items = new Model[itemNum];
            for(int i=0; i<itemNum; i++){
                Model item = new Model();
                item.price = scanner.nextInt();
                item.length = scanner.nextInt();
                item.width = scanner.nextInt();
                item.height = scanner.nextInt();
                items[i] = item;
            }
            long startTime = System.currentTimeMillis();
            Integer boxMinNum = Integer.MAX_VALUE;
            System.out.println (process(boxTemplate,items));

        }
    }

    private static int process(Model boxTemplate, Model[] items) {
        //不可能的情况
        //1.大于十件
        if (items.length>=10) return -1;
        //2.单个物品价格或规格超过限制
        int sum=0,flag=0;
        for (int i = 0; i <items.length ; i++) {
            sum+=items[i].price;
            if (items[i].price>boxTemplate.price ||
                    min3(items[i].height,items[i].length,items[i].width)>
                    max3(boxTemplate.height,boxTemplate.length,boxTemplate.width)
                    )
                flag=-1;
        }
        if (flag==-1) return -1;
        //if (sum>boxTemplate.price) return -1;

        //最简单情况sum<2000
        //尽量放到一个箱子里
        if (sum<=boxTemplate.price) sum=0;

        //一般情况；
        // 1.先把n个商品放到n个箱子里；
        // 2.两两合并，如果合并后小于单个箱子的容量和价格限制，则n--；


        return sum;
    }

    private static int max3(int height, int length, int width) {
        return Integer.max(height,Integer.max(length,width));
    }

    private static int min3(int height, int length, int width) {
        return Integer.min(height,Integer.min(length,width));
    }


    private static class Model {
        public int price;
        public int length;
        public int width;
        public int height;
    }
}



