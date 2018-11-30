import java.util.*;

public class Board {

	private int N = 8;
	private char[][] board;
	private LinkedList<Move> moveHistory;
	private Set<String> possibleMoves;
	
	public Board() {
		this.board = new char[N][N];
		moveHistory = new LinkedList<>();
		possibleMoves = new HashSet<>();
		initializeBoard();
	}

	private void initializeBoard() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++){
				board[i][j] = '-';
				char pos = Character.toUpperCase((char) (i + 65));
				possibleMoves.add(Character.toString(pos) + Integer.toString(j + 1));
			}
		}
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public char[][] getBoard() {
		return board;
	}

	public LinkedList<Move> getMoveHistory(){
		return moveHistory;
	}
	
	public Set<String> getPossibleMoves(){
		return possibleMoves;
	}

	public boolean validateMove(String move) {
		if(move.length() == 0 || (move.length() > 2 || move.length() == 1)) 
			return false;

		int x = convertXMove(move);
		int y = Character.getNumericValue(move.charAt(1)) - 1;

		if(x >= 0 && 
				x < N && 
				y >= 0 && 
				y < N && 
				board[x][y] == '-') {
			System.out.println(possibleMoves.size());
			Move placed = new Move(move.toUpperCase(),x,y);
			moveHistory.add(placed);
			possibleMoves.remove(move.toUpperCase());
			System.out.println(possibleMoves.size());
			return true;
		}

		return false;
	}

	public void placePiece(int player) {		
		Move placed = moveHistory.getLast();
		int x = placed.getX(), y = placed.getY();

		if(player == 1)
			board[x][y] = 'X';
		else 
			board[x][y] = 'O';
	}
	
	public void placePiece(int player, int x, int y) {
		
		if(player == 1)
			board[x][y] = 'X';
		else 
			board[x][y] = 'O';
		
	}
	
	public void removePreviousPiece() {
		if(!moveHistory.isEmpty()) {		
			Move remove = moveHistory.removeLast();
			
			int x = remove.getX(), y = remove.getY();
			
			board[x][y] = '-';
		}
	}

	public boolean checkDraw() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; i++) {
				if(board[i][j] == '-')
					return false;
			}
		}

		return true;
	}

	public boolean emptyBoard() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(board[i][j] != '-')
					return false;
			}
		}

		return true;
	}

	public int evaluateBoard() {
		int evaluation = 0;

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				//UP, DOWN, RIGHT, LEFT
				if(board[i][j] == 'X') {
					//evaluation += evaluatePieces('X', i, j, 0, 1);
					evaluation += evaluatePieces('X', i, j, 0, -1);
					evaluation += evaluatePieces('X', i, j, 1, 0);
					//evaluation += evaluatePieces('X', i, j, -1, 0);
				}
				//System.out.println("X EVALUATION: " + evaluation);
				if(board[i][j] == 'O') {
					//evaluation -= evaluatePieces('O', i, j, 0, 1);
					evaluation -= evaluatePieces('O', i, j, 0, -1);
					evaluation -= evaluatePieces('O', i, j, 1, 0);
					//evaluation -= evaluatePieces('O', i, j, -1, 0);
				}
				//System.out.println("O EVALUATION: " + evaluation);
			}
		}

		return evaluation;
	}

	private int evaluatePieces(char piece, int xPos, int yPos, int horizontal, int vertical) {
		assert xPos >= 0 && xPos < N && yPos >= 0 && yPos < N;

		int evaluationScore = 0, valueItr = 0;
		int[] consecutiveValues = {10,100,100000};

		for(
				int x = xPos + horizontal, y = yPos + vertical;
				(x >= 0 && x < N) && (y >= 0 && y < N);
				x += horizontal, y += vertical
				) {

			if(board[x][y] == piece && valueItr < 3) {
				evaluationScore += consecutiveValues[valueItr];
				valueItr++;

				if(valueItr == 2) {
					evaluationScore += checkKillerMove(piece, x, y, horizontal, vertical);
				}
			}

//			if(board[x][y] != '-' && board[x][y] != piece) {
//				evaluationScore -= evaluationScore/2;
//			}

		}

		System.out.println(evaluationScore);
		return evaluationScore;
	}

	private int checkKillerMove(char piece, int xPos, int yPos, int horizontal, int vertical) {

		int row = yPos, col = xPos;

		//System.out.println("ORIGINAL KILLER MOVE CHECK" + row + " " + col);

		row += vertical;
		col += horizontal;

		//System.out.println("BOTTOM/RIGHT KILLER MOVE CHECK" + row + " " + col);

		if(	(row >= 0 && row < N) && 
				(col >= 0 && col < N) &&
				board[row][col] == '-') {
			
			row += vertical * -4;
			col += horizontal * -4;

			//System.out.println("TOP/LEFT KILLER MOVE CHECK" + row + " " + col);

			if(	(row >= 0 && row < N) && 
					(col >= 0 && col < N) &&
					board[row][col] == '-') {
				return 1000000;				
			}
		}

		return 0;

	}

	public boolean checkWin(char piece) {

		int count = 0;

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {

				if(board[i][j] == piece) {

					int row = i, col = j;

					//Horizontal Checker
					while(board[row][col] == piece && col < N) {	
						count++;
						if(count == 4)
							return true;
						col++;
					}

					count = 0;
					col = j;

					//Vertical checker
					while(board[row][col] == piece && row < N) {	
						count++;
						if(count == 4)
							return true;
						row++;
					}

					count = 0;
				}
			}
		}

		return false;
	}

	public String printBoard() {
		StringBuilder sb = new StringBuilder();

		sb.append("  1 2 3 4 5 6 7 8 	Player vs. Oppenent");

		for(int i = 0; i < N; i ++) {
			sb.append("\n" + Character.toString(((char)(65 + i))) + " ");
			for(int j = 0; j < N; j++) {
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

}
