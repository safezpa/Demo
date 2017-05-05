package mytest.payPal2;

/**
 * Created by safe on 2017-04-13.
 */
public class Main{

    public static void main(String[] args) throws InterruptedException {

        Runnable s = new Runnable() {

            public void run() {

                try {

                    System.out.print("线程开始执行");
                            Thread.sleep(1000);

                    System.out.print("线程执行完毕");

                } catch (InterruptedException e) {

                    System.out.print("睡眠中发生中断 ");

                }finally {

                    System.out.print("finally块被调用了 ");

                }

            }

        };

        Thread thread = new Thread(s);

        thread.setDaemon(true);

        thread.start();

        Thread.sleep(500);

    }

}





