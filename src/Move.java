public class Move {
	
	private String move;
	private int x,y;
	
	public Move(String move, int x, int y) {
		this.move = move;
		this.x = x;
		this.y = y;
	}
	
	public String getMove() {
		return move;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int convertXMove(String move) {	
		int pos = Character.toUpperCase(move.charAt(0)) - 65;
		return pos;
	}
	
 }