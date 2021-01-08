package pongGame;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;

public class Player {

	public static void main(String[] args) {
		try(Socket s=new Socket("localhost",3048);DataInputStream in=new DataInputStream(s.getInputStream())){
			int num=in.read();
			int myPort=in.readInt();
			int enemyPort=in.readInt();
			if(num==1) {
				Player1 p1=new Player1(myPort,enemyPort);
			}else {
				Player2 p2=new Player2(myPort,enemyPort);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
