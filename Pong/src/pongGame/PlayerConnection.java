package pongGame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class PlayerConnection implements Runnable {
	private CountDownLatch count;
	private Socket s;
	private int player;
	private Port port;

	public PlayerConnection(Socket s, int player, CountDownLatch count, Port port) {
		super();
		this.count = count;
		this.s = s;
		this.player = player;
		this.port = port;

	}

	@Override
	public void run() {
		port.setPlayer(player);
		port.setPort(s.getPort());
		count.countDown();

	}

}
