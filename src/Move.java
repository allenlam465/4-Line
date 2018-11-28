public class Move {

	private String move;
	private int x,y;

	public Move(String move, int x, int y) {
		this.move = move;
		this.x = x;
		this.y = y;
	}
	
	public Move(int x, int y) {
		this.x = x;
		this.y = y - 1;
		move = convertXMove(x,y);

	}
	
	public Move(String move) {
		this.move = move;
		this.x = convertXMove(move);
		this.y = Character.getNumericValue(move.charAt(1)) - 1;
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

	public String convertXMove(int x, int y) {
		char pos = Character.toUpperCase((char) (x + 65));
		String move = Character.toString(pos) + Integer.toString(y + 1);
		return move;
	}

}