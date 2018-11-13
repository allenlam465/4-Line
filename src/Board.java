import java.util.*;

public class Board {

	public enum GameState {

	}

	private char[][] board;
	private LinkedList<MoveSet> moveHistory;
	private HashSet<Integer> availableMoves;    //keeps track of valid moves

	public Board() {
		this.board = new char[8][8];
		moveHistory = new LinkedList<>();
		availableMoves = new HashSet<>();
		initializeBoard();
	}

	private void initializeBoard() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++){
				board[i][j] = '-';
			}
		}
	}

	//	private void initializeBoard() {
	//		for(int i = 0; i < 8; i++) {
	//			for(int j = 0; j < 8; j++) {
	//				if(i == 2) {
	//					board[i][j] = 'X';
	//				}
	//				else if(i == 3) {
	//					board[i][j] = 'O';
	//				}
	//				else if(j == 6) {
	//					board[i][j] = 'X';
	//				}
	//				else if(j == 2) {
	//					board[i][j] = 'X';
	//				}
	//				else {
	//					board[i][j] = '-';
	//				}
	//			}
	//		}
	//
	//		availableMoves.clear();
	//
	//		for (int i = 0; i < 64; i++) {
	//			availableMoves.add(i);
	//		}
	//	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public char[][] getBoard() {
		return board;
	}

	public boolean validateMove(String move) {
		if(move.equals(null) || move.length() > 2)
			return false;

		int x = convertXMove(move);
		int y = Character.getNumericValue(move.charAt(1)) - 1;

		if(x >= 0 && 
				x < 8 && 
				y >= 0 && 
				y < 8 && 
				board[x][y] == '-') {
			MoveSet placed = new MoveSet(move,x,y);
			moveHistory.add(placed);
			return true;
		}

		return false;
	}

	public void placePiece(char piece) {		
		MoveSet placed = moveHistory.getLast();
		int x = placed.getX(), y = placed.getY();

		board[x][y] = piece;

	}

	public boolean checkDraw() {

		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; i++) {
				if(board[i][j] == '-')
					return false;
			}
		}

		return true;
	}


	public String printBoard() {
		StringBuilder sb = new StringBuilder();

		sb.append("  1 2 3 4 5 6 7 8 	Player vs. Oppenent");

		for(int i = 0; i < 8; i ++) {
			sb.append("\n" + Character.toString(((char)(65 + i))) + " ");
			for(int j = 0; j < 8; j++) {
				sb.append(board[i][j] + " ");
			}

			sb.append("	   " + "Moves Here");	
		}

		return sb.toString();
	}

	private int convertXMove(String move) {	
		int pos = Character.toUpperCase(move.charAt(0)) - 65;
		return pos;
	}
	// 0 -> blank
	// 1 -> X
	// 2 -> O
	public int[][] getIntBoard() {
		int[][] intBoard = new int[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(this.board[i][j] == '-') {
					intBoard[i][j] = 0;
				}
				else if(this.board[i][j] == 'X') {
					intBoard[i][j] = 1;
				}
				else {
					intBoard[i][j] = 2;
				}
			}
		}

		return intBoard;
	}

	public void printIntBoard() {
		int[][] intBoard = new int[8][8];
		intBoard = getIntBoard();

		for (int i = 0; i < 8; i++) {
			System.out.println();
			for (int j = 0; j < 8; j++) {
				System.out.print(intBoard[i][j]);
			}
		}
	}

	//not fully working, on to-do list
	public void checkRow(int row) { 
		int pairs = 0;

		for(int j = 1; j < 8; j++) {
			if(board[row][j] != board[row][j-1]) {
				break;
			}
			else if(board[row][j] == board[row][j-1]) {
				pairs++;
			}
			if(j == 3) {
				System.out.println("\nrow WIN");
			}
		}
	}
	//not fully working, on to-do list
	public void checkCol(int col) { 
		int pairs = 0;

		for(int i = 1; i < 8; i++) {
			if(board[i][col] != board[i-1][col]) {
				break;
			}
			else if(board[i][col] == board[i-1][col]) {
				pairs++;
			}
			if(pairs == 3) {
				System.out.println("\ncol WIN");
			}
		} 
	}

}
