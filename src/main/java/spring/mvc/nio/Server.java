package spring.mvc.nio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String args[]) {
       //new ServerSocket listen 8080
        try {
            //1.Create a server with the specified port,
            // bind endpoint
            ServerSocket  server=new ServerSocket(8080);
            // Listens for a connection to be made to this socket and acceptsit.
            // The method blocks until a connection is made.
            Socket socket=server.accept();


            //2.输入流
           InputStream in=socket.getInputStream();
           //输入流字节流-->字符流
            InputStreamReader ins=new InputStreamReader(in);
           //包装流
            //BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //creates a buffering character-input stream
            // that uses an input buffer of the specified size.
            BufferedReader br=new BufferedReader(ins);
            System.out.println("收到客户端的数据："+br.readLine());

            //3.输出
            //pw 发送数据
            PrintWriter pw=new PrintWriter(socket.getOutputStream());
            pw.println("来自服务端的数据："+"echo");
            pw.flush();
            //4.关闭资源
            br.close();
            pw.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

