package pongGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.rmi.UnknownHostException;

public class HostPaddle implements Runnable {

	private int x, y, yDirection, id;

	private Rectangle paddle;
	
	private int myPort, enemyPort;

	public HostPaddle(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		paddle = new Rectangle(x, y, 10, 50);
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getID() {
		return this.id;
	}

	public Rectangle getPaddle() {
		return this.paddle;
	}


	public void setPorts(int myPort, int enemyPort) {
		this.myPort = myPort;
		this.enemyPort = enemyPort;
	}

	public void keyPressed(KeyEvent e) {
		switch (id) {
		default:
			System.out.println("Please enter a Valid ID in paddle contructor");
			break;
		case 1:
			if (e.getKeyCode() == KeyEvent.VK_W) {
				setYDirection(-1);
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(1);
			}
			break;

		case 2:
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				setYDirection(-1);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(1);
			}
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (id) {
		default:
			System.out.println("Please enter a Valid ID in paddle contructor");
			break;
		case 2:
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				setYDirection(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(0);
			}
			break;
		case 1:

			if (e.getKeyCode() == KeyEvent.VK_W) {
				setYDirection(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(0);
			}
			break;
		}
	}

	public void setYDirection(int yDir) {
		yDirection = yDir;
	}

	public void move() {
		paddle.y = paddle.y + yDirection;

		if (paddle.y <= 15) {
			paddle.y = 15;
		}
		if (paddle.y >= 340) {
			paddle.y = 340;
		}
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

	@Override
	public void run() {
		try (DatagramSocket correos = new DatagramSocket();){
			while (true) {
				move();
				byte y[] = ByteBuffer.allocate(4).putInt(paddle.y).array();
				//Los mensajes de las palas serán (id,posiciónY[2],0,0,0,0).
				byte mensaje[]= {(byte)id,y[2],y[3],0,0,0,0};
				DatagramPacket envio=null;
				if(id==1) {
					envio = new DatagramPacket(mensaje, mensaje.length, InetAddress.getByName("localhost"),
							 62075);
				}else {
					envio = new DatagramPacket(mensaje, mensaje.length, InetAddress.getByName("localhost"),
							 62074);
				}
				correos.send(envio);
				Thread.sleep(8);

			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
