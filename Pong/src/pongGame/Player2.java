package pongGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JFrame;


public class Player2 extends JFrame{
	private int miPuerto;
	private int suPuerto;
	int gWidth = 500;
	int gHeight = 400;
	Dimension screenSize = new Dimension(gWidth, gHeight);

	Image dbImage;
	Graphics dbGraphics;

	// ball object
	static GuestBall b = new GuestBall(250, 200);
	
	public Player2(int miPuerto, int suPuerto) {
		this.miPuerto=miPuerto;
		this.suPuerto=suPuerto;
		b.setPorts(miPuerto, suPuerto);
		this.setTitle("PongGuest!");
		this.setSize(screenSize);
		this.setResizable(false);
		this.setVisible(true);
		this.setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new AL());
		Thread ball = new Thread(b);
		ball.start();
		Thread p1 = new Thread(b.p1);
		Thread p2 = new Thread(b.p2);
		p2.start();
		p1.start();
	}
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbGraphics = dbImage.getGraphics();
		draw(dbGraphics);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void draw(Graphics g) {
		b.draw(g);
		b.p1.draw(g);
		b.p2.draw(g);

		g.setColor(Color.WHITE);
		g.drawString("" + b.score.getP1score(), 25, 40);
		g.drawString("" + b.score.getP2score(), 465, 40);

		repaint();
	}

	public class AL extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			//b.p1.keyPressed(e);
			b.p2.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//b.p1.keyReleased(e);
			b.p2.keyReleased(e);
		}

	}
}
