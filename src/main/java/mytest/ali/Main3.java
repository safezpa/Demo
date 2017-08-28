package mytest.ali;

/**
 *
 在快递公司干线运输的车辆使用中，存在着单边车和双边车的两种使用场景，例如 北京中心-杭州中心，两个分拨中心到彼此的单量对等，则可以开双边车（即同一辆车可以往返对开），而当两个中心的对发单量不对等时，则会采用单边车，并且双边车的成本是低于单边车的，即将两辆对开的单边车合并为一辆往返的双边车是能够节省运力成本的

 单边车优化原则：

 将单边车优化的规则进行可抽象为以下三种（A,B,C均表示分拨中心）：

 规则-1: A-B单边车，B-A单边车 优化方案：将A-B和B-A的两辆单边车合并为双边；

 规则-2: A-B单边车，B-C单边车，C-A单边车 优化方案：将A-B、B-C、C-A的三辆单边车优化为一辆环形往返车；

 规则-3: A-B单边车，C-A单边车，B、C同省 优化方案：当B、C同省，将A-B、C-A两辆单边优化为一辆环形往返

 问题如下：

 以某快递公司的实际单边车数据为例（线路ID编码;出分拨中心; 出分拨中心所在省;到达分拨中心;到达分拨中心所在省；车型；），进行优化，优化的规则参照以上，并且优先级依次降低，合并的时候需要考虑车型（分为17.5m和9.6m两种）：1、相同车型才能进行合并；2、两辆同方向的9.6m可以与一辆17.5m的对开车型合并优化 说明：优化输出结果按照规则分类，例如rule1： 2016120001+2016120002表示将单边车线路ID编码为2016120001和2016120002按照规则1合并优化



 *
 *
 *
 *
 */


import java.util.Scanner;

public class Main3 {
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





