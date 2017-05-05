package datastructure;

/**
 * Created by safe on 2017/3/12.
 */



//消息中间件中有两个概念：消息和队列。

//队列可以理解为消息的载体，生产者（Producer）将消息发送到队列中，消费者（Consumer）从队列中消费消息，
// 同一个队列的消息只会被一个消费者按序消费，不同队列的消息不保证消费顺序。

//消息又分为顺序消息和乱序消息，在很多业务中，产生的消息需要保证一定程度上的按序消费，
// 将这类消息按一定的规则发送到同一个队列里即可保证消息的有序性。



// 随着业务的发展，消息系统需要对队列数进行扩容，为了实现不停机的扩容方式，
// 且保证消息在扩容过程中不会乱序，约定扩容时需要按当前队列的倍数进行扩容。
// 因为大多数情况下，消息消费的速度慢于生产的速度，为了保证扩容过程的消息有序性约定：
// 在t时刻新扩容的队列可以立马接收消息，
// 但必须等旧队列上在t时刻前接收到的消息被全部消费完毕后才可以被消费者进行消费。

 //消费者在消费的过程中，通过消费位点来记录或者控制消费速度，
// 比如当前某队列上堆积了5条消息，最开始消费位点为0，当有三条消息被消费后，消费位点则变成了3。



import java.util.Scanner;

public class Main {

    /*请完成下面这个函数，实现题目要求的功能*/
    /*当然，你也可以不按照下面这个模板来作答，完全按照自己的想法来 ^-^ */
    /******************************开始写代码******************************/
    private static int [] compute(int queueNums, int msgNums, int expandTime, int sendSpeed, int consumeSpeed, int targetTime) {
        int [] result = new int[0];
        // 实现扩容
        // 按倍数扩容
        //1.扩容过程中生产的消息
        int expandMsgNumSp=expandTime*sendSpeed;
        //2.扩容过程中消费的消息、
        int expandMsgNumCo=expandTime*consumeSpeed;
        //targetTime时刻前接收到的消息 msgNums
        // 3.消费完成需要的时间
        int spendTime=msgNums/consumeSpeed;
        // 4.需要扩容的倍数
        int num=msgNums*(queueNums+expandMsgNumSp)/queueNums;


        return result;
    }
    /******************************结束写代码******************************/

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int queueNums = in.nextInt(), msgNums = in.nextInt(), expandTime = in.nextInt();
        in.nextLine();
        int sendSpeed = in.nextInt(), consumeSpeed = in.nextInt();
        in.nextLine();
        int targetTime = in.nextInt();
        in.close();

        int [] result = compute(queueNums, msgNums, expandTime, sendSpeed, consumeSpeed, targetTime);
        for (int i = 0; i < result.length; i++) {
            System.out.println(String.format("%d %d", i,  result[i]));
        }
    }
}
