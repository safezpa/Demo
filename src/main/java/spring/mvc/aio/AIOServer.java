package spring.mvc.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AIOServer {

    public static int clientCount;
    AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open();
    public CountDownLatch latch=new CountDownLatch(2);;
    public AIOServer() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        AIOServer aio=new AIOServer();
        aio.main();
    }
    public  void main() throws IOException {

      channel.bind(new InetSocketAddress(8080));
      System.out.println("服务器已启动，端口号：" + "8080");
      channel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AIOServer>() {
          @Override
          public void completed(AsynchronousSocketChannel result, AIOServer attachment) {

              //继续接受其他客户端的请求
              AIOServer.clientCount++;
              System.out.println("连接的客户端数：" + AIOServer.clientCount);
              attachment.channel.accept(attachment, this);
              //创建新的Buffer
              ByteBuffer buffer = ByteBuffer.allocate(1024);
              //异步读  第三个参数为接收消息回调的业务Handler

                  latch.countDown();

              //channel.read(buffer, buffer, new ServerReadHandler(channel));
          }

          @Override
          public void failed(Throwable exc, AIOServer attachment) {

          }
      });
    }
}
