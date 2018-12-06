import java.util.*;

//CHEATSHEET BOTH SIDES ON FINAL

public class Board {

	private int N = 8;
	private char[][] board;
	private LinkedList<Move> moveHistory;
	private Set<String> possibleMoves;
	private Move[] moves = {new Move(-1,-1), new Move(-1,-1)};
	private boolean winning, losing;
	
	public Board() {
		this.board = new char[N][N];
		moveHistory = new LinkedList<>();
		possibleMoves = new HashSet<>();
		initializeBoard();
	}

	public Board(Board board) {
		this.board = new char[N][N];

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++){
				this.board[i][j] = board.getBoard()[i][j];
			}
		}

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
			Move placed = new Move(move.toUpperCase(),x,y);
			moveHistory.add(placed);
			possibleMoves.remove(move);
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

	//Used for the minimax testing placement
	public void placePiece(int player, int x, int y) {
		Move move = new Move(x,y);

		if(player == 1)
			board[x][y] = 'X';
		else 
			board[x][y] = 'O';

		possibleMoves.remove(move.getMove());
	}

	public void placePiece(int player, String move) {
		Move placed = new Move(move);
		int x = placed.getX(), y = placed.getY();

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

	public ArrayList<String> currentPlayerMoves(int player){

		ArrayList<String> movesMade = new ArrayList<>();

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(player == 1 && board[i][j] == 'X') {
					char pos = Character.toUpperCase((char) (i + 65));
					String move = Character.toString(pos) + Integer.toString(j + 1);
					movesMade.add(move);
				}
				if ( player != 1 && board[i][j] == 'O') {
					char pos = Character.toUpperCase((char) (i + 65));
					String move = Character.toString(pos) + Integer.toString(j + 1);
					movesMade.add(move);
				}
			}
		}

		return movesMade;

	}

	public ArrayList<Move> adjacencyCheck() {
		ArrayList<Move> adjacentAvalible = new ArrayList<>();

		if(winCheck()||loseCheck()) {
			if (moves[0].getX() != -1 && moves[0].getY() != -1){
				adjacentAvalible.add(moves[0]);
			}
			if (moves[1].getX() != -1 && moves[1].getY() != -1){
				adjacentAvalible.add(moves[1]);
			}
		}
		else if(winKillerMove()||loseKillerMove()) {
			if (moves[0].getX() != -1 && moves[0].getY() != -1){
				adjacentAvalible.add(moves[0]);
			}
			if (moves[1].getX() != -1 && moves[1].getY() != -1){
				adjacentAvalible.add(moves[1]);
			}
		}
		else {
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(board[i][j] == '-') {					
						Move move = new Move(i, j);
						adjacentAvalible.add(move);
					}
				}
			}
		}

		return adjacentAvalible;

	}

	Set<String> getEmptySpace(){
		Set<String> emptySpace = new HashSet<>();

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				Move move = new Move(i, j);
				emptySpace.add(move.getMove());
			}
		}

		return emptySpace;
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
		int count = 0;

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {

				int currentPoints = 0;

				if(board[i][j] == '-') {
					int row = i;
					int col = j;

					col++;

					while((col < N) && (board[row][col] == 'X')) {
						count++;
						if(count == 1)
							currentPoints -= 5;
						if(count == 2)
							currentPoints -= 25;
						if(count == 3)
							currentPoints -= 50;
						col++;	
					}

					if (j - 1 >= 0)
						col = j - 1;

					while ((col >= 0) && (board[row][col] == 'X')){
						count++;
						if (count == 1)
							currentPoints -= 5;
						if (count == 2)
							currentPoints -= 25;                           
						if (count == 3)
							currentPoints -= 50;                           
						col--;

						count = 0;
						col = j;
						row++;

						while ((row < N) && (board[row][col] == 'X')){
							count++;
							if (count == 1)
								currentPoints -= 5;
							if (count == 2)
								currentPoints -= 25;                          
							if (count == 3)
								currentPoints -= 50;                            
							row++;
						}

						if (i - 1 >= 0)
							row = i - 1;
						while ((row >= 0) && (board[row][col] == 'X')){
							count++;
							if (count == 1)
								currentPoints -= 5;
							if (count == 2)
								currentPoints -= 25;                           
							if (count == 3)
								currentPoints -= 50;                           
							row--;
						}

						row = i;
						col = j;
						count = 0;

						col++;
						while ((col < N) && (board[row][col] == 'O')){
							count++;
							if (count == 1)
								currentPoints += 5;
							if (count == 2)
								currentPoints += 25;
							if (count == 3)
								currentPoints += 50;
							col++;
						}

						if (j - 1 >= 0)
							col = j - 1;
						while ((col >= 0) && (board[row][col] == 'O')){
							count++;
							if (count == 1)
								currentPoints += 5;
							if (count == 2)
								currentPoints += 25;
							if (count == 3)
								currentPoints += 50;
							col--;
						}

						count = 0;
						col = j;
						row++;

						while ((row < N) && (board[row][col] == 'O')){
							count++;
							if (count == 1)
								currentPoints += 5;
							if (count == 2)
								currentPoints += 25;
							if (count == 3)
								currentPoints += 50;
							row++;
						}

						if (i - 1 >= 0)
							row = i - 1;
						while ((row >= 0) && (board[row][col] == 'O')){
							count++;
							if (count == 1)
								currentPoints += 5;
							if (count == 2)
								currentPoints += 25;
							if (count == 3)
								currentPoints += 50;
							row--;
						}
					}
				}
				else if (board[i][j] == 'O'){
					int row = i;
					int col = j;
					count = 0;
					while ((col < N) && (board[row][col] == 'X')){
						count++;
						if (count == 4)
							currentPoints -= 1000;
						col++;
					}
					count = 0;
					col = j;
					while ((row < N) && (board[row][col] == 'X')){
						count++;
						if (count == 4)
							currentPoints -= 1000;
						row++;
					}                                      
				}
				else if (board[i][j] == 'X'){
					int row = i;
					int col = j;
					count = 0;
					while ((col < N) && (board[row][col] == 'O')){
						count++;
						if (count == 4)
							currentPoints += 1000;
						col++;
					}
					count = 0;
					col = j;
					while ((row < N) && (board[row][col] == 'O')){
						count++;
						if (count == 4)
							currentPoints += 1000;
						row++;
					}                                    
				}
				evaluation += currentPoints;
			}
		}       
		return evaluation;
	}

	public boolean loseCheck() {
		int count;

		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){               
				int row = i;
				int col = j;
				count = 0;

				while ((col < N) && (board[row][col] == 'O')){
					count++;			
					if (count == 3){
						if ((col + 1 < N ) && (board[row][col + 1] == '-')){
							losing = true;
							moves[0] = new Move(row, col + 1);                            
						}
						else if ((col - 3 >= 0) && (board[row][col - 3] == '-')){
							losing = true;
							moves[1] = new Move(row, col - 3);   
						}
					}                        
					col++;
				}                   

				count = 0;
				col = j;

				while ((row < N) && (board[row][col] == 'O')){
					count++;
					if (count == 3){
						if ((row + 1 < N ) && (board[row + 1][col] == '-')){                                
							losing = true;
							moves[0] = new Move(row + 1, col);
						}
						else if ((row - 3 >= 0) && (board[row - 3][col] == '-')){
							losing = true;
							moves[1] = new Move(row - 3, col);
						}                          
					}                  
					row++;
				}               
			}
		}
		return losing;
	}

	public boolean loseKillerMove() {
		int count;

		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){               
				int row = i;
				int col = j;
				count = 0;

				while ((col < N) && (board[row][col] == 'O')){
					count++;
					if (count == 2){                            
						if (((col + 1 < N ) && (board[row][col + 1] == '-'))
								&& ((col - 2 >= 0) && (board[row][col - 2] == '-'))){
							losing = true;
							moves[0] = new Move(row, col + 1);
							moves[1] = new Move(row, col - 2);
						}
					}			  
					col++;
				}
				count = 0;
				col = j;

				while ((row < N) && (board[row][col] == 'O')){
					count++;
					if (count == 2){                            
						if (((row + 1 < N ) && (board[row + 1][col] == '-'))
								&& ((row - 2 >= 0) && board[row - 2][col] == '-')){
							losing = true;
							moves[0] = new Move(row + 1, col);
							moves[1] = new Move(row - 2, col);
						}
					}			
					row++;
				}                   
			}
		}
		return losing;
	}

	public boolean winCheck() {
		int count;

		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				int row = i;
				int col = j;
				count = 0;

				//checks if there is any opportunity to set up a three trap

				while ((col < N) && (board[row][col] == 'X')){
					count++;			
					if (count == 3){
						if ((col + 1 < N ) && (board[row][col + 1] == '-')){
							winning = true;
							moves[0] = new Move (row, col + 1);                         
						}
						else if ((col - 3 >= 0) && (board[row][col - 3] == '-')){
							winning = true;
							moves[1] = new Move (row, col-3);
						}
					}
					if (count == 2){
						if ((col + 1 < N) && (col + 2 < N) && 
								(board[row][col + 1] == '-') && (board[row][col + 2] == 'X')){
							winning = true;
							moves[0] = new Move (row, col + 1);  
						}
						else if ((col - 2 >= 0) && (col - 3 >= 0) && 
								(board[row][col - 2] == '-') && (board[row][col - 3] == 'X')){
							winning = true;
							moves[1] = new Move (row, col-2);
						}
					}
					col++;
				}

				count = 0;
				col = j;

				//same check for rows

				while ((row < N) && (board[row][col] == 'X')){
					count++;			
					if (count == 3){
						if ((row + 1 < N ) && (board[row + 1][col] == '-')){                                
							winning = true;
							moves[0] = new Move(row + 1, col);
						}
						else if ((row - 3 >= 0) && (board[row - 3][col] == '-')){
							winning = true;
							moves[1] = new Move(row - 3, col);
						}                          
					}
					if (count == 2){
						if ((row + 1 < N) && (row + 2 < N) && 
								(board[row + 1][col] == '-') && (board[row + 2][col] == 'X')){
							winning = true;
							moves[0] = new Move(row + 1, col);
						}
						else if ((row - 2 >= 0) && (row - 3 >= 0) && 
								(board[row - 2][col] == '-') && (board[row - 3][col] == 'X')){
							winning = true;
							moves[1] = new Move(row - 2, col);
						}
					}
					row++;
				}               
			}
		}
		return winning;       
	}

	public boolean winKillerMove() {
		int count;

		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				int row = i;
				int col = j;
				count = 0;

				while ((col < N) && (board[row][col] == 'X')){
					count++;
					if (count == 2){                            
						if (((col + 1 < N ) && (board[row][col + 1] == '-'))
								&& ((col - 2 >= 0) && (board[row][col - 2] == '-'))){
							winning = true;
							moves[0] = new Move (row, col + 1);
							moves[1] = new Move (row, col - 2);
						}
						else if ((col + 1 < N ) && (board[row][col + 1] == '-')){
							winning = true;
							moves[0] = new Move (row, col + 1);
						}
						else if ((col - 2 >= 0) && (board[row][col - 2] == '-')){
							winning = true;
							moves[1] = new Move (row, col - 2);
						}                                
					}			
					col++;
				}

				count = 0;
				col = j;

				while ((row < N) && (board[row][col] == 'X')){
					count++;
					if (count == 2){                            
						if (((row + 1 < N ) && (board[row + 1][col] == '-'))
								&& ((row - 2 >= 0) && board[row - 2][col] == '-')){
							winning = true;
							moves[0] = new Move(row + 1, col);
							moves[1] = new Move(row - 2, col);
						}
						else if ((row + 1 < N ) && (board[row + 1][col] == '-')){
							winning = true;
							moves[0] = new Move(row + 1, col);
						}
						else if ((row - 2 >= 0) && (board[row - 2][col] == '-')){
							winning = true;
							moves[1] = new Move (row - 2, col);
						}                     
					}			
					row++;
				}
			}
		}
		return winning;
	}

	public boolean checkWin(char piece) {

		int count = 0;

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {

				if(board[i][j] == piece) {

					int row = i, col = j;



					//Horizontal Checker
					while(col < N && board[row][col] == piece) {	
						count++;
						if(count == 4)
							return true;
						col++;
					}

					count = 0;
					col = j;

					//Vertical checker
					while(row < N && board[row][col] == piece) {	
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
