
public class MoveSet {
	
	private String player, opponent;
	private int playerX, playerY, opponentX, opponentY;
	
	public MoveSet(int x, int y) {
		this.playerX = x;
		this.playerY = y;
		
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getPlayerMove() {
		return player;
	}
	
	private String convertStringMove() {
		
		Character.toString(((char)(65 + y)));
		
	}
}
