package pongGame;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class PlayerConnection implements Runnable {
	private CountDownLatch count;
	private Socket s;

	private Port port;

	public PlayerConnection(Socket s, CountDownLatch count, Port port) {
		super();
		this.count = count;
		this.s = s;
		this.port = port;

	}

	@Override
	public void run() {
		port.setPort(s.getPort());
		count.countDown();

	}

}
