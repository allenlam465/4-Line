import java.util.LinkedList;
import java.util.List;

public class Board {
	
	private char[][] board;
	private List<String> moveHistory;
	
	public Board() {
		this.board = new char[8][8];
		this.moveHistory = new LinkedList<>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++){
				board[i][j] = '-';
			}
		}
	}
	
	public void setBoard(char[][] board) {
		this.board = board;
	}
	
	public char[][] getBoard() {
		return board;
	}
	
	public void placeBoard(int x, int y, char symbol) {
		this.board[x][y] = symbol;
	}
	
	public boolean detectWin() {
		return false;
	}
	
	public String printBoard() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  1 2 3 4 5 6 7 8 	Player vs. Oppenent");
		
		for(int i = 0; i < 8; i ++) {
			sb.append("\n" + Character.toString(((char)(65 + i))) + " ");
			for(int j = 0; j < 8; j++) {
				sb.append(board[i][j] + " ");
			}
			
			if(!moveHistory.isEmpty())
				sb.append("	   " + "Moves Here");	
		}
		
		return sb.toString();
	}
	
}
