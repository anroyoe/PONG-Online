package pongGame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		Port portj1=new Port(0);
		Port portj2=new Port(0);
		try(ServerSocket server=new ServerSocket(3048)){
			
			CountDownLatch count=new CountDownLatch(2);
			Socket s1=server.accept();
			PlayerConnection p1=new PlayerConnection(s1,1,count,portj1);
			pool.execute(p1);
			
			Socket s2=server.accept();
			PlayerConnection p2=new PlayerConnection(s2,2,count,portj2);
			pool.execute(p2);
			count.await();
			DataOutputStream out1=new DataOutputStream(s1.getOutputStream());
			out1.write(portj1.getPlayer());
			out1.writeInt(portj1.getPort());
			out1.writeInt(portj2.getPort());
			DataOutputStream out2=new DataOutputStream(s2.getOutputStream());
			out2.write(portj2.getPlayer());
			out2.writeInt(portj2.getPort());
			out2.writeInt(portj1.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}

	}

}
