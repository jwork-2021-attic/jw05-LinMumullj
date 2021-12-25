import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = null;
        Socket accept = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //1. 我得有一个地址
            serverSocket = new ServerSocket(9999);
            //2.等待客户端连接过来  
            accept = serverSocket.accept();
            //3.读取客户端消息
            is = accept.getInputStream();

        /*byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) != -1 ){
            String s = new String(buf, 0, len);
            System.out.println(s);
        }*/

            //管道流
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;

            while ((len = is.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            System.out.println(baos.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (is != null) {
                is.close();
            }
            if (accept != null) {
                accept.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}