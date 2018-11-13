public class MoveSet {
	
	private String playerMove, oppoMove;
	private int playerX, playerY, oppoX, oppoY;
	
	public MoveSet(String move) {
		this.playerMove = move;
		
		this.playerX = convertMove(playerMove);
		this.playerY = Integer.parseInt(playerMove);
	}
	
	public void setOppoMove(String move) {
		this.oppoMove = move;
		
		this.playerX = convertMove(oppoMove);
		this.playerY = Integer.parseInt(oppoMove);
	}
	
	public int getplayerX() {
		return playerX;
	}
	
	public int getplayerY() {
		return playerY;
	}
	
	public int getoppoX() {
		return oppoX;
	}
	
	public int getoppoY() {
		return oppoY;
	}
	
	public int convertMove(String move) {	
		int pos = Character.toUpperCase(move.charAt(0)) - 65;
		return pos;
	}
	
	public String printMoveSet(){
		StringBuilder sb = new StringBuilder();
		
		if(!playerMove.equals(null))
			sb.append(playerMove + " ");
		
		if(!oppoMove.equals(null))
			sb.append(oppoMove);
		
		return sb.toString();
	}
 }