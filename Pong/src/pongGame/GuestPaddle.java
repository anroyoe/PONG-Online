package pongGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class GuestPaddle {

	private int x, y, yDirection, id;

	private Rectangle paddle;

	public GuestPaddle(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		paddle = new Rectangle(x, y, 10, 50);
	}

	public void setY(int y) {
		paddle.y = y;
	}

	public void setX(int x) {
		paddle.x = x;
	}

	public int getID() {
		return this.id;
	}

	public Rectangle getPaddle() {
		return this.paddle;
	}

	public void setYDirection(int yDir) {
		yDirection = yDir;
	}

	public void draw(Graphics g) {
		switch (id) {
		default:
			System.out.println("Please enter a Valid ID in paddle contructor");
			break;
		case 1:
			g.setColor(Color.CYAN);
			g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
			break;
		case 2:
			g.setColor(Color.pink);
			g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
			break;
		}
	}

}
