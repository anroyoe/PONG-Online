package pongGame;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class GuestPaddle extends Paddle {

	public GuestPaddle(int x, int y, int id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}
	public void move() {
		byte mensaje[]=new byte[2];
		try(DatagramSocket client=new DatagramSocket();) {
			DatagramPacket recibo= new DatagramPacket(mensaje,mensaje.length);
			while(true) {
				client.receive(recibo);
				this.setX(recibo.getData()[0]);
				this.setY(recibo.getData()[1]);
			}
			
		}catch(SocketException e) {e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
		
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
