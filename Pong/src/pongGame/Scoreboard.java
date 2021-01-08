package pongGame;

public class Scoreboard {
	private int p1score;
	private int p2score;
	public Scoreboard() {
		this.p1score=0;
		this.p2score=0;
	}
	public int getP1score() {
		return p1score;
	}
	public void setP1score(int p1score) {
		this.p1score = p1score;
	}
	public int getP2score() {
		return p2score;
	}
	public void setP2score(int p2score) {
		this.p2score = p2score;
	}
	public void pointP1() {
		this.p1score++;
	}
	public void pointP2() {
		this.p2score++;
	}
	
}
