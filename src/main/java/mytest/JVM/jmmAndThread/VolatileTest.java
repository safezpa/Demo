package mytest.JVM.jmmAndThread;

/**
 * Created by safe on 4/6/17.
 *
 *
 */
public class VolatileTest {
    public static volatile int race=0;

    public static void increase() {
        race++;
    }

    public static final int THREADS_COUNT=20;

    //why cannot stop the thread
    public static void main(String[] args) {
        Thread[] threads=new Thread[THREADS_COUNT];

        for (int i = 0; i <THREADS_COUNT ; i++) {

            threads[i]=new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int j = 0; j <10000 ; j++) {
                        increase();
                    }
                }

            });

            threads[i].start();
        }

        //wait for all increase thread finished
       // while (Thread.activeCount()>1) Thread.yield();
        System.out.println(Thread.activeCount());

        System.out.println(race);
    }


    //right use for volatile
    volatile boolean shutdownRequested;
    public  void shoutdown(){
        shutdownRequested=true;
    }

    public void doWork(){
        while (!shutdownRequested){
            //do stuff
        }
    }

    //stop instructure reoder




}
