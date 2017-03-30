package mytest.JVM;

/**
 * Created by safe on 2017/3/14.
 */
public class OOMByThread {
    public void stackLeakByThread(){
        while (true){
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    private void dontStop() {
        while (true){

        }
    }

    public static void main(String[] args) {
        OOMByThread oom=new OOMByThread();
        oom.stackLeakByThread();
    }


}
