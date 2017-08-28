package mytest.souhu;

//箱子类
class Box {
    static int MAX_SIZE = 6;
    int id = 0;
    int size;
    int[] store;//记录箱子里装入的物品

    Box(int n) {
        store = new int[n];
    }
}


public class BinPacking {

    private int[] goods;

    private BinPacking(int[] goods) {
        this.goods = goods;
    }


    //    将Good按照size由大到小排序
    private void sort(int[] goods) {
        for (int i = 0; i < goods.length; i++) {
            for (int j = 1; j < goods.length - i; j++) {
                if (goods[j - 1] < goods[j]) {
                    int temp = goods[j];
                    goods[j] = goods[j - 1];
                    goods[j - 1] = temp;
                }
            }
        }
    }


    //    用于判断是否全部物品已经装完
    private int sum(int[] goods) {
        int sum = 0;
        for (int good : goods) {
            sum += good;
        }
        return sum;
    }


    //    装箱过程
    private int binPacking() {

        sort(goods);

        Box box = new Box(goods.length);

        while (sum(goods) != 0) {
            box.id++;//箱子id从1开始
            box.size = Box.MAX_SIZE;
            for (int j = 0; j < goods.length; j++) {
                box.store[j] = 0;
            }//存储清零,相当于开一个新箱子

            for (int i = 0; i < goods.length; i++) {
                while (goods[i] == 0) {
                    if (i == goods.length - 1)
                        break;
                    i++;
                }//跳过已经装入的物品

                if (goods[i] <= box.size) {
                    box.size -= goods[i];
                    box.store[i] = goods[i];//将已装入的物品存在Box类的store[]中
                    goods[i] = 0;
                    if (box.size == 0)
                        break;
                }
            }

//            一次遍历结束,打印结果
            System.out.println("第" + box.id + "个箱子装入质量为：");
            for (int j = 0; j < goods.length; j++) {
                if (box.store[j] != 0) {
                    System.out.print(box.store[j] + " ");
                }
            }
            //System.out.println();

        }
        //System.out.println("最少需要" + box.id + "个箱子");
        return box.id;
    }


    public static void main(String[] args) {
        int[] goods = {5,  7,  3,  9,  6,  8,  1,  4,  2,  5};
 /*       Scanner in = new Scanner(System.in);
        List<String> list=new ArrayList<>();
        while (in.hasNext()){
            list.add(in.nextLine());
        }
        for (int i = 0; i <list.size()-1 ; i++) {
         String[] strings=list.get(i).split(" ");
         int[] ints=new int[1024];
         int m=0;
            for (int j = 0; j <6 ; j++) {
                int k=Integer.parseInt(strings[j]);
                for (int l = 0; l <k ; l++) {
                    ints[m]=j+1;
                    m++;
                }
            }
            BinPacking b = new BinPacking(ints);
            b.binPacking();

        }*/
        BinPacking b = new BinPacking(goods);
        b.binPacking();

    }

}
