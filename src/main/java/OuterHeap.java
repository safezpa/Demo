import java.nio.ByteBuffer;

public class OuterHeap {
    public static void main(String[] args) {
        //mark-->position-->limit-->capacity
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        buffer.putChar('a');
        buffer.putChar('c');
        System.out.println("插入完数据 " + buffer);
        byte[] data=new byte[buffer.limit()-2];

        buffer.putChar('s');
        System.out.println("putted " + buffer);
        buffer.get(data);
        System.out.println("data"+new String(data));
        System.out.println("getted " + buffer);

        buffer.mark();// 记录mark的位置
        System.out.println("marked " + buffer);
        buffer.position(3);// 设置的position一定要比mark大，比lim小，否则mark无法重置
        System.out.println("reset前 " + buffer);
        buffer.reset();// 重置reset ，reset后的position=mark
        System.out.println("reset后 " + buffer);
        buffer.rewind();//清除标记，position变成0，mark变成-1
        System.out.println("清除标记后 " + buffer);

        data=new byte[buffer.limit()];
        buffer.get(data);
        System.out.println("data"+new String(data));
        System.out.println("getted " + buffer);
    }
}
