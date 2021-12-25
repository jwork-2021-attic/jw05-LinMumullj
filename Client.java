import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
public class Client {
    public static void main(String[] args)throws Exception{
        Socket socket = null;
        OutputStream os = null;
        //要知道服务器地址
        try {
            InetAddress serverIp = InetAddress.getByName("localhost");
            int port = 9999;
            //2.创建连接
            socket = new Socket(serverIp,port);
            //3.发生消息 IO流
            os = socket.getOutputStream();
            os.write("你好，闲言".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (os != null) {
                os.close();
            }
            if (socket != null) {
                socket.close();
            }

        }
    }

}