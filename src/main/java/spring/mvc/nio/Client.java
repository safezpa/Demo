package spring.mvc.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        //数据
        String msg="Hello Server！";
        try {
            Socket socket=new Socket("127.0.0.1",8080);
            //读 字节流-->字符流-->缓冲区
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //写 字符流-->字节流
            PrintWriter pw=new PrintWriter(socket.getOutputStream());

            //发送数据
            for (int i = 0; i <10 ; i++) {
                pw.println(msg);
            }

            pw.flush();

            //读数据
            System.out.println(br.readLine());
            br.close();
            pw.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
