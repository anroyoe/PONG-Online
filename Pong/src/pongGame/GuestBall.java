package pongGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Random;

public class GuestBall implements Runnable {
	private int x, y, xDirection, yDirection;

	private boolean fin = false;
	Scoreboard score;

	GuestPaddle p1 = new GuestPaddle(10, 25, 1);
	HostPaddle p2 = new HostPaddle(485, 25, 2);

	Rectangle ball;

	private int myPort, enemyPort;

	public GuestBall(int x, int y) {
		score = new Scoreboard();
		this.x = x;
		this.y = y;

		// Set ball moving randomly
		Random r = new Random();
		int rXDir = r.nextInt(1);
		if (rXDir == 0)
			rXDir--;
		setXDirection(rXDir);

		int rYDir = r.nextInt(1);
		if (rYDir == 0)
			rYDir--;
		setYDirection(rYDir);

		// create "ball"
		ball = new Rectangle(this.x, this.y, 15, 15);

	}

	public void setXDirection(int xDir) {
		xDirection = xDir;
	}

	public void setYDirection(int yDir) {
		yDirection = yDir;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(ball.x, ball.y, ball.width, ball.height);
	}

	public void collision() {
		if (ball.intersects(p1.getPaddle()))
			setXDirection(+1);
		if (ball.intersects(p2.getPaddle()))
			setXDirection(-1);
	}

	public void move() {
		try {
			DatagramSocket server = new DatagramSocket(myPort);
			byte mensaje[] = new byte[5];
			DatagramPacket recibo = new DatagramPacket(mensaje, mensaje.length);
			server.receive(recibo);
			if (mensaje[0] == 0) {
				byte x[] = { 0, 0, mensaje[1], mensaje[2] };
				byte y[] = { 0, 0, mensaje[3], mensaje[4] };
				ball.x = ByteBuffer.wrap(x).getInt();
				ball.y = ByteBuffer.wrap(y).getInt();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFinished() {
		return fin;
	}

	public void end() {
		this.fin = true;
	}

	public void setPorts(int myPort, int enemyPort) {
		this.myPort = myPort;
		this.enemyPort = enemyPort;
		p1.setPorts(myPort, enemyPort);
		p2.setPorts(myPort, enemyPort);
	}

	@Override
	public void run() {
		try (DatagramSocket server = new DatagramSocket(myPort); DatagramSocket server2 = new DatagramSocket(62075);) {
			while (!this.fin) {
				byte mensaje[] = new byte[7];
				DatagramPacket recibo = new DatagramPacket(mensaje, mensaje.length);
				server.receive(recibo);
				byte x[] = { 0, 0, mensaje[1], mensaje[2] };
				byte y[] = { 0, 0, mensaje[3], mensaje[4] };
				if (mensaje[0] == 0) {
					ball.x = ByteBuffer.wrap(x).getInt();
					ball.y = ByteBuffer.wrap(y).getInt();
					score.setP1score(mensaje[5]);
					score.setP2score(mensaje[6]);
				}
				server2.receive(recibo);
				if (mensaje[0] == 1) {
					y[2] = mensaje[1];
					y[3] = mensaje[2];
					p1.setY(ByteBuffer.wrap(y).getInt());
				}
				Thread.sleep(8);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
