package pongGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

public class HostBall implements Runnable {
	// global variables
	private int x, y, xDirection, yDirection;

	Scoreboard score;

	HostPaddle p1 = new HostPaddle(10, 25, 1);
	GuestPaddle p2 = new GuestPaddle(485, 25, 2);

	Rectangle ball;

	private int myPort, enemyPort;

	public HostBall(int x, int y) {
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

	public void reset() {
		ball.x = x;
		ball.y = y;
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

		collision();
		ball.x += xDirection;
		ball.y += yDirection;
		// bounce the ball when it hits the edge of the screen
		if (ball.x <= 0) {
			setXDirection(+1);
			score.pointP2();
			this.reset();
		}
		if (ball.x >= 485) {
			setXDirection(-1);
			score.pointP1();
			this.reset();
		}

		if (ball.y <= 15) {
			setYDirection(+1);
		}

		if (ball.y >= 385) {
			setYDirection(-1);
		}

	}

	public void setPorts(int myPort, int enemyPort) {
		this.myPort = myPort;
		this.enemyPort = enemyPort;
		p1.setPorts(myPort, enemyPort);
	}

	@Override
	public void run() {
		try (DatagramSocket correos = new DatagramSocket(myPort); DatagramSocket server2 = new DatagramSocket(62074);) {
			while (true) {
				move();
				//Hay que convertir los enteros de la posición en bytes.
				byte x[] = ByteBuffer.allocate(4).putInt(ball.x).array();
				byte y[] = ByteBuffer.allocate(4).putInt(ball.y).array();
				// Los mensajes de la bola serán (0, posiciónX[2], posiciónY[2], puntuaciónJ1,
				// puntuaciónJ2).
				byte mensaje[] = { 0, x[2], x[3], y[2], y[3], (byte) score.getP1score(), (byte) score.getP2score() };
				DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, InetAddress.getByName("localhost"),
						enemyPort);
				correos.send(envio);
				Thread.sleep(8);
				DatagramPacket recibo = new DatagramPacket(mensaje, mensaje.length);
				server2.receive(recibo);
				if (mensaje[0] == 2) {
					y[2] = mensaje[1];
					y[3] = mensaje[2];
					p2.setY(ByteBuffer.wrap(y).getInt());
				}

			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}
