package spring.mvc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;


//一个多路复用器Selector可以同时轮询多个Channel，
// 由于JDK使用了epoll()代替传统的select实现，
// 所以它并没有最大连接句柄1024/2048的限制。
// 这也就意味着只需要一个线程负责Selector的轮询，
// 就可以接入成千上万的客户端，这确实是个非常巨大的进步。
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel，监听8080端口
        ServerSocketChannel ssc=ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);

        Selector selector=Selector.open();
        ssc.register(selector,SelectionKey.OP_ACCEPT);
        //创建处理器
        Handler handler=new Handler(1024);

        //轮询事件
        while (true){
            //等待请求，每次等待阻塞3s，超过3s继续运行，如果传入0或者不传入将一直阻塞
            if (selector.select(3000)==0){
                System.out.println("请求超时...");
                continue;
            }
            System.out.println("处理请求。。。");
           Iterator<SelectionKey> keyIter=selector.selectedKeys().iterator();
            while (keyIter.hasNext()){
                SelectionKey key=keyIter.next();
                try {
                    //接收到连接请求
                    if (key.isAcceptable()) {
                        System.out.println("接收到连接请求");
                        handler.handleAccept(key);
                    }
                    //读数据
                    if (key.isReadable()) {
                        System.out.println("读数据");
                        handler.handleRead(key);
                    }
                }catch (IOException ex){
                    keyIter.remove();
                    continue;
                }
                //处理完成后，从SelectionKey迭代器中移除当前使用的key
                keyIter.remove();
            }
        }
    }

    private static class Handler {
        private int bufferSize=1024;
        private String localCharset="UTF-8";


        public Handler(int bufferSize,String localCharset){
            if (bufferSize>0) this.bufferSize=bufferSize;
            if (localCharset!=null) this.localCharset=localCharset;

        }


        public Handler(String localCharset){
            this(-1,localCharset);
        }
        public Handler(int bufferSize) {
            this(bufferSize,null);
        }

        public void handleAccept(SelectionKey acceptKey) throws IOException {

            SocketChannel sc=((ServerSocketChannel)acceptKey.channel()).accept();
            sc.configureBlocking(false);
            sc.register(acceptKey.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead(SelectionKey readKey) throws IOException {
            //获取channel
           SocketChannel sc= (SocketChannel) readKey.channel();
           //获取buffer并重置
            ByteBuffer buffer= (ByteBuffer) readKey.attachment();
            buffer.clear();
            //没有读到内容则关闭
            if (sc.read(buffer)==-1){
                sc.close();
            }else {
                //将buffer转换为读状态
                buffer.flip();
                //将buffer中的内容编码
                String receivedString= Charset.forName(localCharset).newDecoder().decode(buffer)
                        .toString();
                System.out.println("received from client :"+receivedString);
                //返回数据给客户端
                String sendString ="received data :"+receivedString;
                buffer=ByteBuffer.wrap(sendString.getBytes(localCharset));
                sc.write(buffer);

                sc.close();
            }
        }

    }
}
