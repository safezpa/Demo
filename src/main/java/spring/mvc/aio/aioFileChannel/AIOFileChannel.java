package spring.mvc.aio.aioFileChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AIOFileChannel {
    public static void readingData() throws IOException {
        //Reading Data

        Path path= Paths.get("C:\\Users\\Administrator\\Desktop\\test-write.txt");
        AsynchronousFileChannel fileChannel=AsynchronousFileChannel.open(path,
                StandardOpenOption
                .READ);
        ByteBuffer buffer=ByteBuffer.allocate(10);
        long position=0;

        //1.Reading Data Via a Future
        Future<Integer> operation=fileChannel.read(buffer,position);
        //blocking
        while (!operation.isDone());

        buffer.flip();
        byte[] data=new byte[buffer.limit()];
        buffer.get(data);
        System.out.println("1.Reading Data Via a Future");
        System.out.println(new String(data));
        buffer.clear();
        //2.Reading data via a CompletionHandler
        fileChannel.read(buffer, position, buffer,new CompletionHandler<Integer,ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result = " + result);

                attachment.flip();
                byte[] data=new byte[attachment.limit()];
                attachment.get(data);
                System.out.println("2.Reading data via a CompletionHandler");
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("failed!");
            }
        });
        //Once the read operation finishes
        // the CompletionHandler's completed() method will be called.
        // As parameters to the completed() method are passed an Integer
        // telling how many bytes were read, and the "attachment"
        // which was passed to the read() method.
        // The "attachment" is the third parameter to the read() method.
        // In this case it was the ByteBuffer into which the data is also read.
        // You can choose freely what object to attach.


    }

    //Writing Data
    public static void writingData() throws IOException {
        //1.Writing Data Via a Future
        Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\test-write.txt");
        if(!Files.exists(path)){
            Files.createFile(path);
        }
        AsynchronousFileChannel fileChannel =
                AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        buffer.put("test data".getBytes());
        buffer.flip();

/*        Future<Integer> operation = fileChannel.write(buffer, position);
        buffer.clear();

        while(!operation.isDone());
        System.out.println("Write done");*/

        //2.Writing Data Via a CompletionHandler

        fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written: " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Write failed");
                exc.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        readingData();
        //writingData();
    }
}
