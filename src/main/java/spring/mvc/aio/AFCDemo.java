package spring.mvc.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 */
public class AFCDemo {
    static Thread current;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        //Path filePath = Paths.get(args[0]);
        Path filePath= Paths.get("C:\\Users\\Administrator\\Desktop\\test-write.txt");
        AsynchronousFileChannel afc = AsynchronousFileChannel.open(filePath);
        ByteBuffer byteBuffer = ByteBuffer.allocate(16 * 1024);
//使用FutureDemo时，请注释掉completionHandlerDemo，反之亦然
        //futureDemo(afc, byteBuffer);
        completionHandlerDemo(afc, byteBuffer);
    }

    private static void completionHandlerDemo(AsynchronousFileChannel afc, ByteBuffer byteBuffer) throws IOException {
        current = Thread.currentThread();
        afc.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("Bytes Read = " + result);
                attachment.flip();
                byte[] data=new byte[attachment.limit()];
                attachment.get(data);
                System.out.println("2.Reading data via a CompletionHandler");
                System.out.println(new String(data));
                attachment.clear();
                current.interrupt();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(exc.getCause());
                current.interrupt();
            }

        });
        System.out.println("Waiting for completion...");
        try {
            current.join();
        } catch (InterruptedException e) {
        }
        System.out.println("End");
        afc.close();
    }

    private static void futureDemo(AsynchronousFileChannel afc, ByteBuffer byteBuffer) throws InterruptedException, ExecutionException, IOException {
        Future<Integer> result = afc.read(byteBuffer, 0);
        while (!result.isDone());
        System.out.println("result："+result.get());
        System.out.println("position:"+byteBuffer.position());
        System.out.println("mark:"+byteBuffer.mark());
        System.out.println("limit:"+byteBuffer.limit());
        byteBuffer.flip();
        System.out.println("----------------after flip------------------");
        System.out.println("result："+result.get());
        System.out.println("position:"+byteBuffer.position());
        System.out.println("mark:"+byteBuffer.mark());
        System.out.println("limit:"+byteBuffer.limit());
        byte[] data=new byte[byteBuffer.limit()];
        byteBuffer.get(data);
        System.out.println(new String(data));
        byteBuffer.clear();
    }
}
