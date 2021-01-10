package pongGame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	// Para el inicio de la aplicación se deberá dejecutar un Server y dos Player.
	// El servidor se encargará por medio de TCP de conectar a los 2 jugadores
	// enviándoles su número de jugador y el puerto que deberán utilizar para pasar
	// los mensajes UDP de la bola.

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		Port portj1 = new Port(0);
		Port portj2 = new Port(0);
		Socket s1 = null;
		Socket s2 = null;
		DataOutputStream out1 = null;
		DataOutputStream out2 = null;
		try (ServerSocket server = new ServerSocket(3048)) {

			CountDownLatch count = new CountDownLatch(2);
			s1 = server.accept();
			PlayerConnection p1 = new PlayerConnection(s1, count, portj1);
			pool.execute(p1);
			s2 = server.accept();
			PlayerConnection p2 = new PlayerConnection(s2, count, portj2);
			pool.execute(p2);
			count.await();
			out1 = new DataOutputStream(s1.getOutputStream());
			out1.write(1);
			out1.writeInt(portj1.getPort());
			out1.writeInt(portj2.getPort());
			out2 = new DataOutputStream(s2.getOutputStream());
			out2.write(2);
			out2.writeInt(portj2.getPort());
			out2.writeInt(portj1.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (s1 != null) {
				try {
					s1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (s2 != null) {
				try {
					s2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out1 != null) {
				try {
					out1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out2 != null) {
				try {
					out2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
