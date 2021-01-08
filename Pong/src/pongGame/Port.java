package pongGame;

public class Port {
	private Integer puerto;
	private Integer player;
	public Port(Integer puerto) {
		this.puerto=puerto;
		this.player=0;
	}
	public Integer getPlayer() {
		return player;
	}
	public void setPlayer(Integer player) {
		this.player = player;
	}
	public void setPort(Integer puerto) {
		this.puerto=puerto;
	}
	public int getPort() {
		return this.puerto;
	}
}
